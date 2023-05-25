package team.themoment.hellogsm.web.domain.user.service;

import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;

public interface UserByProviderQuery {
    UserDto execute(String provider, String providerId);
}
