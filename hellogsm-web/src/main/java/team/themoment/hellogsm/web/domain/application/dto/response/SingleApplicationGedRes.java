package team.themoment.hellogsm.web.domain.application.dto.response;

import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionInfoDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionStatusDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.GedAdmissionGradeDto;

public record SingleApplicationGedRes (
        Long id,
        AdmissionInfoDto admissionInfo,
        String middleSchoolGrade,
        GedAdmissionGradeDto admissionGrade,
        AdmissionStatusDto admissionStatus

) {
}
