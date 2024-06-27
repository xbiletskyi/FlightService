package aroundtheeurope.takeflights.Models;

public class FlightFaresRyanair {
    private String flightNumber;
    private String departureAt;
    private String originAirportName;
    private String originAirportCode;
    private String destinationAirportName;
    private String destinationAirportCode;
    private double price;

    public FlightFaresRyanair(String flightNumber, String departureAt, String originAirportName,
                              String originAirportCode, String destinationAirportName, String destinationAirportCode,
                              double price) {
        this.flightNumber = flightNumber;
        this.departureAt = departureAt;
        this.originAirportName = originAirportName;
        this.originAirportCode = originAirportCode;
        this.destinationAirportName = destinationAirportName;
        this.destinationAirportCode = destinationAirportCode;
        this.price = price;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureAt() {
        return departureAt;
    }

    public void setDepartureAt(String departureAt) {
        this.departureAt = departureAt;
    }

    public String getOriginAirportName() {
        return originAirportName;
    }

    public void setOriginAirportName(String originAirportName) {
        this.originAirportName = originAirportName;
    }

    public String getOriginAirportCode() {
        return originAirportCode;
    }

    public void setOriginAirportCode(String originAirportCode) {
        this.originAirportCode = originAirportCode;
    }

    public String getDestinationAirportName() {
        return destinationAirportName;
    }

    public void setDestinationAirportName(String destinationAirportName) {
        this.destinationAirportName = destinationAirportName;
    }

    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    public void setDestinationAirportCode(String destinationAirportCode) {
        this.destinationAirportCode = destinationAirportCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
