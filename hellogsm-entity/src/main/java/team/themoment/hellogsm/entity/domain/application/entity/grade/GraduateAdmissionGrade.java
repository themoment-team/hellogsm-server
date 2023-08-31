package team.themoment.hellogsm.entity.domain.application.entity.grade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import team.themoment.hellogsm.entity.domain.application.entity.grade.data.GeneralScoreData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 졸업예정, 졸업 학생의 입학 원서 성적을 저장하는 서브타입의 Entity입니다.
 *
 * @author 양시준, 변찬우
 * @since 1.0.0
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue("GRADUATE")
@Getter
@SuperBuilder
@ToString
public class GraduateAdmissionGrade extends AdmissionGrade {

    @Column(name = "grade_1_semester_1_score", nullable = false)
    private BigDecimal grade1Semester1Score;  // 1 grade(학년) 1 semester(학기) 점수

    @Column(name = "grade_1_semester_2_score", nullable = false)
    private BigDecimal grade1Semester2Score;

    @Column(name = "grade_2_semester_1_score", nullable = false)
    private BigDecimal grade2Semester1Score;

    @Column(name = "grade_2_semester_2_score", nullable = false)
    private BigDecimal grade2Semester2Score;

    @Column(name = "grade_3_semester_1_score", nullable = false)
    private BigDecimal grade3Semester1Score;

    @Column(name = "artistic_score", nullable = false)
    private BigDecimal artisticScore;

    @Column(name = "curricular_subtotal_score", nullable = false)
    private BigDecimal curricularSubtotalScore;

    @Column(name = "attendance_score", nullable = false)
    private BigDecimal attendanceScore;

    @Column(name = "volunteer_score", nullable = false)
    private BigDecimal volunteerScore;

    @Column(name = "extracurricular_subtotal_score", nullable = false)
    private BigDecimal extracurricularSubtotalScore;

