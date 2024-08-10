package aroundtheeurope.flightservice.Services;

import aroundtheeurope.flightservice.Models.DepartureInfo;
import aroundtheeurope.flightservice.Models.RyanairResponseModels.Fare;
import aroundtheeurope.flightservice.Models.RyanairResponseModels.RyanairResponse;
import aroundtheeurope.flightservice.Redis.Cacher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightsBetweenService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Cacher cacher;

    @Value("${ryanair.request.url}")
    private String API_URL;

    @Value("${ryanair.request.parameters}")
    private String REQUEST_PARAMETERS;

    public FlightsBetweenService(RestTemplate restTemplate, ObjectMapper objectMapper, Cacher cacher) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.cacher = cacher;
    }

    public List<DepartureInfo> getFlights(List<String> origins, List<String> destinations, String departureAt, int dayRange) {
        List<DepartureInfo> flights = new ArrayList<>();
        LocalDate departureDate = LocalDate.parse(departureAt, DateTimeFormatter.ISO_LOCAL_DATE);

        for (String origin : origins) {
            for (String destination : destinations) {
                LocalDate currentDay = departureDate;
                for (int i = 0; i < dayRange; i++) {
                    List<DepartureInfo> dayDepartures = cacher.retrieveCache(origin, destination, currentDay.toString());
                    if (dayDepartures == null) {
                        dayDepartures = getRyanairOnDayFlight(origin, destination, currentDay.toString());
                        cacher.storeCache(origin, destination, currentDay.toString(), dayDepartures);
                    }
                    flights.addAll(dayDepartures);
                    currentDay = currentDay.plusDays(1);
                }
            }
        }

        return flights;
    }

    public List<DepartureInfo> getRyanairOnDayFlight(String origin, String destination, String departureAt) {
        String url = API_URL + REQUEST_PARAMETERS + "&departureAirportIataCode=" + origin +
                "&arrivalAirportIataCode=" + destination +
                "&outboundDepartureDateFrom=" + departureAt + "&outboundDepartureDateTo=" + departureAt;
        String response = restTemplate.getForObject(url, String.class);
        List<DepartureInfo> flights = new ArrayList<>();
        try {
            RyanairResponse ryanairResponse = objectMapper.readValue(response, RyanairResponse.class);
            List<Fare> fares = ryanairResponse.getFares();
            for (Fare fare : fares) {
                DepartureInfo flightInfo = DepartureInfo.createFromRyanAirFare(fare);
                flights.add(flightInfo);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return flights;
    }
}
