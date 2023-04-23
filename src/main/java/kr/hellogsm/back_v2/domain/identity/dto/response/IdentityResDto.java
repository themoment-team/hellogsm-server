package kr.hellogsm.back_v2.domain.identity.dto.response;

import kr.hellogsm.back_v2.domain.identity.entity.Identity;

public record IdentityResDto(
        Long id,
        String name,
        String phoneNumber

) {
    public static IdentityResDto from(Identity identity) {
        return new IdentityResDto(
                identity.getId(),
                identity.getName(),
                identity.getPhoneNumber()
        );
    }
}
