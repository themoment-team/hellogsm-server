package kr.hellogsm.back_v2.domain.user.service;

import kr.hellogsm.back_v2.domain.user.dto.domain.UserDto;

public interface UserByIdQuery {
    UserDto execute(Long userId);
}
