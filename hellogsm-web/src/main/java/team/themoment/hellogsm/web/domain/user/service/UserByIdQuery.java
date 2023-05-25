package team.themoment.hellogsm.web.domain.user.service;

import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;

public interface UserByIdQuery {
    UserDto execute(Long userId);
}
