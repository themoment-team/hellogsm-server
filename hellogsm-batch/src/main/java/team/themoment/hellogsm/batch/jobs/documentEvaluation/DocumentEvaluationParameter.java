package team.themoment.hellogsm.batch.jobs.documentEvaluation;

import lombok.Getter;
import team.themoment.hellogsm.batch.common.BaseVersionParameter;

@Getter
public class DocumentEvaluationParameter extends BaseVersionParameter {
    private final Integer general;
    private final Integer social;
    private final Integer specialVeterans;
    private final Integer specialAdmission;

    public DocumentEvaluationParameter(Long version, Integer general, Integer social, Integer specialVeterans, Integer specialAdmission) {
        super(version);
        this.general = general;
        this.social = social;
        this.specialVeterans = specialVeterans;
        this.specialAdmission = specialAdmission;
    }
}
