package pl.szotaa.fbweatherbot.weather.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Forecast {

    private LocalDate applicableDate;
    private String weatherState;
    private String weatherStateAbbr;
    private double minTemp;
    private double maxTemp;
    private double temp;
    private double pressure;
    private double humidity;
}
