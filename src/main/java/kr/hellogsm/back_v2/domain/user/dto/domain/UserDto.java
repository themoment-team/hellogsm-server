package kr.hellogsm.back_v2.domain.user.dto.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.hellogsm.back_v2.domain.user.entity.User;
import kr.hellogsm.back_v2.domain.user.enums.Role;

public record UserDto(
        Long id,
        String provider,
        String providerId,

        @Enumerated(EnumType.STRING)
        Role role
) {
    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getProvider(),
                user.getProvider(),
                user.getRole()
        );
    }
}