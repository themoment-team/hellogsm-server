package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.admission.AdmissionInfo;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.ModifyApplicationForConsistency;

import java.util.Optional;

@Service
@XRayEnabled
@RequiredArgsConstructor
public class ModifyApplicationForConsistencyImpl implements ModifyApplicationForConsistency {
    private final ApplicationRepository applicationRepository;

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
        }
    }
}
