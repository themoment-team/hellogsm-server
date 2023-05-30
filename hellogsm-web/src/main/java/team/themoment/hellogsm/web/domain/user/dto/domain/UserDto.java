package team.themoment.hellogsm.web.domain.user.dto.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import team.themoment.hellogsm.entity.domain.user.enums.Role;

import java.io.Serializable;

public record UserDto(
        Long id,
        String provider,
        String providerId,

        @Enumerated(EnumType.STRING)
        Role role
) implements Serializable {
}
