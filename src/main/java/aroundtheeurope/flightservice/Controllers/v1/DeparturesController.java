package aroundtheeurope.flightservice.Controllers.v1;

import aroundtheeurope.flightservice.Models.DepartureInfo;
import aroundtheeurope.flightservice.Services.DepartureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling requests related to flight departures.
 * This controller exposes endpoints to retrieve information about flight departures,
 * including filtering options based on origin, departure date, and Schengen Area restriction.
 */
@RestController
@RequestMapping("/v1/departures")
public class DeparturesController {

    private final DepartureService departuresService;

    /**
     * Constructor for DeparturesController.
     *
     * @param departuresService the service used to retrieve departure information
     */
    @Autowired
    public DeparturesController(DepartureService departuresService) {
        this.departuresService = departuresService;
    }

    /**
     * Endpoint to retrieve departure information based on specified parameters.
     *
     * This endpoint allows clients to retrieve a list of departure information from a specified origin
     * on a given departure date, with optional filtering by Schengen Area and a day range.
     *
     * @param origin the IATA code of the departure airport (required)
     * @param departureAt the departure date in the format yyyy-MM-dd (required)
     * @param dayRange the number of days to search from the departure date (optional, default is 1)
     * @param schengenOnly if true, only includes flights within the Schengen Area (optional, default is false)
     * @return a ResponseEntity containing a list of DepartureInfo objects if found, or an appropriate error status
     */
    @GetMapping
    ResponseEntity<List<DepartureInfo>> departures(
            @RequestParam("origin") String origin,
            @RequestParam("departureAt") String departureAt,
            @RequestParam(value = "dayRange", defaultValue = "1") int dayRange,
            @RequestParam(value = "schengenOnly", defaultValue = "false") boolean schengenOnly
    ) {
        try {
            // Validate required parameters
            if (origin == null || origin.isEmpty() || departureAt == null || departureAt.isEmpty() || dayRange <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Retrieve departure information using the provided parameters
            List<DepartureInfo> flightData = departuresService.getDepartures(origin, departureAt, dayRange, schengenOnly);

            // If no data is found, return a 204 No Content status
            if (flightData.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            // Return the retrieved flight data with a 200 OK status
            return ResponseEntity.ok(flightData);

        } catch (Exception e) {
            // Log the exception and return a 500 Internal Server Error status
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
