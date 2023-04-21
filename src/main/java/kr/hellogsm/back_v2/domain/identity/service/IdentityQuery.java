package kr.hellogsm.back_v2.domain.identity.service;

import kr.hellogsm.back_v2.domain.identity.dto.response.IdentityResDto;

public interface IdentityQuery {
    IdentityResDto execute(Long userId);
}
