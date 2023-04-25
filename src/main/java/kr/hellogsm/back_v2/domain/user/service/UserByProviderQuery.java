package kr.hellogsm.back_v2.domain.user.service;

import kr.hellogsm.back_v2.domain.user.dto.domain.UserDto;

public interface UserByProviderQuery {
    UserDto execute(String provider, String providerId);
}
