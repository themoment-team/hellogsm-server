package team.themoment.hellogsm.entity.domain.application.entity.grade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.themoment.hellogsm.entity.domain.application.entity.grade.data.GedScoreData;

import java.math.BigDecimal;

public class GedAdmissionGradeMock {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String maxScore() throws JsonProcessingException {
        GedScoreData gedScoreData = new GedScoreData(
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(261)
        );

        return objectMapper.writeValueAsString(gedScoreData);
    }
}
