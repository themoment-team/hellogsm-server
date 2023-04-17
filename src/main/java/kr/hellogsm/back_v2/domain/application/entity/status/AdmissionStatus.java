package kr.hellogsm.back_v2.domain.application.entity.status;


import jakarta.persistence.*;
import kr.hellogsm.back_v2.domain.application.enums.EvaluationStatus;
import kr.hellogsm.back_v2.domain.application.enums.Major;
import lombok.*;

import java.math.BigDecimal;

/**
 * 입학 원서의 상태를 저장하는 Entity입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class AdmissionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_status_id")
    private Long id;

    @Column(name = "is_final_submitted", nullable = false)
    private Boolean isFinalSubmitted;

    @Column(name = "is_prints_arrived", nullable = false)
    private Boolean isPrintsArrived;

    @Column(name = "first_evaluation", nullable = false)
    private EvaluationStatus firstEvaluation;

    @Column(name = "second_evaluation", nullable = false)
    private EvaluationStatus secondEvaluation;

    @Column(name = "registration_number", nullable = true)
    private Long registrationNumber;  // 접수 번호, 원서 제출 기간 후 배정됨

    @Column(name = "second_score", nullable = true) // TODO 이름 바꾸기 - 2차 시험 점수
    private BigDecimal secondScore;

    @Column(name = "final_major", nullable = true)
    private Major finalMajor; // "배정되지 않음" 상태를 표현하기 애패해서 일단은 null로 둠

    /**
     * 초기 상태의 {@code AdmissionStatus}를 생성합니다.
     *
     * @return 초기 상태의 {@code AdmissionStatus} 객체
     */
    public static AdmissionStatus init() {
        return AdmissionStatus.builder()
                .id(null)
                .isFinalSubmitted(false)
                .isPrintsArrived(false)
                .firstEvaluation(EvaluationStatus.NOT_YET)
                .secondEvaluation(EvaluationStatus.NOT_YET)
                .finalMajor(null)
                .build();
    }

    public Boolean isFinalSubmitted() {
        return isFinalSubmitted;
    }

    public Boolean isPrintsArrived() {
        return isPrintsArrived;
    }
}