package kr.hellogsm.back_v2.domain.identity.dto.request;

import jakarta.validation.constraints.NotNull;
import kr.hellogsm.back_v2.domain.identity.entity.Identity;

public record CreateIdentityReqDto(
        @NotNull
        String name,

        @NotNull
        String phoneNumber
) {
    public Identity toEntity(Long userId) {
        return new Identity(
                null,
                name,
                phoneNumber,
                userId
        );
    }
}
