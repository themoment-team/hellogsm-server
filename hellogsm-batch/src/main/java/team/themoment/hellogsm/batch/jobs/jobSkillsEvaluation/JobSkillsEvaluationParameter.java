package team.themoment.hellogsm.batch.jobs.jobSkillsEvaluation;

import lombok.Getter;
import team.themoment.hellogsm.batch.common.BaseVersionParameter;

@Getter
public class JobSkillsEvaluationParameter extends BaseVersionParameter {
    private final Integer general;
    private final Integer social;
    private final Integer specialVeterans;
    private final Integer specialAdmission;

    public JobSkillsEvaluationParameter(Long version, Integer general, Integer social, Integer specialVeterans, Integer specialAdmission) {
        super(version);
        this.general = general;
        this.social = social;
        this.specialVeterans = specialVeterans;
        this.specialAdmission = specialAdmission;
    }
}
