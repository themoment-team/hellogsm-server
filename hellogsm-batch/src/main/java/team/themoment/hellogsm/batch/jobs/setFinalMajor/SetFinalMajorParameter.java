package team.themoment.hellogsm.batch.jobs.setFinalMajor;

import lombok.Getter;
import team.themoment.hellogsm.batch.common.BaseVersionParameter;

@Getter
public class SetFinalMajorParameter extends BaseVersionParameter {
    private final Integer iot;
    private final Integer sw;
    private final Integer ai;
    public SetFinalMajorParameter(Long version, Integer iot, Integer sw, Integer ai) {
        super(version);
        this.iot = iot;
        this.sw = sw;
        this.ai = ai;
    }
}
