package team.themoment.hellogsm.batch.common;

import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EvaluationDecisionProvider {
    private final Map<Screening, Integer> currentCandidate;

    private final Map<Screening, Integer> maxCandidate;

    public EvaluationDecisionProvider(Integer general, Integer social, Integer specialVeterans, Integer specialAdmission) {
        this.currentCandidate = initCurrentCandidate();
        this.maxCandidate = initMaxCandidate(general, social, specialVeterans, specialAdmission);
    }

    public EvaluationResult evaluate(Screening screening) {
        if (passAble(screening)) {
            currentCandidate.put(screening, currentCandidate.get(screening) + 1);
            return new EvaluationResult(EvaluationStatus.PASS, screening);
        } else {
            return evaluateNext(screening);
        }
    }

    private EvaluationResult evaluateNext(Screening screening) {
        switch (screening.getCategory()) {
            case SPECIAL:
                return evaluate(Screening.SOCIAL);
            case SOCIAL:
                return evaluate(Screening.GENERAL);
            case GENERAL:
                return new EvaluationResult(EvaluationStatus.FALL, screening);
            default:
                throw new IllegalArgumentException("처리할 수 없는 Screening Type : " + screening.name());
        }
    }


    private boolean passAble(Screening screening) {
        return currentCandidate.get(screening) < maxCandidate.get(screening);
    }

    private Map<Screening, Integer> initCurrentCandidate() {
        Map<Screening, Integer> map = Stream.of(Screening.values())
                .collect(Collectors.toMap(category -> category, category -> 0));
        return map;
    }

    private Map<Screening, Integer> initMaxCandidate(Integer general, Integer social, Integer specialVeterans, Integer specialAdmission) {
        Map<Screening, Integer> map = new HashMap<>();
        map.put(Screening.GENERAL, general);
        map.put(Screening.SOCIAL, social);
        map.put(Screening.SPECIAL_VETERANS, specialVeterans);
        map.put(Screening.SPECIAL_ADMISSION, specialAdmission);
        return map;
    }

    @Override
    public String toString() {
        return "EvaluationDecisionProvider{" +
                "currentCandidate=" + currentCandidate +
                ", maxCandidate=" + maxCandidate +
                '}';
    }
}
