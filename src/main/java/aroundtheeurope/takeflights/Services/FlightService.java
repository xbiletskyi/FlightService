package aroundtheeurope.takeflights.Services;

import aroundtheeurope.takeflights.Models.FlightFares;
import aroundtheeurope.takeflights.Models.RyanairResponseModels.Fare;
import aroundtheeurope.takeflights.Models.RyanairResponseModels.RyanairResponse;
import aroundtheeurope.takeflights.Redis.Cacher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class to find the cheapest flights.
 */
@Service
public class FlightService {
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
    public FlightService(RestTemplate restTemplate, ObjectMapper objectMapper, Cacher cacher) {
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
     * @param schengenOnly if true, only includes flights within the Schengen Area
     * @return the list of FlightFares
     */
    public List<FlightFares> findCheapestFlights(String origin, String departureAt, boolean schengenOnly) {
        List<FlightFares> allFlights = cacher.retrieveCache(origin, departureAt);
        if (allFlights == null) {
            allFlights = getRyanairFlights(origin, departureAt);
            if (allFlights != null) {
                cacher.storeCache(origin, departureAt, allFlights);
            }
        }

        if (schengenOnly){
            allFlights = allFlights.stream().filter(flight -> SCHENGEN_COUNTRIES
                    .contains(flight.getDestinationCountryCode())).collect(Collectors.toList());
        }
        return allFlights;
    }

    /**
     * Retrieves Ryanair flights from the external API.
     *
     * @param origin the IATA code of the departure airport
     * @param departureAt the departure date
     * @return the list of FlightFares
     */
    public List<FlightFares> getRyanairFlights(String origin, String departureAt) {
        String url = API_URL + REQUEST_PARAMETERS + "&departureAirportIataCode=" + origin +
                "&outboundDepartureDateFrom=" + departureAt + "&outboundDepartureDateTo=" + departureAt;
        String response = restTemplate.getForObject(url, String.class);
        List<FlightFares> flights = new ArrayList<>();
        try{
            RyanairResponse ryanairResponse = objectMapper.readValue(response, RyanairResponse.class);
            List<Fare> fares = ryanairResponse.getFares();
            for (Fare fare : fares) {
                FlightFares flightFare = new FlightFares(
                        fare.getOutbound().getFlightNumber(),
                        fare.getOutbound().getDepartureDate(),
                        fare.getOutbound().getDepartureAirport().getName(),
                        fare.getOutbound().getDepartureAirport().getIataCode(),
                        fare.getOutbound().getDepartureAirport().getCity().getCountryCode(),
                        fare.getOutbound().getArrivalAirport().getName(),
                        fare.getOutbound().getArrivalAirport().getIataCode(),
                        fare.getOutbound().getArrivalAirport().getCity().getCountryCode(),
                        fare.getOutbound().getPrice().getValue(),
                        fare.getOutbound().getPrice().getCurrencyCode()
                );
                flights.add(flightFare);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return flights;
    }
}
