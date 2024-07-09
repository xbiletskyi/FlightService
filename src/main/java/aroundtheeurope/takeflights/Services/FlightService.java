package aroundtheeurope.takeflights.Services;

import aroundtheeurope.takeflights.Models.FlightFares;
import aroundtheeurope.takeflights.Models.RyanairResponseModels.Fare;
import aroundtheeurope.takeflights.Models.RyanairResponseModels.RyanairResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ryanair.request.url}")
    private String API_URL;

    @Value("${ryanair.request.parameters}")
    private String REQUEST_PARAMETERS;

    @Autowired
    public FlightService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<FlightFares> findCheapestFlights(String origin, String departureAt) {
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
                        fare.getOutbound().getArrivalAirport().getName(),
                        fare.getOutbound().getArrivalAirport().getIataCode(),
                        fare.getOutbound().getPrice().getValue()
                );
                flights.add(flightFare);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return flights;
    }
}
