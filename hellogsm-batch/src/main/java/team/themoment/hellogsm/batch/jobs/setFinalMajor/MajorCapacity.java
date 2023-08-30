package team.themoment.hellogsm.batch.jobs.setFinalMajor;

import team.themoment.hellogsm.entity.domain.application.entity.admission.DesiredMajor;
import team.themoment.hellogsm.entity.domain.application.enums.Major;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

import java.util.HashMap;
import java.util.Map;

public class MajorCapacity {
    private final static String GENERAL = "GENERAL";
    private final static String SPECIAL = "SPECIAL";
    private final Map<String, Map<Major, Integer>> currentCapacity = new HashMap<>();
    private final Map<String, Map<Major, Integer>> maxCapacity;

    public MajorCapacity(Integer giot, Integer gsw, Integer gai, Integer siot, Integer ssw, Integer sai) {
        this.maxCapacity = new HashMap<>();
        initializeCapacity(currentCapacity, 0, 0, 0, 0, 0, 0);
        initializeCapacity(maxCapacity, gsw, gai, giot, ssw, sai, siot);
    }

    private void initializeCapacity(Map<String, Map<Major, Integer>> capacityMap,
                                    Integer gsw, Integer gai, Integer giot,
                                    Integer ssw, Integer sai, Integer siot) {
        capacityMap.put(GENERAL, createMajorMap(gsw, gai, giot));
        capacityMap.put(SPECIAL, createMajorMap(ssw, sai, siot));
    }

    private Map<Major, Integer> createMajorMap(Integer sw, Integer ai, Integer iot) {
        Map<Major, Integer> majorMap = new HashMap<>();
        majorMap.put(Major.SW, sw);
        majorMap.put(Major.AI, ai);
        majorMap.put(Major.IOT, iot);
        return majorMap;
    }

    public Major assignMajor(Screening screening, DesiredMajor desiredMajor) {
        switch (screening) {
            case GENERAL, SOCIAL -> {
                return assign(GENERAL, desiredMajor);
            }
            case SPECIAL_VETERANS, SPECIAL_ADMISSION -> {
                return assign(SPECIAL, desiredMajor);
            }
            default -> throw new IllegalArgumentException("지원하지 않는 타입 : " + screening.name());
        }
    }

    private Major assign(String key, DesiredMajor desiredMajor) {
        Map<Major, Integer> currentMajor = currentCapacity.get(key);
        Map<Major, Integer> maxMajor = maxCapacity.get(key);
        Major first = desiredMajor.getFirstDesiredMajor();
        Major second = desiredMajor.getSecondDesiredMajor();
        Major third = desiredMajor.getThirdDesiredMajor();

        if (currentMajor.get(first) < maxMajor.get(first)) {
            currentMajor.put(first, currentMajor.get(first) + 1);
            currentCapacity.put(key, currentMajor);
            return first;
        } else if (currentMajor.get(second) < maxMajor.get(second)) {
            currentMajor.put(second, currentMajor.get(second) + 1);
            currentCapacity.put(key, currentMajor);
            return second;
        } else if (currentMajor.get(third) < maxMajor.get(third)) {
            currentMajor.put(third, currentMajor.get(third) + 1);
            currentCapacity.put(key, currentMajor);
            return third;
        } else {
            throw new IllegalStateException("발생해선 안되는 상황, Limit을 넘을 수 없음, " +
                    "setFinalMajor Job의 파라미터의 총 합은 2차 시험을 통과한 인원 수와 동일해야 함");
        }
    }

    @Override
    public String toString() {
        return "MajorCapacity{" +
                "currentCapacity=" + currentCapacity +
                ", maxCapacity=" + maxCapacity +
                '}';
    }
}
