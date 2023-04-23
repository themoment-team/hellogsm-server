package kr.hellogsm.back_v2.domain.identity.service.impl;

import kr.hellogsm.back_v2.domain.identity.dto.request.CreateIdentityReqDto;
import kr.hellogsm.back_v2.domain.identity.dto.response.IdentityResDto;
import kr.hellogsm.back_v2.domain.identity.entity.Identity;
import kr.hellogsm.back_v2.domain.identity.repository.IdentityRepository;
import kr.hellogsm.back_v2.domain.identity.service.CreateIdentityService;
import kr.hellogsm.back_v2.domain.user.repository.UserRepository;
import kr.hellogsm.back_v2.global.exception.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public IdentityResDto execute(CreateIdentityReqDto identityReqDto, Long userId) {
        if (!userRepository.existsById(userId))
            throw new ExpectedException("존재하지 않는 User 입니다", HttpStatus.BAD_REQUEST);
        if (identityRepository.existsByUserId(userId))
            throw new ExpectedException("이미 존재하는 Identity 입니다", HttpStatus.BAD_REQUEST);
        Identity identity = identityRepository.save(identityReqDto.toEntity(userId));
        return IdentityResDto.from(identity);
    }
}
