package kr.hellogsm.back_v2.domain.temporary.dto.request;

import jakarta.validation.constraints.NotNull;
import kr.hellogsm.back_v2.domain.temporary.entity.Temporary;

public record CreateTemporaryReqDto(
        @NotNull
        String provider,

        @NotNull
        String providerId
) {
    public Temporary toEntity() {
        return new Temporary(
                null,
                provider,
                providerId
        );
    }
}
