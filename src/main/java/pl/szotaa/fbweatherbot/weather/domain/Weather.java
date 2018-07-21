package pl.szotaa.fbweatherbot.weather.domain;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {

    private String location;
    private String parentLocation;
    private Set<Forecast> forecasts;
}
