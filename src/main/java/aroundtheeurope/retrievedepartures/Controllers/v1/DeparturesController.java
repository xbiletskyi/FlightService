package aroundtheeurope.retrievedepartures.Controllers.v1;

import aroundtheeurope.retrievedepartures.Models.DepartureInfo;
import aroundtheeurope.retrievedepartures.Services.DeparturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling requests of flight data.
 * This controller exposes endpoints to retrieve flight fares
 */
@RestController
@RequestMapping("/v1/departures")
public class DeparturesController {
    @Autowired
    private final DeparturesService departuresService;

    /**
     * Constructor for DeparturesController.
     *
     * @param departuresService the service used to retrieve flight fares
     */
    @Autowired
    public DeparturesController(DeparturesService departuresService) {
        this.departuresService = departuresService;
    }

    /**
     * Endpoint to retrieve the cheapest flight fares from a specified origin
     * on a given departure date.
     *
     * @param origin the IATA code of the departure airport
     * @param departureAt the departure date in the format yyyy-MM-dd
     * @param schengenOnly if true, only includes flights within the Schengen Area
     * @return a ResponseEntity containing a list of FlightFares objects or an appropriate error status
     */
    @GetMapping
    ResponseEntity<List<DepartureInfo>> departures(@RequestParam("origin") String origin,
                                                   @RequestParam("departureAt") String departureAt,
                                                   @RequestParam(value = "daysRange", defaultValue = "1") int daysRange,
                                                   @RequestParam(value = "schengenOnly", defaultValue = "false") boolean schengenOnly) {
        try {
            // validate required parameters
            if (origin == null || origin.isEmpty() || departureAt == null || departureAt.isEmpty() || daysRange <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            List<DepartureInfo> flightData = departuresService.getDepartures(origin, departureAt, daysRange, schengenOnly);

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
