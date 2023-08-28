package team.themoment.hellogsm.batch.jobs.setFinalMajor;

import lombok.Getter;
import team.themoment.hellogsm.batch.common.BaseVersionParameter;

@Getter
public class SetFinalMajorParameter extends BaseVersionParameter {
    public SetFinalMajorParameter(Long version) {
        super(version);
    }
}
