package kr.hellogsm.back_v2;

import kr.hellogsm.back_v2.global.schedule.ScheduleEnvironment;
import kr.hellogsm.back_v2.global.security.auth.AuthEnvironment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ScheduleEnvironment.class, AuthEnvironment.class})
public class HelloGsmBackV2Application {

    public static void main(String[] args) {
        SpringApplication.run(HelloGsmBackV2Application.class, args);
    }

}
