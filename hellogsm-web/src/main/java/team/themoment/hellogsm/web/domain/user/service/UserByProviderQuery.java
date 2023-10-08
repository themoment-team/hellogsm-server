package team.themoment.hellogsm.web.domain.user.service;

import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;

/**
 * provider로 user를 찾아서 userDto로 반환하는 interface입니다.
 */
public interface UserByProviderQuery {
    UserDto execute(String provider, String providerId);
}
