package team.themoment.hellogsm.web.domain.user.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import team.themoment.hellogsm.entity.domain.user.event.DeleteUserEvent;
import team.themoment.hellogsm.web.domain.application.service.CascadeDeleteApplicationService;
import team.themoment.hellogsm.web.domain.identity.service.CascadeDeleteIdentityService;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteUserEventHandler {
    private final CascadeDeleteIdentityService cascadeDeleteIdentityService;
    private final CascadeDeleteApplicationService cascadeDeleteApplicationService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void deleteUserEventHandler(DeleteUserEvent event) {
        cascadeDeleteIdentityService.execute(event.user().getId());
        cascadeDeleteApplicationService.execute(event.user().getId());
    }
}
