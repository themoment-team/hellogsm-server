package team.themoment.hellogsm.entity.domain.application.entity.grade;

import jakarta.persistence.*;
import lombok.*;

/**
 * 입학 원서의 중학교 성적을 저장하는 Entity입니다.
 */
@Entity
@Table(name = "middle_school_grade")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class MiddleSchoolGrade {

    @Id
    @Column(name = "middle_school_grade_id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "middle_school_grade_text", nullable = false, length = 10000)
    private String middleSchoolGradeText;
}

