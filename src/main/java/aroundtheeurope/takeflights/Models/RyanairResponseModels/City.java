package aroundtheeurope.takeflights.Models.RyanairResponseModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a city with its country code.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
    @JsonProperty("countryCode")
    String countryCode;

    /**
     * Default constructor for City.
     */
    public City(){}

    /**
     * Constructor for City.
     *
     * @param countryCode the country code of the city
     */
    public City(String countryCode) {
        this.countryCode = countryCode;
    }
    // Getters and setters
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
