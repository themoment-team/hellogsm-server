package kr.hellogsm.back_v2.global.schedule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *  일정 환경 설정 정보를 관리하기 위한 클래스입니다.
 *
 *  @author 양시준
 *  @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "schedule")
public final class ScheduleEnvironment {

    private final LocalDate startDateOfApplication;
    private final LocalDate endDateOfApplication;
    private final LocalDate firstEvaluationAnnouncementDate;
    private final LocalDate secondEvaluationDate;
    private final LocalDate finalResultAnnouncementDate;
    private final LocalTime startOfServiceOperationTime;
    private final LocalTime endOfServiceOperationTime;
}