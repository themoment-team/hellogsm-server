package team.themoment.hellogsm.batch.jobs.setFinalMajor;

import lombok.Getter;
import team.themoment.hellogsm.batch.common.BaseVersionParameter;

@Getter
public class SetFinalMajorParameter extends BaseVersionParameter {
    private final Integer giot;
    private final Integer gsw;
    private final Integer gai;
    private final Integer siot;
    private final Integer ssw;
    private final Integer sai;

    public SetFinalMajorParameter(Long version , Integer giot, Integer gsw, Integer gai, Integer siot, Integer ssw, Integer sai) {
        super(version);
        this.giot = giot;
        this.gsw = gsw;
        this.gai = gai;
        this.siot = siot;
        this.ssw = ssw;
        this.sai = sai;
    }
}
