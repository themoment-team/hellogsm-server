package kr.hellogsm.back_v2.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import kr.hellogsm.back_v2.domain.user.entity.User;

public record CreateUserReqDto(
        @NotNull
        String provider,

        @NotNull
        String providerId
) {
    public User toEntity() {
        return new User(
                null,
                provider,
                providerId,
                null
        );
    }
}
