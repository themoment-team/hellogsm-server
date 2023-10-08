package team.themoment.hellogsm.web.global.security.schedule;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;
import java.util.List;

@ConfigurationProperties(prefix = "schedule")
public record ScheduleEnvironment (
        LocalDate startReceptionDate,
        LocalDate endReceptionDate,
        LocalDate firstEvaluationAnnouncementDate,
        LocalDate finalEvaluationRunDate,
        LocalDate finalResultAnnouncementDate
) {
}
