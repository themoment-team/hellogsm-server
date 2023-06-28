package team.themoment.hellogsm.web.domain.identity.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.admission.AdmissionInfo;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.IdentityReqDto;
import team.themoment.hellogsm.web.domain.identity.mapper.IdentityMapper;
import team.themoment.hellogsm.web.domain.identity.repository.CodeRepository;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;
import team.themoment.hellogsm.web.domain.identity.service.ModifyIdentityService;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class ModifyIdentityServiceImpl implements ModifyIdentityService {

    private final IdentityRepository identityRepository;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final ApplicationRepository applicationRepository; // TODO 양방향 의존성 제거하기 Application <-> Identity - 이벤트로 의존성 해소

    @Override
    public IdentityDto execute(IdentityReqDto reqDto, Long userId) {
        if(!userRepository.existsById(userId))
                throw new ExpectedException("존재하지 않는 User 입니다", HttpStatus.BAD_REQUEST);
        Identity savedidentity = identityRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 Identity 입니다", HttpStatus.BAD_REQUEST));
        List<AuthenticationCode> codes = codeRepository.findByUserId(userId);
        AuthenticationCode recentCode = codeRepository.findByUserId(userId).stream()
                .max(Comparator.comparing(AuthenticationCode::getCreatedAt))
                .orElseThrow(() ->
                        new ExpectedException("사용자의 code가 존재하지 않습니다. 사용자의 ID : " + userId, HttpStatus.BAD_REQUEST));
        if (!recentCode.getCode().equals(reqDto.code()) || !recentCode.getAuthenticated())
            throw new ExpectedException("유효하지 않은 code 입니다. 이전 혹은 잘못되었거나 인증받지 않은 code입니다.", HttpStatus.BAD_REQUEST);

        // Identity의 인증 정보를 Application의 지원자 정보와 일관되도록 하는 로직
        // 여기서부터
        Optional<Application> savedApplicationOpt = applicationRepository.findByUserId(userId);
        if (savedApplicationOpt.isPresent()) {
            Application savedApplication = savedApplicationOpt.get();
            AdmissionInfo newAdmissionInfo =
                    ApplicationMapper.INSTANCE.toConsistentAdmissionInfoWithIdentityReqDto(savedApplication.getAdmissionInfo(), reqDto);

            Application a = applicationRepository.save(
                    new Application(
                            savedApplication.getId(),
                            newAdmissionInfo,
                            savedApplication.getAdmissionStatus(),
                            savedApplication.getMiddleSchoolGrade(),
                            savedApplication.getUserId()
                    ));
            var a1 = a;
        }
        // 여기까지, Identity 모듈에서 이벤트 발행해서 Application 모듈에서 처리하도록 수정해야 함

        Identity newIdentity = IdentityMapper.INSTANCE.identityReqDtoToIdentity(reqDto, userId, savedidentity.getId());
        Identity newSavedIdentity = identityRepository.save(newIdentity);

        codes.forEach(code -> codeRepository.deleteById(code.getCode())); // 인증이 성공한 경우 재사용 방지를 위해 해당 유저의 모든 code 제거

        return IdentityMapper.INSTANCE.identityToIdentityDto(newSavedIdentity);
    }
}
