package team.themoment.hellogsm.web.global.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team.themoment.hellogsm.web.global.data.profile.ServerProfile;

@Configuration
public class WebResourceConfig {

    @Configuration
    @Profile(ServerProfile.PROD)
    public class LocalWebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            // 리소스 허용 X
        }

    }

    @Configuration
    @Profile({ServerProfile.DEV, ServerProfile.LOCAL})
    public class ProdWebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            restdocs(registry);
        }
    }

    private void restdocs(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/docs/{index:index}.html")
                .addResourceLocations("classpath:/static/docs/");
    }
}
