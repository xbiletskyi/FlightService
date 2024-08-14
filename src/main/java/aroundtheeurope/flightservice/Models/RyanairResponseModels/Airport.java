package aroundtheeurope.flightservice.Models.RyanairResponseModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an airport with its IATA code, name, and city.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {

    @JsonProperty("iataCode")
    private String iataCode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("city")
    private City city;

    /**
     * Default constructor for Airport.
     */
    public Airport() {}

    /**
     * Constructor for Airport.
     *
     * @param iataCode the IATA code of the airport
     * @param name the name of the airport
     * @param city the city where the airport is located
     */
    public Airport(
            String iataCode,
            String name,
            City city
    ) {
        this.iataCode = iataCode;
        this.name = name;
        this.city = city;
    }

    // Getters and setters
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }
}
