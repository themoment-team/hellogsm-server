package team.themoment.hellogsm.web.domain.application.dto.response;

import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionInfoDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionStatusDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.SuperGrade;

public record SingleApplicationRes(
        Long id,
        AdmissionInfoDto admissionInfo,
        String middleSchoolGrade,
        SuperGrade admissionGrade,
        AdmissionStatusDto admissionStatus
) {
}
