package team.themoment.hellogsm.web.domain.application.dto.response;

import java.util.List;

public record ApplicationListDto(
        ApplicationListInfoDto info,
        List<ApplicationsDto> applications
) {
}
