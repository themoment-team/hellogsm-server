package team.themoment.hellogsm.web.domain.user.dto.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.enums.Role;

import java.io.Serializable;

public record UserDto(
        Long id,
        String provider,
        String providerId,

        @Enumerated(EnumType.STRING)
        Role role
) implements Serializable {
    public static UserDto from(User user) {
        return new UserDto (
                user.getId(),
                user.getProvider(),
                user.getProviderId(),
                user.getRole()
        );
    }
}
