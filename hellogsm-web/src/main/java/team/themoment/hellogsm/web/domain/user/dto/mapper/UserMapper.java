package team.themoment.hellogsm.web.domain.user.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.web.domain.user.dto.request.CreateUserReqDto;
import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.WARN
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "provider", target = "provider"),
            @Mapping(source = "providerId", target = "providerId"),
            @Mapping(source = "role", target = "role")
    })
    UserDto userToUserDto(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "provider", target = "provider"),
            @Mapping(source = "providerId", target = "providerId"),
            @Mapping(target = "role", ignore = true)
    })
    User createUserReqDtoToUser(CreateUserReqDto createUserReqDto);
}
