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

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class CreateIdentityServiceImpl implements CreateIdentityService {

    private final IdentityRepository identityRepository;
    private final UserRepository userRepository;

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
