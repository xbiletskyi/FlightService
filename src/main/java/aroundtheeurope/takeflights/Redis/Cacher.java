package aroundtheeurope.takeflights.Redis;

import aroundtheeurope.takeflights.Models.FlightFaresRyanair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Cacher {
    private final RedisTemplate<String, List<FlightFaresRyanair>> redisTemplate;
    private final long timeoutHours;

    @Autowired
    public Cacher(RedisTemplate<String, List<FlightFaresRyanair>> redisTemplate, @Value("${cache.timeout.hours}") long timeoutHours) {
        this.redisTemplate = redisTemplate;
        this.timeoutHours = timeoutHours;
    }

    public List<FlightFaresRyanair> retrieveCache(String origin, String departureAt) {
        String cacheKey = origin + ":" + departureAt;
        return redisTemplate.opsForValue().get(cacheKey);
    }

    public void storeCache(String origin, String departureAt, List<FlightFaresRyanair> flights) {
        String cacheKey = origin + ":" + departureAt;
        redisTemplate.opsForValue().set(cacheKey, flights, timeoutHours, TimeUnit.HOURS);
    }
}