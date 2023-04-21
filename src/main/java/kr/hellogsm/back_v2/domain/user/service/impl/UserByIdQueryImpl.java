package kr.hellogsm.back_v2.domain.user.service.impl;

import kr.hellogsm.back_v2.domain.user.dto.response.UserResDto;
import kr.hellogsm.back_v2.domain.user.entity.User;
import kr.hellogsm.back_v2.domain.user.repository.UserRepository;
import kr.hellogsm.back_v2.domain.user.service.UserByIdQuery;
import kr.hellogsm.back_v2.global.exception.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserByIdQueryImpl implements UserByIdQuery {

    private final UserRepository userRepository;

    @Override
    public UserResDto execute(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 User 입니다", HttpStatus.BAD_REQUEST));

        return UserResDto.from(user);
    }
}
