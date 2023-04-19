package kr.hellogsm.back_v2.domain.temporary.service;

import kr.hellogsm.back_v2.domain.temporary.dto.request.CreateTemporaryReqDto;

public interface CreateTemporaryService {
    void execute(CreateTemporaryReqDto createTemporaryReqDto);
}
