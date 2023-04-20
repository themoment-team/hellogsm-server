package kr.hellogsm.back_v2.domain.user.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.hellogsm.back_v2.domain.user.entity.User;
import kr.hellogsm.back_v2.domain.user.enums.Role;

public record UserResDto(
        Long id,
        String provider,
        String providerId,

        @Enumerated(EnumType.STRING)
        Role role
) {
    public static UserResDto from(User user) {
        return new UserResDto(
                user.getId(),
                user.getProvider(),
                user.getProvider(),
                user.getRole()
        );
    }
}
