package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.admission.AdmissionInfo;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.ModifyApplicationForConsistency;

import java.util.Optional;

/**
 * 본인인증 정보 수정 시 원서를 수정하는 service implementation 입니다.
 */
@Slf4j
@Service
@XRayEnabled
@RequiredArgsConstructor
public class ModifyApplicationForConsistencyImpl implements ModifyApplicationForConsistency {
    private final ApplicationRepository applicationRepository;

    /**
     * 수정된 본인인증 정보를 바탕으로 원서를 수정합니다. <br>
     * 원서가 존재하지 않는 요청은 무시합니다.
     *
     * @param identity 수정 된 본인인증 정보
     */
    @Override
    public void execute(Identity identity) {
        Optional<Application> savedApplicationOpt = applicationRepository.findByUserId(identity.getUserId());

        if (savedApplicationOpt.isPresent()) {
            Application savedApplication = savedApplicationOpt.get();
            AdmissionInfo newAdmissionInfo =
                    ApplicationMapper.INSTANCE.toConsistentAdmissionInfoWithIdentity(savedApplication.getAdmissionInfo(), identity);

            applicationRepository.save(
                    new Application(
                            savedApplication.getId(),
                            newAdmissionInfo,
                            savedApplication.getAdmissionStatus(),
                            savedApplication.getMiddleSchoolGrade(),
                            savedApplication.getUserId()
                    ));
        } else {
            log.warn("Application이 없는 User의 Identity 수정 발생 - User Id: {}", identity.getUserId());
        }
    }
}
