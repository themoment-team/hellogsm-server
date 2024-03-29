package team.themoment.hellogsm.web.domain.identity.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;
import team.themoment.hellogsm.web.domain.identity.service.CascadeDeleteIdentityService;

import java.util.Optional;

/**
 * CascadeDeleteIdentityService의 구현체입니다.
 */
@Slf4j
@Service
@XRayEnabled
@RequiredArgsConstructor
public class CascadeDeleteIdentityServiceImpl implements CascadeDeleteIdentityService {
    private final IdentityRepository identityRepository;

    /**
     * identity를 삭제합니다.
     *
     * @param userId 삭제할 identity에 대한 user의 userId
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void execute(Long userId) {
        final Optional<Identity> optionalIdentity = identityRepository.findByUserId(userId);

        if (optionalIdentity.isPresent()) {
            identityRepository.deleteById(optionalIdentity.get().getId());
        } else {
            log.warn("존재하지 않는 Identity 삭제 요청 발생 - User Id: {}", userId);
        }
    }
}
