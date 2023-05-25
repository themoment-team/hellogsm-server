package team.themoment.hellogsm.web.domain.identity.dto.domain;

import team.themoment.hellogsm.entity.domain.identity.entity.Identity;

public record IdentityDto(
        Long id,
        String name,
        String phoneNumber

) {
    public static IdentityDto from(Identity identity) {
        return new IdentityDto(
                identity.getId(),
                identity.getName(),
                identity.getPhoneNumber()
        );
    }
}
