package aroundtheeurope.flightservice.Models.RyanairOperatedAirportsResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OperatedFlightData {
    @JsonProperty("arrivalAirport")
    private ArrivalAirport arrivalAirport;

    @JsonProperty("recent")
    private boolean recent;

    @JsonProperty("seasonal")
    private boolean seasonal;

    @JsonProperty("operator")
    private String operator;

    public OperatedFlightData() {
    }

    public OperatedFlightData(ArrivalAirport arrivalAirport, boolean recent, boolean seasonal, String operator) {
        this.arrivalAirport = arrivalAirport;
        this.recent = recent;
        this.seasonal = seasonal;
        this.operator = operator;
    }

    // Getters and setters
    public ArrivalAirport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(ArrivalAirport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public boolean isRecent() {
        return recent;
    }

    public void setRecent(boolean recent) {
        this.recent = recent;
    }

    public boolean isSeasonal() {
        return seasonal;
    }

    public void setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
