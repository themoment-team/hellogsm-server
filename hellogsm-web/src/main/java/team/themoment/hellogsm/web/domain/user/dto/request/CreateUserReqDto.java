package team.themoment.hellogsm.web.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import team.themoment.hellogsm.entity.domain.user.entity.User;

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
