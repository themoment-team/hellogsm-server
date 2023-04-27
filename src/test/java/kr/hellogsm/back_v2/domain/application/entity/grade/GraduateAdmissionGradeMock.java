package kr.hellogsm.back_v2.domain.application.entity.grade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hellogsm.back_v2.domain.application.service.data.ScoreData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GraduateAdmissionGradeMock {
    ObjectMapper objectMapper = new ObjectMapper();
    List<BigDecimal> subjectPerfectScore = createBigDecimalArray(new int[]{5, 5, 5, 5, 5, 5, 5, 5});

    public String grad1FreeSchoolYearPerfectScore() throws JsonProcessingException {
        ScoreData value = new ScoreData(
                null,
                null,
                subjectPerfectScore,
                subjectPerfectScore,
                subjectPerfectScore,
                createBigDecimalArray(new int[] {5,5,5,5,5,5,5,5,5}),
                createBigDecimalArray(new int[] {0,0,0}),
                createBigDecimalArray(new int[] {0,0,0,0,0,0,0,0,0}),
                createBigDecimalArray(new int[] {0,0,0}),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                "자유학년제",
                null
        );

        return objectMapper.writeValueAsString(value);
    }

    public String grad2FreeSchoolYearPerfectScore() throws JsonProcessingException {
        ScoreData value = new ScoreData(
                subjectPerfectScore,
                subjectPerfectScore,
                null,
                null,
                subjectPerfectScore,
                createBigDecimalArray(new int[] {5,5,5,5,5,5,5,5,5}),
                createBigDecimalArray(new int[] {0,0,0}),
                createBigDecimalArray(new int[] {0,0,0,0,0,0,0,0,0}),
                createBigDecimalArray(new int[] {0,0,0}),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                "자유학년제",
                null
        );

        return objectMapper.writeValueAsString(value);
    }

    public String grade1Semester1PerfectScore() throws JsonProcessingException {
        ScoreData scoreData = new ScoreData(
                null,
                subjectPerfectScore,
                subjectPerfectScore,
                subjectPerfectScore,
                subjectPerfectScore,
                createBigDecimalArray(new int[] {5,5,5,5,5,5,5,5,5}),
                createBigDecimalArray(new int[] {0,0,0}),
                createBigDecimalArray(new int[] {0,0,0,0,0,0,0,0,0}),
                createBigDecimalArray(new int[] {0,0,0}),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                "자유학기제",
                "1-1"
        );

        return objectMapper.writeValueAsString(scoreData);
    }

    public String grade1Semester2PerfectScore() throws JsonProcessingException {
        ScoreData scoreData = new ScoreData(
                subjectPerfectScore,
                null,
                subjectPerfectScore,
                subjectPerfectScore,
                subjectPerfectScore,
                createBigDecimalArray(new int[] {5,5,5,5,5,5,5,5,5}),
                createBigDecimalArray(new int[] {0,0,0}),
                createBigDecimalArray(new int[] {0,0,0,0,0,0,0,0,0}),
                createBigDecimalArray(new int[] {0,0,0}),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                "자유학기제",
                "1-2"
        );

        return objectMapper.writeValueAsString(scoreData);
    }

    public String grade2Semester1PerfectScore() throws JsonProcessingException {
        ScoreData scoreData = new ScoreData(
                subjectPerfectScore,
                subjectPerfectScore,
                null,
                subjectPerfectScore,
                subjectPerfectScore,
                createBigDecimalArray(new int[] {5,5,5,5,5,5,5,5,5}),
                createBigDecimalArray(new int[] {0,0,0}),
                createBigDecimalArray(new int[] {0,0,0,0,0,0,0,0,0}),
                createBigDecimalArray(new int[] {0,0,0}),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                "자유학기제",
                "2-1"
        );

        return objectMapper.writeValueAsString(scoreData);
    }

    private List<BigDecimal> createBigDecimalArray(int[] array) {
        List<BigDecimal> result = new ArrayList<>();
        for (int i : array) {
            result.add(BigDecimal.valueOf(i));
        }

        return result;
    }
}
