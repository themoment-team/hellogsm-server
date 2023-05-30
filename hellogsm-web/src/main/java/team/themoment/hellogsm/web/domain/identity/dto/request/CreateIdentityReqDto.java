package team.themoment.hellogsm.web.domain.identity.dto.request;

import jakarta.validation.constraints.NotNull;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;

public record CreateIdentityReqDto(
        @NotNull
        String name,

        @NotNull
        String phoneNumber
) {
}
