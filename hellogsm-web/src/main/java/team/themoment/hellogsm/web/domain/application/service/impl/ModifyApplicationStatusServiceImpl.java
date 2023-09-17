package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Major;
import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationStatusReqDto;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.ModifyApplicationStatusService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * 특정 사용자의 원서 상태를 변경하는 service implementation 입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class ModifyApplicationStatusServiceImpl implements ModifyApplicationStatusService {
    final private ApplicationRepository applicationRepository;

    /**
     * 원서를 찾고 해당 원서와 원서상태 정보를 바탕으로 원서의 입학 현황을 수정합니다.
     *
     * @param userId 변경될 유저의 pk값
     * @param applicationStatusReqDto 변경할 원서상태 정보
     * @throws ExpectedException 발생조건은 아래와 같음 <br>
     *      1. 존재하지 않는 유저일 경우 <br>
     */
    @Override
    public void execute(Long userId, ApplicationStatusReqDto applicationStatusReqDto) {
        Application application = applicationRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 유저입니다", HttpStatus.NOT_FOUND));

        AdmissionStatus admissionStatus = ApplicationMapper.INSTANCE
                .createNewAdmissionStatus(application.getAdmissionStatus().getId(), applicationStatusReqDto);

        Application newApplication = new Application(
                application.getId(),
                application.getAdmissionInfo(),
                admissionStatus,
                application.getMiddleSchoolGrade(),
                application.getUserId()
        );

        applicationRepository.save(newApplication);
    }
}
