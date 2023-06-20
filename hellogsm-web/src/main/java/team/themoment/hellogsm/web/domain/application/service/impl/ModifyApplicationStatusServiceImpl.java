package team.themoment.hellogsm.web.domain.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Major;
import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationStatusReqDto;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.ModifyApplicationStatusService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class ModifyApplicationStatusServiceImpl implements ModifyApplicationStatusService {
    final private ApplicationRepository applicationRepository;

    @Override
    public void execute(Long userId, ApplicationStatusReqDto applicationStatusReqDto) {
        Application application = applicationRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 유저입니다", HttpStatus.NOT_FOUND));

        AdmissionStatus admissionStatus = createNewAdmissionStatus(application.getAdmissionStatus().getId(), applicationStatusReqDto);

        Application newApplication = new Application(
                application.getId(),
                application.getAdmissionInfo(),
                admissionStatus,
                application.getMiddleSchoolGrade(),
                application.getUserId()
        );

        applicationRepository.save(newApplication);
    }

    private AdmissionStatus createNewAdmissionStatus(Long admissionStatusId, ApplicationStatusReqDto applicationStatusReqDto) {
        Major major = null;

        try {
            major = Major.valueOf(applicationStatusReqDto.finalMajor());
        } catch (Exception ignored) {}

        return AdmissionStatus.builder()
                .id(admissionStatusId)
                .isPrintsArrived(applicationStatusReqDto.isPrintsArrived())
                .firstEvaluation(EvaluationStatus.valueOf(applicationStatusReqDto.firstEvaluation()))
                .secondEvaluation(EvaluationStatus.valueOf(applicationStatusReqDto.secondEvaluation()))
                .isFinalSubmitted(applicationStatusReqDto.isFinalSubmitted())
                .registrationNumber(applicationStatusReqDto.registrationNumber())
                .finalMajor(major)
                .secondScore(applicationStatusReqDto.secondScore())
                .build();
    }
}
