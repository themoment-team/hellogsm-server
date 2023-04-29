package kr.hellogsm.back_v2.domain.application.entity.grade;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

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


    @Column(name = "ged_total_score", nullable = true)
    private BigDecimal gedTotalScore;


    @Column(name = "ged_max_score", nullable = true)
    private BigDecimal gedMaxScore;

    public GedAdmissionGrade(MiddleSchoolGrade middleSchoolGrade) {

    }

    public GedAdmissionGrade(Long id, BigDecimal totalScore, BigDecimal percentileRank, BigDecimal gedTotalScore, BigDecimal gedMaxScore) {
        super(id, totalScore, percentileRank);
        this.gedTotalScore = gedTotalScore;
        this.gedMaxScore = gedMaxScore;
    }
}