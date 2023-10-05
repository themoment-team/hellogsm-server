package team.themoment.hellogsm.web.global.thirdParty.aws.xray.config;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.jakarta.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.plugins.EC2Plugin;
import com.amazonaws.xray.slf4j.SLF4JSegmentListener;
import com.amazonaws.xray.strategy.LogErrorContextMissingStrategy;
import com.amazonaws.xray.strategy.jakarta.SegmentNamingStrategy;
import com.amazonaws.xray.strategy.sampling.CentralizedSamplingStrategy;
import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.URL;

@Configuration
public class AWSXRayConfig {

    private final static String prefix = "AWS-XRAY-TRACE-ID";

    @Bean
    public AWSXRayServletFilter TracingFilter() {
        return new AWSXRayServletFilter(SegmentNamingStrategy.dynamic("HelloGSM-Web-BE"));
    }

    @PostConstruct
    public void init() {
        AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard().withPlugin(new EC2Plugin());

        URL ruleFile;
        try {
            ruleFile = ResourceUtils.getURL("classpath:sampling-rules.json");
            builder.withSamplingStrategy(new CentralizedSamplingStrategy(ruleFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        builder.withSamplingStrategy(new LocalizedSamplingStrategy(ruleFile));
        builder.withSegmentListener(new SLF4JSegmentListener(prefix));
        builder.withContextMissingStrategy(new LogErrorContextMissingStrategy());
        AWSXRay.setGlobalRecorder(builder.build());

        AWSXRay.endSegment();
    }
}