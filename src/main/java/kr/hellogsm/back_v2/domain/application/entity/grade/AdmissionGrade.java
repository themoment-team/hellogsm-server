package kr.hellogsm.back_v2.domain.application.entity.grade;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * 학생의 입학 원서 성적을 저장하는 슈퍼타입의 Entity입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admission_grade")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@ToString
public abstract class AdmissionGrade {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admission_grade_id", nullable = false)
    protected Long id;

    
    @Column(name = "total_score", nullable = false)
    protected BigDecimal totalScore;

    
    @Column(name = "percentile_rank", nullable = false)
    protected BigDecimal percentileRank;

}

