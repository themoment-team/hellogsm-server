package team.themoment.hellogsm.web.domain.application.dto.response;

import team.themoment.hellogsm.web.domain.application.dto.domain.GeneralAdmissionGradeDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionInfoDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionStatusDto;

public record SingleApplicationGeneralRes(
        Long id,
        AdmissionInfoDto admissionInfo,
        String middleSchoolGrade,
        GeneralAdmissionGradeDto admissionGrade,
        AdmissionStatusDto admissionStatus
) {
}