    public GraduateAdmissionGrade(MiddleSchoolGrade middleSchoolGrade) {
        ObjectMapper objectMapper = new ObjectMapper();
        GeneralScoreData result = null;
        try {
            result = objectMapper.readValue(middleSchoolGrade.getMiddleSchoolGradeText(), GeneralScoreData.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("MiddleSchoolGrade값이 올바르지 않습니다");
        }

        grade1Semester1Score = calc(result.score1_1(),BigDecimal.valueOf(18));
        grade1Semester2Score = calc(result.score1_2(), result.freeSemester().equals("2-1") ? BigDecimal.valueOf(36) : BigDecimal.valueOf(18));
        grade2Semester1Score = calc(result.score2_1(), result.system().equals("자유학년제") ? BigDecimal.valueOf(54) : BigDecimal.valueOf(36));
        grade2Semester2Score = calc(result.score2_2(),BigDecimal.valueOf(54));
        grade3Semester1Score = calc(result.score3_1(), BigDecimal.valueOf(72));

        BigDecimal generalCurriculumScoreSubtotal = Stream.of(
                        grade1Semester1Score,
                        grade1Semester2Score,
                        grade2Semester1Score,
                        grade2Semester2Score,
                        grade3Semester1Score).reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(3, RoundingMode.HALF_UP);

        // 예체능 성적
        artisticScore = calcArtSports(result.artSportsScore());

        // 예체능 성적 + 교과 성적
        curricularSubtotalScore = artisticScore
                .add(generalCurriculumScoreSubtotal)
                .setScale(3, RoundingMode.HALF_UP);

        // 출결 성적
        attendanceScore = calcAttendance(result.absentScore(), result.attendanceScore()).setScale(3, RoundingMode.HALF_UP);

        // 봉사 성적
        volunteerScore = calcVolunteer(result.volunteerScore()).setScale(3, RoundingMode.HALF_UP);

        // 비 교과 성적
        extracurricularSubtotalScore = attendanceScore.add(volunteerScore).setScale(3, RoundingMode.HALF_UP);

        totalScore = curricularSubtotalScore.add(extracurricularSubtotalScore).setScale(3, RoundingMode.HALF_UP);

        percentileRank = calcPercentileRank(totalScore);
    }

    private BigDecimal calc(List<BigDecimal> scoreArray, BigDecimal maxPoint) {
        if (scoreArray == null || scoreArray.isEmpty()) return BigDecimal.valueOf(0).setScale(3, RoundingMode.HALF_UP);
        List<BigDecimal> noZeroScoreArray = scoreArray.stream().filter((score) -> score.compareTo(BigDecimal.ZERO) != 0).toList();
        if (noZeroScoreArray.isEmpty()) return BigDecimal.valueOf(0).setScale(3, RoundingMode.HALF_UP);

        // (A 개수 * 5) + (B 개수 * 4) + (C 개수 * 3) + (D 개수 * 2) + (E 개수 * 1)
        BigDecimal reduceResult = scoreArray.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        // reduceResult / (과목 수 * 5) (소수점 7째자리 반올림)
        BigDecimal divideResult = reduceResult.divide(BigDecimal.valueOf(noZeroScoreArray.size() * 5L), 6, RoundingMode.HALF_UP);
        // 각 학년 당 배점 * divideResult (소수점 4째자리에서 반올림)
        return divideResult.multiply(maxPoint).setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal calcArtSports(List<BigDecimal> scoreArray) {
        if (scoreArray == null) return BigDecimal.valueOf(0).setScale(3, RoundingMode.HALF_UP);

        // (A의 개수 * 5) + (B의 개수 * 4) + (C의 개수 * 3)
        BigDecimal reduceResult = scoreArray.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        // reduceResult / (개수 * 5) (소수점 4째 자리에서 반올림)
        scoreArray = scoreArray.stream()
                .filter(score -> BigDecimal.ZERO.compareTo(score) != 0)
                .collect(Collectors.toList());
        BigDecimal divideResult = reduceResult.divide(BigDecimal.valueOf(scoreArray.size() * 5L), 3, RoundingMode.HALF_UP);
        return BigDecimal.valueOf(60).multiply(divideResult).setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal calcAttendance(List<BigDecimal> absentScore, List<BigDecimal> attendanceScore) {
        BigDecimal absent = absentScore.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        // 결석이 10번 이상이면 0점
        if (absent.compareTo(BigDecimal.valueOf(10)) != -1) return BigDecimal.valueOf(0);

        // 모든 출결 더하기
        BigDecimal attendance = attendanceScore.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        // 출결은 3개당 결석 1
        BigDecimal absentResult = attendance.divide(BigDecimal.valueOf(3), 0, RoundingMode.DOWN);

        // 총 점(30점) - (3 * 총 결석)
        BigDecimal result = BigDecimal.valueOf(30).subtract(absent.add(absentResult).multiply(BigDecimal.valueOf(3)));

        if (result.compareTo(BigDecimal.ZERO) != 1) return BigDecimal.ZERO;
        return result;
    }

    private BigDecimal calcVolunteer(List<BigDecimal> scoreArray) {
        return scoreArray.stream().reduce(BigDecimal.valueOf(0), (current, hour) -> {
            // 7 이상
            if (hour.compareTo(new BigDecimal("7")) >= 0)
                return current.add(BigDecimal.valueOf(10));
                // 6시간 이상
            else if (hour.compareTo(new BigDecimal("6")) >= 0)
                return current.add(BigDecimal.valueOf(8));
                // 5시간 이상
            else if (hour.compareTo(new BigDecimal("5")) >= 0)
                return current.add(BigDecimal.valueOf(6));
                // 4시간 이상
            else if (hour.compareTo(new BigDecimal("4")) >= 0)
                return current.add(BigDecimal.valueOf(4));
                // 기본 접수
            else return current.add(BigDecimal.valueOf(2));
        });
    }

    private BigDecimal calcPercentileRank(BigDecimal scoreTotal) {
        // (1 - scoreTotal / 300) * 100 (소수점 넷째 자리에서 반올림)
        return BigDecimal.ONE.subtract(scoreTotal.divide(BigDecimal.valueOf(300), 20, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(100)).setScale(3, RoundingMode.HALF_UP);
    }
}

