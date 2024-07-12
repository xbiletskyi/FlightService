package aroundtheeurope.takeflights.Models;

public class FlightFares {
    private String flightNumber;
    private String departureAt;
    private String originAirportName;
    private String originAirportCode;
    private String originCountryCode;
    private String destinationAirportName;
    private String destinationAirportCode;
    private String destinationCountryCode;
    private double price;
    private String currencyCode;

    public FlightFares () {}

    public FlightFares (String flightNumber,
                        String departureAt,
                        String originAirportName,
                        String originAirportCode,
                        String originCountryCode,
                        String destinationAirportName,
                        String destinationAirportCode,
                        String destinationCountryCode,
                        double price,
                        String currencyCode) {
        this.flightNumber = flightNumber;
        this.departureAt = departureAt;
        this.originAirportName = originAirportName;
        this.originAirportCode = originAirportCode;
        this.originCountryCode = originCountryCode;
        this.destinationAirportName = destinationAirportName;
        this.destinationAirportCode = destinationAirportCode;
        this.destinationCountryCode = destinationCountryCode;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDestinationCountryCode() {
        return destinationCountryCode;
    }

    public void setDestinationCountryCode(String destinationCountryCode) {
        this.destinationCountryCode = destinationCountryCode;
    }

    public String getOriginCountryCode() {
        return originCountryCode;
    }

    public void setOriginCountryCode(String originCountryCode) {
        this.originCountryCode = originCountryCode;
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
