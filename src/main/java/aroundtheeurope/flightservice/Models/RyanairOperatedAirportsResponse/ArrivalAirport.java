package aroundtheeurope.flightservice.Models.RyanairOperatedAirportsResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrivalAirport {
    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    public ArrivalAirport() {
    }

    public ArrivalAirport(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
