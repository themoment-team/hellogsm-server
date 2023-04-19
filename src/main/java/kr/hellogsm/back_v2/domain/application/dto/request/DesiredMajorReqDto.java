package kr.hellogsm.back_v2.domain.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import kr.hellogsm.back_v2.domain.application.enums.Major;

/**
 * 전공 지망 순위를 저장하는 dto 입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
public record DesiredMajorReqDto(
        @NotBlank
        Major firstDesiredMajor,

        @NotBlank
        Major secondDesiredMajor,

        @NotBlank
        Major thirdDesiredMajor
) {}
