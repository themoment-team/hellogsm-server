package team.themoment.hellogsm.entity.domain.application.entity.grade;

import lombok.RequiredArgsConstructor;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;

/**
 * {@link AdmissionGrade}의 구현체를 생성하는 팩토리 클래스입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class AdmissionGradeFactory {

    /**
     * 졸업 상태에 맞는 {@link AdmissionGrade}의 구현체를 중학교 성적을 입력받아 생성합니다.
     *
     * @param graduationStatus   졸업 상태
     * @param middleSchoolGrades 중학교 성적
     * @return 생성된 입학 원서 성적 Entity
     * @throws IllegalArgumentException 지원하는 졸업 상태가 아닌 경우 예외 발생
     */
    public static AdmissionGrade create(GraduationStatus graduationStatus, MiddleSchoolGrade middleSchoolGrades) {
        if (graduationStatus.equals(GraduationStatus.CANDIDATE) || graduationStatus.equals(GraduationStatus.GRADUATE)) {
            return new GraduateAdmissionGrade(middleSchoolGrades);
        } else if (graduationStatus.equals(GraduationStatus.GED)) {
            return new GedAdmissionGrade(middleSchoolGrades);
        } else {
            throw new IllegalArgumentException("원서의 졸업싱태와 중학교 성적의 형식이 일치하지 않습니다.");
        }
    }
}
