package team.themoment.hellogsm.entity.domain.application.entity.admission;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertFalse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import team.themoment.hellogsm.entity.domain.application.enums.Major;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code AdmissionInfo}의 희망 학과를 저장하는 embedded type입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@Slf4j
@Getter
public class DesiredMajor {


    @Enumerated(EnumType.STRING)
    @Column(name = "first_desired_major", nullable = false)
    private Major firstDesiredMajor;


    @Enumerated(EnumType.STRING)
    @Column(name = "second_desired_major", nullable = false)
    private Major secondDesiredMajor;


    @Enumerated(EnumType.STRING)
    @Column(name = "third_desired_major", nullable = false)
    private Major thirdDesiredMajor;

    // 조건보다 @AssertTrue가 먼저 실행되서 NullPointerException 이 발생할 수 있음
    // 일단 null 여부를 확인하는 validation을 추가하긴 했는데, DTO에서 이미 확인하기 때문에 중복이라 좀 그럼
    // @GroupSequence 사용해서 순서 정해주거나, 다른 방법 찾아보기
    @AssertFalse(message = "Null인 전공이 있습니다")
    private boolean hasNullMajor() {
        if (firstDesiredMajor == null || secondDesiredMajor == null || thirdDesiredMajor == null) {
            return true;
        }
        return false;
    }

    @AssertFalse(message = "중복된 전공이 있습니다")
    private boolean hasSameMajor() {
        if (hasNullMajor()) return false; // null이면 그냥 넘겨서 hasNullMajor()가 처리하게 유도
        List<Major> majors = List.of(firstDesiredMajor, secondDesiredMajor, thirdDesiredMajor);
        Set<Major> deduplicationMajors = new HashSet<>(majors);
        // 중복 체크를 위해 Set의 크기와 원본 리스트의 크기를 비교
        return deduplicationMajors.size() != majors.size();
    }
}
