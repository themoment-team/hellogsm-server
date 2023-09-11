package team.themoment.hellogsm.web.global.thirdParty.aws.xray.config;

import com.amazonaws.xray.jakarta.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.strategy.jakarta.SegmentNamingStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class AWSXRayConfig {
    @Bean
    public AWSXRayServletFilter TracingFilter() {
        return new AWSXRayServletFilter(SegmentNamingStrategy.dynamic("HelloGSM-Web-BE"));
    }
}