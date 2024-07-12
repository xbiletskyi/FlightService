package aroundtheeurope.takeflights.Redis;

import aroundtheeurope.takeflights.Models.FlightFares;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Cacher {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    @Value("${cache.timeout.hours}")
    private long timeoutHours;

    @Autowired
    public Cacher(RedisTemplate<String, String> redisTemplate,
                  ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public List<FlightFares> retrieveCache(String origin, String departureAt) {
        String key = generateKey(origin, departureAt);
        String cachedData = redisTemplate.opsForValue().get(key);
        if (cachedData != null) {
            try {
                return objectMapper.readValue(cachedData, new TypeReference<List<FlightFares>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void storeCache(String origin, String departureAt, List<FlightFares> flightData) {
        String key = generateKey(origin, departureAt);
        try {
            String value = objectMapper.writeValueAsString(flightData);
            redisTemplate.opsForValue().set(key, value, timeoutHours, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateKey(String origin, String departureAt){
        return origin + ":" + departureAt;
    }
}
