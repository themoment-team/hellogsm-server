package team.themoment.hellogsm.batch.jobs.setRegistrationNumber;

import team.themoment.hellogsm.entity.domain.application.enums.Screening;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RegistrationNumberSequence {
    private Map<Screening, Integer> sequenceMap =
            new HashMap(Map.of(Screening.GENERAL, 0,Screening.SOCIAL, 0,Screening.SPECIAL, 0));

    public void add(Screening screening) {
        sequenceMap.put(screening, sequenceMap.get(screening) + 1);
    }

    public Integer get(Screening screening) {
        return sequenceMap.get(screening);
    }

    public void init() {
        sequenceMap = new HashMap(Map.of(Screening.GENERAL, 0,Screening.SOCIAL, 0,Screening.SPECIAL, 0));
    }

    public void clear() {
        sequenceMap = Collections.emptyMap();
    }
}