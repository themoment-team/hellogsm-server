package kr.hellogsm.back_v2.domain.identity.service;

import kr.hellogsm.back_v2.domain.identity.dto.request.CreateIdentityReqDto;
import kr.hellogsm.back_v2.domain.identity.dto.response.IdentityResDto;

public interface CreateIdentityService {
    IdentityResDto execute(CreateIdentityReqDto userDto, Long userId);
}
