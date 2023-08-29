package team.themoment.hellogsm.web.domain.user.service.impl;

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

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class DeleteUserServiceImpl implements DeleteUserService {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void execute(Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 사용자입니다.", HttpStatus.BAD_REQUEST));
        userRepository.deleteById(user.getId());
        applicationEventPublisher.publishEvent(new DeleteUserEvent(user));
    }
}
