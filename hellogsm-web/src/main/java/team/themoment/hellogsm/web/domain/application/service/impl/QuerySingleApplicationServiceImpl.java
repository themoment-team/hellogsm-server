package team.themoment.hellogsm.web.domain.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.grade.AdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.entity.grade.GedAdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.entity.grade.GraduateAdmissionGrade;
import team.themoment.hellogsm.web.domain.application.dto.domain.*;
import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationRes;
import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationGrade;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.QuerySingleApplicationService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class QuerySingleApplicationServiceImpl implements QuerySingleApplicationService {
    final private ApplicationRepository applicationRepository;

    @Override
    public SingleApplicationGrade execute(Long applicant) {
        Application application = applicationRepository.findByUserId(applicant)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 유저입니다", HttpStatus.NOT_FOUND));

        AdmissionInfoDto admissionInfo = ApplicationMapper.INSTANCE.admissionInfoToAdmissionInfoDto(application.getAdmissionInfo());
        AdmissionStatusDto admissionStatus = ApplicationMapper.INSTANCE.admissionStatusToAdmissionStatusDto(application.getAdmissionStatus());
        SuperGrade admissionGrade = toConvertAdmissionGradeDto(application.getAdmissionGrade());

        return new SingleApplicationRes(
                application.getId(),
                admissionInfo,
                application.getMiddleSchoolGrade().getMiddleSchoolGradeText(),
                admissionGrade, admissionStatus
        );
    }

    private SuperGrade toConvertAdmissionGradeDto(AdmissionGrade admissionGrade) {
        Object unProxyAdmissionGrade = Hibernate.unproxy(admissionGrade);

        if (unProxyAdmissionGrade instanceof GraduateAdmissionGrade) {
            return ApplicationMapper.INSTANCE.graduateAdmissionGradeToAdmissionGradeDto((GraduateAdmissionGrade) unProxyAdmissionGrade);
        }
        return ApplicationMapper.INSTANCE.gedAdmissionGradeToGedAdmissionGradeDto((GedAdmissionGrade) unProxyAdmissionGrade);
    }
}
