package team.themoment.hellogsm.web.global.schedule;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;


/**
 * 일정 환경 설정 정보를 관리하기 위한 클래스입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */

@ConfigurationProperties(prefix = "schedule")
public record ScheduleEnvironment(
        LocalDate startReceptionDate,
        LocalDate endReceptionDate,
        LocalDate firstEvaluationAnnouncementDate,
        LocalDate finalEvaluationRunDate,
        LocalDate finalResultAnnouncementDate
) {
}
