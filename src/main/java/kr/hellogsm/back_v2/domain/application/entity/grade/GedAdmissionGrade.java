package kr.hellogsm.back_v2.domain.application.entity.grade;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.hellogsm.back_v2.domain.application.service.data.GedScoreData;
import kr.hellogsm.back_v2.global.exception.error.ExpectedException;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static lombok.AccessLevel.*;

/**
 * 검정고시 학생의 입학 원서 성적을 저장하는 서브타입의 Entity입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue("GED")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@SuperBuilder
@ToString
public class GedAdmissionGrade extends AdmissionGrade {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Column(name = "ged_total_score", nullable = true)
    private BigDecimal gedTotalScore;


    @Column(name = "ged_max_score", nullable = true)
    private BigDecimal gedMaxScore;

    public GedAdmissionGrade(MiddleSchoolGrade middleSchoolGrade) {
        GedScoreData result;
        try {
            result = objectMapper.readValue(middleSchoolGrade.getMiddleSchoolGradeText(), GedScoreData.class);
        } catch (Exception e) {
            throw new ExpectedException("MiddleSchoolGrade값이 올바르지 않습니다", HttpStatus.BAD_REQUEST);
        }

        if (result.nonCurriculumScoreSubtotal().compareTo(result.curriculumScoreSubtotal()) > 0)
            throw new ExpectedException("계산 결과가 올바르지 않습니다", HttpStatus.BAD_REQUEST);

        gedTotalScore = result.curriculumScoreSubtotal();
        gedMaxScore = result.nonCurriculumScoreSubtotal();
        this.percentileRank = calcPercentileRank(result.curriculumScoreSubtotal(), result.nonCurriculumScoreSubtotal());
        this.totalScore = calcTotalScore(result.rankPercentage());

        if (result.rankPercentage().compareTo(this.percentileRank) != 0 || result.scoreTotal().compareTo(this.totalScore) != 0)
            throw new ExpectedException("계산 결과가 올바르지 않습니다", HttpStatus.BAD_REQUEST);
    }

    public GedAdmissionGrade(Long id, BigDecimal totalScore, BigDecimal percentileRank, BigDecimal gedTotalScore, BigDecimal gedMaxScore) {
        super(id, totalScore, percentileRank);
        this.gedTotalScore = gedTotalScore;
        this.gedMaxScore = gedMaxScore;
    }

    private BigDecimal calcPercentileRank(BigDecimal score, BigDecimal maxScore) {
        return maxScore
                .divide(score, 3, RoundingMode.HALF_DOWN)
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100));
    }

    // ((300 - (300 * rankpercentage) / 100) * 0.87).tofixed(3),
    private BigDecimal calcTotalScore(BigDecimal percentileRank) {
        BigDecimal a = percentileRank
                .multiply(BigDecimal.valueOf(300))
                .divide(BigDecimal.valueOf(100), 3, RoundingMode.HALF_UP);

        return BigDecimal.valueOf(300)
                .subtract(a)
                .multiply(BigDecimal.valueOf(0.87)).setScale(3, RoundingMode.HALF_UP);
    }
}