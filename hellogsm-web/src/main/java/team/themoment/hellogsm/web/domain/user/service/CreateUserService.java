package team.themoment.hellogsm.web.domain.user.service;

import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import team.themoment.hellogsm.web.domain.user.dto.request.CreateUserReqDto;

/**
 * user를 생성하는 interface입니다.
 */
public interface CreateUserService {
    UserDto execute(CreateUserReqDto createUserReqDto);
}
