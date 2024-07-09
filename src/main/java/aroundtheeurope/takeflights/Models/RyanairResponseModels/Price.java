package aroundtheeurope.takeflights.Models.RyanairResponseModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {
    @JsonProperty("value")
    private double value;

    public Price() {}

    public Price(double value) {
        this.value = value;
    }

    // Getters and setters
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
