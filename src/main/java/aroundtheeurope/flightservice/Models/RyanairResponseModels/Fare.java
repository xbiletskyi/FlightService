package aroundtheeurope.flightservice.Models.RyanairResponseModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a fare with its outbound flight details.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fare {

    @JsonProperty("outbound")
    private Outbound outbound;

    /**
     * Default constructor for Fare.
     */
    public Fare() {}

    /**
     * Constructor for Fare.
     *
     * @param outbound the outbound flight details
     */
    public Fare(Outbound outbound) {
        this.outbound = outbound;
    }

    // Getters and setters

    public Outbound getOutbound() {
        return outbound;
    }

    public void setOutbound(Outbound outbound) {
        this.outbound = outbound;
    }
}
