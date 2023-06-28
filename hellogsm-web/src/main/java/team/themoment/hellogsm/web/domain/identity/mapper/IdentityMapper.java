package team.themoment.hellogsm.web.domain.identity.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.IdentityReqDto;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.WARN
)
public interface IdentityMapper {
    IdentityMapper INSTANCE = Mappers.getMapper(IdentityMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "phoneNumber", target = "phoneNumber"),
            @Mapping(source = "birth", target = "birth"),
            @Mapping(source = "gender", target = "gender"),
            @Mapping(source = "userId", target = "userId")
    })
    IdentityDto identityToIdentityDto(Identity identity);

    // MapStruct 써서 구현하기 힘들어서 걍 함
    default Identity identityReqDtoToIdentity(IdentityReqDto identityReqDto, Long userId, Long identityId) {
        return new Identity(
                identityId,
                identityReqDto.name(),
                identityReqDto.phoneNumber(),
                identityReqDto.birth(),
                Gender.valueOf(identityReqDto.gender()),
                userId
        );
    }
}
