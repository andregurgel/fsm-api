package dev.andregurgel.fsm_api.commons.infrastructure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("global.properties")
public class GlobalProperties {

    private final Routes routes = new Routes();

    @Data
    public static class Routes {
        private String appUrl = "http://localhost:4200";
        private String apiUrl = "http://localhost:8080";
    }
}
