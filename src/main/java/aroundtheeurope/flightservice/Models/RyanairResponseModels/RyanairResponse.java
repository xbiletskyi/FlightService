package aroundtheeurope.flightservice.Models.RyanairResponseModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the response from Ryanair API containing a list of fares.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RyanairResponse {
    @JsonProperty("fares")
    private List<Fare> fares;

    /**
     * Default constructor for RyanairResponse.
     */
    public RyanairResponse() {}

    /**
     * Constructor for RyanairResponse.
     *
     * @param fares the list of fares
     */
    public RyanairResponse(List<Fare> fares) {
        this.fares = fares;
    }

    // Getters and setters
    public List<Fare> getFares() {
        return fares;
    }

    public void setFares(List<Fare> fares) {
        this.fares = fares;
    }
}
