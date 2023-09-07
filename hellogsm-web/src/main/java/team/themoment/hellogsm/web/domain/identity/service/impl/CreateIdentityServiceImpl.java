package team.themoment.hellogsm.web.domain.identity.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.IdentityReqDto;
import team.themoment.hellogsm.web.domain.identity.dto.response.CreateIdentityResDto;
import team.themoment.hellogsm.web.domain.identity.mapper.IdentityMapper;
import team.themoment.hellogsm.web.domain.identity.repository.CodeRepository;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;
import team.themoment.hellogsm.web.domain.identity.service.CreateIdentityService;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * CreateIdentityService의 기본 구현체입니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class CreateIdentityServiceImpl implements CreateIdentityService {

    private final IdentityRepository identityRepository;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;


    /**
     * identityReqDto와 userId를 인자로 받아서 Identity를 생성하고,
     * 생성한 Identity 정보가 담긴 DTO를 반환합니다.
     *
     * @param identityReqDto 생성할 Identity 정보가 담긴 DTO
     * @param userId         생성할 Identity에 대한 User의 userId
     * @return 생성된 Identity 정보가 담긴 DTO
     * @throws ExpectedException 존재하지 않는 User나 이미 존재하는 Identity일 경우 발생
     */
    @Override
    public CreateIdentityResDto execute(IdentityReqDto identityReqDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 User 입니다", HttpStatus.BAD_REQUEST));

        if (identityRepository.existsByUserId(userId))
            throw new ExpectedException("이미 존재하는 Identity 입니다", HttpStatus.BAD_REQUEST);

        List<AuthenticationCode> codes = codeRepository.findByUserId(userId);
        AuthenticationCode recentCode = codes.stream()
                .max(Comparator.comparing(AuthenticationCode::getCreatedAt))
                .orElseThrow(() ->
                        new ExpectedException("사용자의 code가 존재하지 않습니다. 사용자의 ID : " + userId, HttpStatus.BAD_REQUEST));

        if (!recentCode.getAuthenticated())
            throw new ExpectedException("유효하지 않은 요청입니다. 인증받지 않은 code입니다.", HttpStatus.BAD_REQUEST);

        if (!recentCode.getCode().equals(identityReqDto.code()))
            throw new ExpectedException("유효하지 않은 요청입니다. 이전 혹은 잘못된 형식의 code입니다.", HttpStatus.BAD_REQUEST);

        if (!recentCode.getPhoneNumber().equals(identityReqDto.phoneNumber()))
            throw new ExpectedException("유효하지 않은 요청입니다. code인증에 사용되었던 전화번호와 요청에 사용한 전화번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);

        User roleUpdatdeUser = userRepository.save(
                new User(
                        user.getId(),
                        user.getProvider(),
                        user.getProviderId(),
                        Role.ROLE_USER
                ));

        Identity newIdentity = IdentityMapper.INSTANCE.identityReqDtoToIdentity(identityReqDto, userId, null);
        Identity savedidentity = identityRepository.save(newIdentity);

        codes.forEach(code -> codeRepository.deleteById(code.getCode())); // 인증이 성공한 경우 재사용 방지를 위해 해당 유저의 모든 code 제거

        return IdentityMapper.INSTANCE.identityToCreateIdentityResDto(savedidentity, roleUpdatdeUser);
    }
}
