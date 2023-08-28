package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.request.GenerateCodeReqDto;

// TODO 나중에 기능 합치던가 하기
public interface GenerateTestCodeService {
    String execute(Long userId, GenerateCodeReqDto reqDto);
}
