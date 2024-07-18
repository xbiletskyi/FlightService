package aroundtheeurope.retrievedepartures.Configurations.Redis;

import aroundtheeurope.retrievedepartures.Models.DepartureInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

/**
 * Configuration class for setting up Redis.
 */
@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.port}")
    private int redisPort;

    /**
     * Creates and configures a LettuceConnectionFactory bean.
     *
     * @return the configured LettuceConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(2))
                .shutdownTimeout(Duration.ZERO)
                .build();

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", redisPort), clientConfig);
    }

    /**
     * Creates and configures a RedisTemplate bean for serializing and deserializing flight fares.
     *
     * @param lettuceConnectionFactory the connection factory for Redis
     * @param redisObjectMapper the ObjectMapper for Redis serialization
     * @return the configured RedisTemplate
     */
    @Bean
    public RedisTemplate<String, List<DepartureInfo>> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory, ObjectMapper redisObjectMapper) {
        RedisTemplate<String, List<DepartureInfo>> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(redisObjectMapper));
        return template;
    }
}
