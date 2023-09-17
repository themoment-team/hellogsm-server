package team.themoment.hellogsm.web.domain.user.service;

import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;

/**
 * userId로 user를 찾아서 userDto로 반환하는 interface입니다.
 */
public interface UserByIdQuery {
    UserDto execute(Long userId);
}
