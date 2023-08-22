package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.entity.domain.identity.entity.Identity;

public interface ModifyApplicationForConsistency {
    void execute(Identity identity);
}
