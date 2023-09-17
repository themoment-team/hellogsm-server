package team.themoment.hellogsm.web.domain.user.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.event.DeleteUserEvent;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.domain.user.service.DeleteUserService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * DeleteUserService의 구현체입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class DeleteUserServiceImpl implements DeleteUserService {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * user를 삭제합니다.
     *
     * @param userId 삭제할 user에 대한 userId
     * @throws ExpectedException user가 존재하지 않을 경우 발생
     * @see DeleteUserEvent user 삭제를 리스너에게 알리기 위해 사용
     */
    @Override
    public void execute(Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 사용자입니다.", HttpStatus.BAD_REQUEST));
        userRepository.deleteById(user.getId());
        applicationEventPublisher.publishEvent(new DeleteUserEvent(user));
    }
}
