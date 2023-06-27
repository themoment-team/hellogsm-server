package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.request.AuthenticateCodeReqDto;

public interface AuthenticateCodeService {
    void execute(Long userId, AuthenticateCodeReqDto reqDto);
}
