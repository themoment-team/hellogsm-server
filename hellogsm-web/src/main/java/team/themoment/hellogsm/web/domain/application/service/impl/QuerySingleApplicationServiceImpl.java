package team.themoment.hellogsm.web.domain.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.grade.AdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.entity.grade.GedAdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.entity.grade.GraduateAdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;
import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionInfoDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionStatusDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.GedAdmissionGradeDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.GeneralAdmissionGradeDto;
import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationGedRes;
import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationGeneralRes;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.QuerySingleApplicationService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class QuerySingleApplicationServiceImpl implements QuerySingleApplicationService {
    final private ApplicationRepository applicationRepository;

    @Override
    public Object execute(Long applicant) {
        Application application = applicationRepository.findByUserId(applicant)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 유저입니다", HttpStatus.NOT_FOUND));

        AdmissionInfoDto admissionInfo = ApplicationMapper.INSTANCE.admissionInfoToAdmissionInfoDto(application.getAdmissionInfo());
        AdmissionStatusDto admissionStatus = ApplicationMapper.INSTANCE.admissionStatusToAdmissionStatusDto(application.getAdmissionStatus());
        Object admissionGrade = toConvertAdmissionGradeDto(application.getAdmissionGrade());

        if (application.getAdmissionInfo().getGraduation().equals(GraduationStatus.GED))
            return new SingleApplicationGedRes(
                    application.getId(),
                    admissionInfo,
                    application.getMiddleSchoolGrade().getMiddleSchoolGradeText(),
                    (GedAdmissionGradeDto) admissionGrade, admissionStatus
            );

        return new SingleApplicationGeneralRes(
                application.getId(),
                admissionInfo,
                application.getMiddleSchoolGrade().getMiddleSchoolGradeText(),
                (GeneralAdmissionGradeDto) admissionGrade, admissionStatus
        );
    }

    private Object toConvertAdmissionGradeDto(AdmissionGrade admissionGrade) {
        Object unProxyAdmissionGrade = Hibernate.unproxy(admissionGrade);

        if (unProxyAdmissionGrade instanceof GraduateAdmissionGrade) {
            return ApplicationMapper.INSTANCE.graduateAdmissionGradeToAdmissionGradeDto((GraduateAdmissionGrade) unProxyAdmissionGrade);
        }
        return ApplicationMapper.INSTANCE.gedAdmissionGradeToGedAdmissionGradeDto((GedAdmissionGrade) unProxyAdmissionGrade);
    }
}
