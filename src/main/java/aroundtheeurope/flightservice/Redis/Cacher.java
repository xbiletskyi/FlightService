package aroundtheeurope.flightservice.Redis;

import aroundtheeurope.flightservice.Models.DepartureInfo;
import aroundtheeurope.flightservice.Models.RyanairOperatedAirportsResponse.ArrivalAirport;
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
    public Cacher(
            RedisTemplate<String, String> redisTemplate,
            ObjectMapper objectMapper
    ) {
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
    public List<DepartureInfo> retrieveCache(
            String origin,
            String departureAt
    ) {
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
     * Retrieves cached flight data with destination from Redis.
     *
     * @param origin the origin airport code
     * @param destination the destination airport code
     * @param departureAt the departure date
     * @return the list of FlightFares if found in cache, otherwise null
     */
    public List<DepartureInfo> retrieveCache(String origin, String destination, String departureAt) {
        String key = generateKeyWithDestination(origin, destination, departureAt);
        String cachedData = redisTemplate.opsForValue().get(key);
        if (cachedData != null) {
            try {
                return objectMapper.readValue(cachedData, new TypeReference<List<DepartureInfo>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            key = generateKey(origin, departureAt);
            cachedData = redisTemplate.opsForValue().get(key);
            if (cachedData != null) {
                try {
                    List<DepartureInfo> departures = objectMapper.readValue(cachedData, new TypeReference<List<DepartureInfo>>() {});
                    List<DepartureInfo> withDestination = departures.stream().filter(flight -> flight.getDestinationAirportCode().equals(destination)).toList();
                    storeCache(origin, destination, departureAt, withDestination);
                    return withDestination;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Retrieves cached destination airports from Redis.
     *
     * @param origin the origin airport code
     * @return the list of ArrivalAirports if found in cache, otherwise null
     */
    public List<ArrivalAirport> retrieveOperatedAirportsCache(String origin) {
        String key = generateKeyForOperatedAirport(origin);
        String cachedData = redisTemplate.opsForValue().get(key);
        if (cachedData != null) {
            try {
                return objectMapper.readValue(cachedData, new TypeReference<List<ArrivalAirport>>() {});
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
    public void storeCache(
            String origin,
            String departureAt,
            List<DepartureInfo> flightData
    ) {
        String key = generateKey(origin, departureAt);
        try {
            String value = objectMapper.writeValueAsString(flightData);
            redisTemplate.opsForValue().set(key, value, timeoutHours, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores flight data with a destination in Redis cache.
     *
     * @param origin the origin airport code
     * @param destination the destination airport code
     * @param departureAt the departure date
     * @param flightData the list of FlightFares to cache
     */
    public void storeCache(String origin, String destination, String departureAt, List<DepartureInfo> flightData) {
        String key = generateKeyWithDestination(origin, destination, departureAt);
        try {
            String value = objectMapper.writeValueAsString(flightData);
            redisTemplate.opsForValue().set(key, value, timeoutHours, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores operated airports from a certain destination in Redis cache.
     *
     * @param origin the origin airport code
     * @param flightData the list of ArrivalAirports to cache
     */
    public void storeOperatedAirportsCache(String origin, List<ArrivalAirport> flightData) {
        String key = generateKeyForOperatedAirport(origin);
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
    private String generateKey(
            String origin,
            String departureAt
    ){
        return origin + ":" + departureAt;
    }

    /**
     * Generates a unique key for caching based on origin, destination and departure date.
     *
     * @param origin the origin airport code
     * @param destination the destination airport code
     * @param departureAt the departure date
     * @return the generated cache key
     */
    private String generateKeyWithDestination(String origin, String destination, String departureAt){
        return origin + "-" + destination + ":" + departureAt;
    }

    /**
     * Generates a unique key for caching operated airports based on origin.
     *
     * @param origin the origin airport code
     * @return the generated cache key
     */
    private String generateKeyForOperatedAirport(String origin){
        return origin + "-operated";
    }
}
