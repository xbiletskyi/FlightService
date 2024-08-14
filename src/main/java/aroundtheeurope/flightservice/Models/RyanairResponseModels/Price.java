package aroundtheeurope.flightservice.Models.RyanairResponseModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the price details of a flight.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    @JsonProperty("value")
    private double value;

    @JsonProperty("currencyCode")
    private String currencyCode;

    /**
     * Default constructor for Price.
     */
    public Price() {}

    /**
     * Constructor for Price.
     *
     * @param value the value of the price
     * @param currencyCode the currency code of the price
     */
    public Price(double value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

    // Getters and setters

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
