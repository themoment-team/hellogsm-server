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

        if (!recentCode.getAuthenticated())
            throw new ExpectedException("유효하지 않은 요청입니다. 인증받지 않은 code입니다.", HttpStatus.BAD_REQUEST);

        if (!recentCode.getCode().equals(reqDto.code()))
            throw new ExpectedException("유효하지 않은 요청입니다. 이전 혹은 잘못된 형식의 code입니다.", HttpStatus.BAD_REQUEST);

        if (!recentCode.getPhoneNumber().equals(reqDto.phoneNumber()))
            throw new ExpectedException("유효하지 않은 요청입니다. code인증에 사용되었던 전화번호와 요청에 사용한 전화번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);

        Identity newIdentity = IdentityMapper.INSTANCE.identityReqDtoToIdentity(reqDto, userId, savedidentity.getId());
        Identity newSavedIdentity = identityRepository.save(newIdentity);

        codes.forEach(code -> codeRepository.deleteById(code.getCode())); // 인증이 성공한 경우 재사용 방지를 위해 해당 유저의 모든 code 제거

        return IdentityMapper.INSTANCE.identityToIdentityDto(newSavedIdentity);
    }
}
