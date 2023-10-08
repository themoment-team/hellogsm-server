package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.admission.AdmissionInfo;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.FinalSubmissionService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * 현재 사용자의 원서를 최종제출하는 service implementation 입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class FinalSubmissionServiceImpl implements FinalSubmissionService {
    private final ApplicationRepository applicationRepository;

    /**
     * 원서를 찾고 해당 원서의 최종제출 여부를 수정합니다.
     *
     * @param userId 요청한 유저의 pk값
     * @throws ExpectedException 발생조건은 아래와 같음 <br>
     *      1. 원서가 존재하지 않을 경우 <br>
     *      2. 최종제출이 완료된 원서일 경우 <br>
     */
    @Override
    public void execute(Long userId) {
        Application application = applicationRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("원서가 존재하지 않습니다", HttpStatus.BAD_REQUEST));
        if (application.getAdmissionStatus().isFinalSubmitted())
            throw new ExpectedException("이미 최종제출이 완료 되었습니다", HttpStatus.BAD_REQUEST);

        AdmissionStatus newAdmissionStatus = ApplicationMapper.INSTANCE.updateFinalSubmission(application.getAdmissionStatus());

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
