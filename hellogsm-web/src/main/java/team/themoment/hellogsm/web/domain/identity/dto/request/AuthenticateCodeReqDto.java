package team.themoment.hellogsm.web.domain.identity.dto.request;

import jakarta.validation.constraints.NotNull;

public record AuthenticateCodeReqDto(
        @NotNull String code
) {
}
