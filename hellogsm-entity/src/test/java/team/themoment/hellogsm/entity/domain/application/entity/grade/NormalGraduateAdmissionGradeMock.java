package team.themoment.hellogsm.entity.domain.application.entity.grade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.themoment.hellogsm.entity.domain.application.entity.grade.data.GeneralScoreData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NormalGraduateAdmissionGradeMock {
    ObjectMapper objectMapper = new ObjectMapper();

    public String 추가과목_X_1학년() throws JsonProcessingException {
        GeneralScoreData value = new GeneralScoreData(
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{4, 5, 3, 5, 4, 5, 3, 5}),
                createBigDecimalArray(new int[]{5, 2, 5, 5, 4, 1, 5, 5}),
                createBigDecimalArray(new int[]{3, 5, 3, 5, 1, 3, 5, 2}),
                createBigDecimalArray(new int[]{5, 4, 5, 3, 5, 0, 5, 3, 5}),
                createBigDecimalArray(new int[]{2, 0, 0}),
                createBigDecimalArray(new int[]{0, 4, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{7, 3, 4}),
                List.of("국어", "도덕", "사회", "역사", "수학", "과학", "기술가정", "영어"),
                List.of(),
                List.of("체육", "미술", "음악"),
                "자유학년제",
                null
        );

        return objectMapper.writeValueAsString(value);
    }

    public String 추가과목_O_1학년() throws JsonProcessingException {
        GeneralScoreData value = new GeneralScoreData(
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{4, 3, 3, 5, 5, 5, 1, 5, 0, 5}),
                createBigDecimalArray(new int[]{5, 2, 5, 5, 2, 1, 5, 5, 5, 0}),
                createBigDecimalArray(new int[]{3, 5, 2, 3, 1, 3, 5, 2, 5, 0}),
                createBigDecimalArray(new int[]{5, 4, 5, 3, 5, 4, 5, 3, 5}),
                createBigDecimalArray(new int[]{2, 0, 0}),
                createBigDecimalArray(new int[]{0, 4, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{100, 9, 9}),
                List.of("국어", "도덕", "사회", "역사", "수학", "과학", "기술가정", "영어"),
                List.of("한문", "중국어"),
                List.of("체육", "미술", "음악"),
                "자유학년제",
                "1-1"
        );

        return objectMapper.writeValueAsString(value);
    }

    public String 추가과목_X_1학년_1학기() throws JsonProcessingException {
        GeneralScoreData value = new GeneralScoreData(
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{3, 1, 5, 3, 5, 1, 5, 5}),
                createBigDecimalArray(new int[]{3, 2, 1, 5, 5, 3, 5, 3}),
                createBigDecimalArray(new int[]{5, 3, 2, 5, 2, 5, 5, 2}),
                createBigDecimalArray(new int[]{4, 3, 3, 5, 1, 5, 3, 5}),
                createBigDecimalArray(new int[]{5, 3, 5, 5, 3, 5, 4, 5, 5}),
                createBigDecimalArray(new int[]{0, 9, 0}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{0, 100, 0}),
                List.of("국어", "도덕", "사회", "역사", "수학", "과학", "기술가정", "영어"),
                List.of(),
                List.of("체육", "미술", "음악"),
                "자유학기제",
                "1-1"
        );

        return objectMapper.writeValueAsString(value);
    }



    public String 추가과목_O_1학년_1학기() throws JsonProcessingException {
        GeneralScoreData value = new GeneralScoreData(
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{3, 1, 5, 3, 5, 1, 5, 5, 5, 2, 0}),
                createBigDecimalArray(new int[]{3, 2, 1, 5, 5, 3, 5, 3, 2, 3, 0}),
                createBigDecimalArray(new int[]{5, 3, 2, 5, 2, 5, 5, 2, 5, 3, 1}),
                createBigDecimalArray(new int[]{4, 3, 3, 5, 1, 5, 3, 5, 3, 5, 0}),
                createBigDecimalArray(new int[]{5, 3, 5, 5, 3, 5, 4, 5, 5}),
                createBigDecimalArray(new int[]{0, 9, 0}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{0, 100, 0}),
                List.of("국어", "도덕", "사회", "역사", "수학", "과학", "기술가정", "영어"),
                List.of("애니", "유튜브", "암산"),
                new ArrayList<String>(List.of("체육", "미술", "음악")),
                "자유학기제",
                "1-1"
        );

        return objectMapper.writeValueAsString(value);
    }

    public String 추가과목_X_1학년_2학기() throws JsonProcessingException {
        GeneralScoreData value = new GeneralScoreData(
                createBigDecimalArray(new int[]{5, 5, 3, 5, 3, 2, 5, 3}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{5, 0, 2, 5, 5, 3, 5, 5}),
                createBigDecimalArray(new int[]{5, 5, 3, 2, 5, 5, 3, 5}),
                createBigDecimalArray(new int[]{3, 2, 5, 3, 5, 5, 3, 5}),
                createBigDecimalArray(new int[]{5, 4, 5, 5, 0, 5, 4, 5, 0}),
                createBigDecimalArray(new int[]{0, 0, 0}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{10, 10, 10}),
                List.of("국어", "도덕", "사회", "역사", "수학", "과학", "기술가정", "영어"),
                List.of(),
                List.of("체육", "미술", "음악"),
                "자유학기제",
                "1-2"
        );

        return objectMapper.writeValueAsString(value);
    }

    public String 추가과목_O_1학년_2학기() throws JsonProcessingException {
        GeneralScoreData value = new GeneralScoreData(
                createBigDecimalArray(new int[]{5, 5, 3, 5, 3, 2, 5, 1, 5, 2, 5, 5}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{5, 0, 2, 5, 5, 3, 5, 5, 2, 2, 5, 0}),
                createBigDecimalArray(new int[]{5, 5, 3, 2, 5, 5, 3, 2, 5, 5, 4, 3}),
                createBigDecimalArray(new int[]{3, 2, 5, 3, 5, 3, 3, 5, 5, 2, 5, 2}),
                createBigDecimalArray(new int[]{5, 4, 5, 5, 0, 5, 4, 5, 0}),
                createBigDecimalArray(new int[]{0, 0, 0}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{10, 10, 10}),
                List.of("국어", "도덕", "사회", "역사", "수학", "과학", "기술가정", "영어"),
                List.of("케이팝", "덕후능력", "mbti", "테수투"),
                List.of("체육", "미술", "음악"),
                "자유학기제",
                "1-2"
        );

        return objectMapper.writeValueAsString(value);
    }

    public String 추가과목_X_2학년_1학기() throws JsonProcessingException {
        GeneralScoreData value = new GeneralScoreData(
                createBigDecimalArray(new int[]{5, 5, 1, 5, 3, 2, 5, 1}),
                createBigDecimalArray(new int[]{5, 5, 5, 5, 3, 5, 5, 5}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{5, 5, 3, 2, 5, 5, 3, 2}),
                createBigDecimalArray(new int[]{0, 2, 3, 1, 5, 3, 3, 5}),
                createBigDecimalArray(new int[]{5, 4, 5, 5, 0, 5, 4, 5, 0}),
                createBigDecimalArray(new int[]{0, 0, 0}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{10, 10, 10}),
                List.of("국어", "도덕", "사회", "역사", "수학", "과학", "기술가정", "영어"),
                List.of(),
                List.of("체육", "미술", "음악"),
                "자유학기제",
                "2-1"
        );

        return objectMapper.writeValueAsString(value);
    }



    public String 추가과목_O_2년_1학기() throws JsonProcessingException {
        GeneralScoreData value = new GeneralScoreData(
                createBigDecimalArray(new int[]{5, 5, 3, 5, 3, 2, 5, 1, 5, 2, 5, 5}),
                createBigDecimalArray(new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{5, 5, 3, 2, 5, 5, 3, 2, 5, 5, 4, 3}),
                createBigDecimalArray(new int[]{3, 2, 5, 3, 5, 3, 3, 5, 5, 2, 5, 2}),
                createBigDecimalArray(new int[]{5, 4, 5, 5, 0, 5, 4, 5, 0}),
                createBigDecimalArray(new int[]{0, 0, 0}),
                createBigDecimalArray(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}),
                createBigDecimalArray(new int[]{10, 10, 10}),
                List.of("국어", "도덕", "사회", "역사", "수학", "과학", "기술가정", "영어"),
                List.of("케이팝", "덕후능력", "mbti", "테수투"),
                List.of("체육", "미술", "음악"),
                "자유학기제",
                "2-1"
        );

        return objectMapper.writeValueAsString(value);
    }

    private List<BigDecimal> createBigDecimalArray(int[] array) {
        List<BigDecimal> result = new ArrayList<>();
        for (int i : array) {
            result.add(BigDecimal.valueOf(i));
        }

        return result;
    }
}
