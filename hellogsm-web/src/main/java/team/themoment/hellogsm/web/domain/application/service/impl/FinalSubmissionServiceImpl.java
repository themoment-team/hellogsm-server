package team.themoment.hellogsm.web.domain.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.admission.AdmissionInfo;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.FinalSubmissionService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class FinalSubmissionServiceImpl implements FinalSubmissionService {
    private final ApplicationRepository applicationRepository;

    @Override
    public void execute(Long userId) {
        Application application = applicationRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("원서가 존재하지 않습니다", HttpStatus.BAD_REQUEST));


        AdmissionStatus admissionStatus = application.getAdmissionStatus();
        AdmissionStatus newAdmissionStatus = AdmissionStatus.builder()
                .id(admissionStatus.getId())
                .isFinalSubmitted(true)
                .isPrintsArrived(admissionStatus.isPrintsArrived())
                .firstEvaluation(admissionStatus.getFirstEvaluation())
                .secondEvaluation(admissionStatus.getSecondEvaluation())
                .registrationNumber(admissionStatus.getRegistrationNumber())
                .secondScore(admissionStatus.getSecondScore())
                .finalMajor(admissionStatus.getFinalMajor())
                .build();



        Application newApplication = new Application(
                application.getId(),
                application.getAdmissionInfo(),
                newAdmissionStatus,
                application.getMiddleSchoolGrade(),
                application.getUserId()
        );

        applicationRepository.save(newApplication);
    }
}
