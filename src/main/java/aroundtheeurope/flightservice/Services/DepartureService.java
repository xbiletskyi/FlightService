package aroundtheeurope.flightservice.Services;

import aroundtheeurope.flightservice.Models.DepartureInfo;
import aroundtheeurope.flightservice.Models.RyanairResponseModels.Fare;
import aroundtheeurope.flightservice.Models.RyanairResponseModels.RyanairResponse;
import aroundtheeurope.flightservice.Redis.Cacher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class to find the cheapest flights.
 */
@Service
public class DepartureService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Cacher cacher;

    @Value("${ryanair.request.url}")
    private String API_URL;

    @Value("${ryanair.request.parameters}")
    private String REQUEST_PARAMETERS;

    private static final List<String> SCHENGEN_COUNTRIES = List.of("at", "be", "bg", "cz", "hr", "dk", "ee",
            "fi", "fr", "de", "gr", "hu", "is", "it", "lv", "li", "lt", "lu", "mt", "nl", "no", "pl", "pt", "ro", "sk",
            "si", "es", "se", "ch");

    /**
     * Constructor for FlightService.
     *
     * @param restTemplate the RestTemplate to make HTTP requests
     * @param objectMapper the ObjectMapper to serialize and deserialize objects
     * @param cacher the Cacher to handle caching
     */
    @Autowired
    public DepartureService(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            Cacher cacher
    ) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.cacher = cacher;
    }

    /**
     * Finds the cheapest flights from a specified origin on a given departure date.
     * Optionally filters results to include only flights within the Schengen Area.
     *
     * @param origin the IATA code of the departure airport
     * @param departureAt the departure date
     * @param daysRange number of days in date range
     * @param schengenOnly if true, only includes flights within the Schengen Area
     * @return the list of FlightFares
     */
    public List<DepartureInfo> getDepartures(
            String origin,
            String departureAt,
            int daysRange,
            boolean schengenOnly
    ) {
        List<DepartureInfo> allDepartures = new ArrayList<>();
        LocalDate currentDate = LocalDate.parse(departureAt, DateTimeFormatter.ISO_LOCAL_DATE);
        for (int i = 0; i < daysRange; i++) {
            List<DepartureInfo> dayDepartures = cacher.retrieveCache(origin, currentDate.toString());
            if (dayDepartures == null) {
                dayDepartures = getRyanairOnDayDepartures(origin, currentDate.toString());
                if (dayDepartures != null) {
                    cacher.storeCache(origin, currentDate.toString(), dayDepartures);
                }
            }

            if (schengenOnly){
                dayDepartures = dayDepartures.stream().filter(flight -> SCHENGEN_COUNTRIES
                        .contains(flight.getDestinationCountryCode())).collect(Collectors.toList());
            }
            allDepartures.addAll(dayDepartures);
            currentDate = currentDate.plusDays(1);
        }

        return allDepartures;
    }

    /**
     * Retrieves Ryanair flights from the external API.
     *
     * @param origin the IATA code of the departure airport
     * @param departureAt the departure date
     * @return the list of FlightFares
     */
    public List<DepartureInfo> getRyanairOnDayDepartures(
            String origin,
            String departureAt
    ) {
        String url = API_URL + REQUEST_PARAMETERS + "&departureAirportIataCode=" + origin +
                "&outboundDepartureDateFrom=" + departureAt + "&outboundDepartureDateTo=" + departureAt;
        String response = restTemplate.getForObject(url, String.class);
        List<DepartureInfo> departures = new ArrayList<>();
        try{
            RyanairResponse ryanairResponse = objectMapper.readValue(response, RyanairResponse.class);
            List<Fare> fares = ryanairResponse.getFares();
            for (Fare fare : fares) {
                DepartureInfo departureInfo = DepartureInfo.createFromRyanAirFare(fare);
                departures.add(departureInfo);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return departures;
    }
}
