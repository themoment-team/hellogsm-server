package team.themoment.hellogsm.web.domain.identity.service;

public interface CodeNotificationService {
    void execute(String phoneNumber, String code);
}
