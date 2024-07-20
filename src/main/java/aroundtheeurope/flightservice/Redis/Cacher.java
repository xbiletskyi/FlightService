package aroundtheeurope.flightservice.Redis;

import aroundtheeurope.flightservice.Models.DepartureInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Cacher class handles caching of flight data in Redis.
 */
@Component
public class Cacher {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    @Value("${cache.timeout.hours}")
    private long timeoutHours;

    /**
     * Constructor for Cacher.
     *
     * @param redisTemplate the RedisTemplate to interact with Redis
     * @param objectMapper the ObjectMapper to serialize and deserialize objects
     */
    @Autowired
    public Cacher(RedisTemplate<String, String> redisTemplate,
                  ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieves cached flight data from Redis.
     *
     * @param origin the origin airport code
     * @param departureAt the departure date
     * @return the list of FlightFares if found in cache, otherwise null
     */
    public List<DepartureInfo> retrieveCache(String origin, String departureAt) {
        String key = generateKey(origin, departureAt);
        String cachedData = redisTemplate.opsForValue().get(key);
        if (cachedData != null) {
            try {
                return objectMapper.readValue(cachedData, new TypeReference<List<DepartureInfo>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Stores flight data in Redis cache.
     *
     * @param origin the origin airport code
     * @param departureAt the departure date
     * @param flightData the list of FlightFares to cache
     */
    public void storeCache(String origin, String departureAt, List<DepartureInfo> flightData) {
        String key = generateKey(origin, departureAt);
        try {
            String value = objectMapper.writeValueAsString(flightData);
            redisTemplate.opsForValue().set(key, value, timeoutHours, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a unique key for caching based on origin and departure date.
     *
     * @param origin the origin airport code
     * @param departureAt the departure date
     * @return the generated cache key
     */
    private String generateKey(String origin, String departureAt){
        return origin + ":" + departureAt;
    }
}
