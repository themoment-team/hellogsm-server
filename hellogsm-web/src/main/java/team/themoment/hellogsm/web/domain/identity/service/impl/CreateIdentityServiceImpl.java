package team.themoment.hellogsm.web.domain.identity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.CreateIdentityReqDto;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;
import team.themoment.hellogsm.web.domain.identity.service.CreateIdentityService;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * CreateIdentityService의 기본 구현체입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class CreateIdentityServiceImpl implements CreateIdentityService {

    private final IdentityRepository identityRepository;
    private final UserRepository userRepository;

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
    public IdentityDto execute(CreateIdentityReqDto identityReqDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->new ExpectedException("존재하지 않는 User 입니다", HttpStatus.BAD_REQUEST));
        if (identityRepository.existsByUserId(userId))
            throw new ExpectedException("이미 존재하는 Identity 입니다", HttpStatus.BAD_REQUEST);
        User identifiedUser = new User(
                user.getId(),
                user.getProvider(),
                user.getProviderId(),
                Role.ROLE_USER
        );
        userRepository.save(identifiedUser);
        Identity identity = identityRepository.save(identityReqDto.toEntity(userId));
        return IdentityDto.from(identity);
    }
}
