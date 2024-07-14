package aroundtheeurope.takeflights.Controllers;

import aroundtheeurope.takeflights.Models.FlightFares;
import aroundtheeurope.takeflights.Services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightsController {
    @Autowired
    private final FlightService flightService;

    @Autowired
    public FlightsController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights")
    ResponseEntity<List<FlightFares>> flights(@RequestParam("origin") String origin,
                                        @RequestParam("departure_at") String departureAt,
                                        @RequestParam(value = "schengenOnly", defaultValue = "false") boolean schengenOnly) {
        try {
            if (origin == null || origin.isEmpty() || departureAt == null || departureAt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            List<FlightFares> flightData = flightService.findCheapestFlights(origin, departureAt, schengenOnly);

            if (flightData.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            return ResponseEntity.ok(flightData);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
