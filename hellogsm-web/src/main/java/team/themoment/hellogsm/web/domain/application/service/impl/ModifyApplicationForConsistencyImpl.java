package team.themoment.hellogsm.web.domain.application.service.impl;

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

@Slf4j
@Service
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
        } else {
            log.warn("Application이 없는 User의 Identity 수정 발생 - User Id: {}", identity.getUserId());
        }
    }
}
