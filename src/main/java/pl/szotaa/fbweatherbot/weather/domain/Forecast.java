package pl.szotaa.fbweatherbot.weather.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

/**
 * Represents weather forecast for one day.
 * @see Weather
 *
 * @author szotaa
 */

@Getter
@Builder
public class Forecast implements Comparable<Forecast> {

    private LocalDate applicableDate;
    private String weatherState;
    private String weatherStateAbbr;
    private double minTemp;
    private double maxTemp;
    private double temp;
    private double pressure;
    private double humidity;

    @Override
    public int compareTo(Forecast o) {
        return this.applicableDate.compareTo(o.getApplicableDate());
    }
}
