package team.themoment.hellogsm.web.domain.identity.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record IdentityReqDto(
        @NotNull String code,
        @NotNull String name,
        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$")
        @NotNull String phoneNumber,
        @Pattern(regexp = "^(MALE|FEMALE)$")
        @NotNull String gender,
        @NotNull LocalDate birth
) {
}
