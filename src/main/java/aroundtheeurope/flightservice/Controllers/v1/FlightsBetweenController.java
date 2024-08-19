package aroundtheeurope.flightservice.Controllers.v1;

import aroundtheeurope.flightservice.Models.DepartureInfo;
import aroundtheeurope.flightservice.Services.FlightsBetweenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/flightsbetween")
public class FlightsBetweenController {
    private final FlightsBetweenService flightsBetweenService;

    @Autowired
    public FlightsBetweenController(FlightsBetweenService flightsBetweenService) {
        this.flightsBetweenService = flightsBetweenService;
    }

    @GetMapping
    ResponseEntity<List<DepartureInfo>> flightsBetween(@RequestParam("origins") List<String> origins,
                                                       @RequestParam("destinations") List<String> destinations,
                                                       @RequestParam("departureAt") String departureAt,
                                                       @RequestParam(value = "dayRange", defaultValue = "1") int dayRange) {
        try {
            // validate required parameters
            if (origins == null || origins.isEmpty() || destinations == null || destinations.isEmpty() || departureAt == null || departureAt.isEmpty() || dayRange <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            List<DepartureInfo> flightData = flightsBetweenService.getFlights(origins, destinations, departureAt, dayRange);

            if (flightData.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.ok(flightData);

        } catch (Exception e) {
            // Log the exception (directly to console as for now)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
