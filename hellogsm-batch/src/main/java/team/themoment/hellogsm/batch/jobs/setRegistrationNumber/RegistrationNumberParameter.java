package team.themoment.hellogsm.batch.jobs.setRegistrationNumber;

import lombok.Getter;
import team.themoment.hellogsm.batch.common.BaseVersionParameter;

@Getter
public class RegistrationNumberParameter extends BaseVersionParameter {
    public RegistrationNumberParameter(Long version) {
        super(version);
    }
}
