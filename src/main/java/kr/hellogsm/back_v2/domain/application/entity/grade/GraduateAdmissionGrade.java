package kr.hellogsm.back_v2.domain.application.entity.grade;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * 졸업예정, 졸업 학생의 입학 원서 성적을 저장하는 서브타입의 Entity입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue("GRADUATE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor//(access = AccessLevel.PRIVATE) 테스트용으로 주석
@SuperBuilder
@ToString
public class GraduateAdmissionGrade extends AdmissionGrade {


    @Column(name = "grade_1_semester_1_score", nullable = true)
    private BigDecimal grade1Semester1Score;  // 1 grade 1 semester 점수


    @Column(name = "grade_1_semester_2_score", nullable = true)
    private BigDecimal grade1Semester2Score;


    @Column(name = "grade_2_semester_1_score", nullable = true)
    private BigDecimal grade2Semester1Score;


    @Column(name = "grade_2_semester_2_score", nullable = true)
    private BigDecimal grade2Semester2Score;


    @Column(name = "grade_3_semester_1_score", nullable = true)
    private BigDecimal grade3Semester1Score;


    @Column(name = "artistic_score", nullable = true)
    private BigDecimal artisticScore;


    @Column(name = "curricular_subtotal_score", nullable = true)
    private BigDecimal curricularSubtotalScore;


    @Column(name = "attendance_score", nullable = true)
    private BigDecimal attendanceScore;


    @Column(name = "volunteer_score")
    private BigDecimal volunteerScore;


    @Column(name = "extracurricular_subtotal_score")
    private BigDecimal extracurricularSubtotalScore;

    public GraduateAdmissionGrade(MiddleSchoolGrade middleSchoolGrade) {
    }
}

