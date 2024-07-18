package aroundtheeurope.retrievedepartures.Configurations.Redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration class for setting up ObjectMapper for Redis.
 */
@Configuration
public class RedisObjectMapperConfig {

    /**
     * Creates and configures an ObjectMapper bean for Redis.
     *
     * @return the configured ObjectMapper
     */
    @Bean
    public ObjectMapper redisObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.deactivateDefaultTyping(); // Disable default typing to avoid class information
        return objectMapper;
    }
}
