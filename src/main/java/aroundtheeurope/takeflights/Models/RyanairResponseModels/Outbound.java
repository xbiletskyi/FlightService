package aroundtheeurope.takeflights.Models.RyanairResponseModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the details of an outbound flight.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Outbound {
    @JsonProperty("flightNumber")
    private String flightNumber;

    @JsonProperty("departureDate")
    private String departureDate;

    @JsonProperty("departureAirport")
    private Airport departureAirport;

    @JsonProperty("arrivalAirport")
    private Airport arrivalAirport;

    @JsonProperty("price")
    private Price price;

    /**
     * Default constructor for Outbound.
     */
    public Outbound() {}

    /**
     * Constructor for Outbound.
     *
     * @param flightNumber the flight number
     * @param departureDate the departure date
     * @param departureAirport the departure airport details
     * @param arrivalAirport the arrival airport details
     * @param price the price details
     */
    public Outbound (String flightNumber, String departureDate, Airport departureAirport, Airport arrivalAirport, Price price) {
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.price = price;
    }

    // Getters and setters
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
