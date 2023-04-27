package kr.hellogsm.back_v2.domain.application.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.hellogsm.back_v2.domain.application.enums.Major;

/**
 * 희망 전공을 입력받는 dto
 *
 * @author 변찬우
 * @since 1.0.0
 */
public record DesiredMajorReqDto(
        @Enumerated(EnumType.STRING)
        Major firstDesiredMajor,

        @Enumerated(EnumType.STRING)
        Major secondDesiredMajor,

        @Enumerated(EnumType.STRING)
        Major thirdDesiredMajor
) {
}
