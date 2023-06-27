package team.themoment.hellogsm.web.domain.identity.dto.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record IdentityDto(
        Long id,
        String name,
        String phoneNumber,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate birth,
        Long userId
) {
}
