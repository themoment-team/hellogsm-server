package team.themoment.hellogsm.entity.domain.application.entity.admission;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertFalse;
import lombok.*;
import team.themoment.hellogsm.entity.domain.application.enums.Major;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code AdmissionInfo}의 희망 학과를 저장하는 embedded type입니다.
 */

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
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

    @AssertFalse(message = "모든 필드는 정의되어야 합니다. null일 수 없습니다.")
    private boolean hasNullMajor() {
        if (firstDesiredMajor == null || secondDesiredMajor == null || thirdDesiredMajor == null) {
            return true;
        }
        return false;
    }

    @AssertFalse(message = "Major의 타입은 한 번만 사용되어야 합니다. 같은 타입을 중복하여 사용할 수 없습니다.")
    private boolean hasSameMajor() {
        if (hasNullMajor()) return false; // NPE 방지를 위해서 사용, 어차피 false로 통과시켜도 다른 유효성 검사에서 catch 됨
        List<Major> majors = List.of(firstDesiredMajor, secondDesiredMajor, thirdDesiredMajor);
        Set<Major> deduplicationMajors = new HashSet<>(majors);
        return deduplicationMajors.size() != majors.size();
    }
}
