package team.themoment.hellogsm.web.domain.identity.dto.domain;

import team.themoment.hellogsm.entity.domain.identity.entity.Identity;

public record IdentityDto(
        Long id,
        String name,
        String phoneNumber,
        Long userId

) {
}
