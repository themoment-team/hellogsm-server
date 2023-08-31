package team.themoment.hellogsm.batch.jobs.documentEvaluation;

import lombok.Getter;
import team.themoment.hellogsm.batch.common.BaseVersionParameter;

@Getter
public class DocumentEvaluationParameter extends BaseVersionParameter {
    private final Integer general;
    private final Integer social;
    private final Integer special;

    public DocumentEvaluationParameter(Long version , Integer general, Integer social, Integer special) {
        super(version);
        this.general = general;
        this.social = social;
        this.special = special;
    }
}
