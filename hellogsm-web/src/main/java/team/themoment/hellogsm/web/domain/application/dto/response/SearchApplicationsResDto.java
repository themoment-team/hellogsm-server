package team.themoment.hellogsm.web.domain.application.dto.response;

import java.util.List;

public record SearchApplicationsResDto (
    ApplicationListInfoDto info,
    List<SearchApplicationResDto> applications
) {}
