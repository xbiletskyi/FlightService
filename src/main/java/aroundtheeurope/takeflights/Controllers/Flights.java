package aroundtheeurope.takeflights.Controllers;

import aroundtheeurope.takeflights.Redis.Cacher;
import aroundtheeurope.takeflights.Models.FlightFares;
import aroundtheeurope.takeflights.Services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Flights {
    @Autowired
    private final FlightService flightService;
    private final Cacher cacher;

    @Autowired
    public Flights(FlightService flightService, Cacher cacher) {
        this.flightService = flightService;
        this.cacher = cacher;
    }

    @GetMapping("/flights")
    List<FlightFares> flights(@RequestParam("origin") String origin, @RequestParam("departure_at") String departureAt) {
        List<FlightFares> flightData = cacher.retrieveCache(origin, departureAt);
        if (flightData == null) {
            flightData = flightService.findCheapestFlights(origin, departureAt);
            if (flightData != null) {
                cacher.storeCache(origin, departureAt, flightData);
            }
        }
        return flightData;
    }


}
