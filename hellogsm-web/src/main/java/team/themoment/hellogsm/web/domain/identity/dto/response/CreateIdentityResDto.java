package team.themoment.hellogsm.web.domain.identity.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.user.enums.Role;

import java.time.LocalDate;

public record CreateIdentityResDto(
        Long id,
        String name,
        String phoneNumber,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate birth,
        Gender gender,
        Role userRole,
        Long userId
) {
}
