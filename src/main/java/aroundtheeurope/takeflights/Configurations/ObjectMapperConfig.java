package aroundtheeurope.takeflights.Configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up ObjectMapper.
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * Creates and configures an ObjectMapper bean.
     *
     * @return the configured ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper;
    }
}
