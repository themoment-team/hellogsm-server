package team.themoment.hellogsm.web.domain.identity.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import team.themoment.hellogsm.entity.domain.identity.event.UpdateIdentityEvent;
import team.themoment.hellogsm.web.domain.application.service.ModifyApplicationForConsistency;

@Component
@RequiredArgsConstructor
public class UpdateIdentityEventHandler {
    private final ModifyApplicationForConsistency modifyApplicationForConsistency;

    @EventListener
    public void updateIdentityEventHandler(UpdateIdentityEvent event) {
        modifyApplicationForConsistency.execute(event.identity());
    }
}
