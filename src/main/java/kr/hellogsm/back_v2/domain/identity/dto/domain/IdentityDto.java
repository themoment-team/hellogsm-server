package kr.hellogsm.back_v2.domain.identity.dto.domain;

import kr.hellogsm.back_v2.domain.identity.entity.Identity;

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
