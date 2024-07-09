package aroundtheeurope.takeflights.Models.RyanairResponseModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fare {
    @JsonProperty("outbound")
    private Outbound outbound;

    public Fare() {}

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
