package jmcunst.test.jwt.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private String allowedOrigins;
    private String allowedMethods;
    private String allowedHeaders;
    private Long maxAge;
}
