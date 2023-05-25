package team.themoment.hellogsm.web.domain.user.service;

import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import team.themoment.hellogsm.web.domain.user.dto.request.CreateUserReqDto;

public interface CreateUserService {
    UserDto execute(CreateUserReqDto createUserReqDto);
}
