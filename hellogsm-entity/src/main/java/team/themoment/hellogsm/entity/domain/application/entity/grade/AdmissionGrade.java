package team.themoment.hellogsm.entity.domain.application.entity.grade;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * 입학 원서의 점수을 저장하는 역할의 추상클래스입니다.
 */
@Entity
@Table(name = "admission_grade")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@ToString
public abstract class AdmissionGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admission_grade_id", nullable = false)
    protected Long id;

    @Digits(integer = 3, fraction = 3)
    @Column(name = "total_score", nullable = false)
    protected BigDecimal totalScore;

    @Digits(integer = 2, fraction = 3)
    @Column(name = "percentile_rank", nullable = false)
    protected BigDecimal percentileRank;
}
