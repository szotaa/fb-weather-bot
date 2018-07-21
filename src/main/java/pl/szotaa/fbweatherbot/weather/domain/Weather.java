package pl.szotaa.fbweatherbot.weather.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import pl.szotaa.fbweatherbot.weather.domain.json.MetaWeatherDeserializer;

@Data
@Builder
@JsonDeserialize(using = MetaWeatherDeserializer.class)
public class Weather {

    private String location;
    private String parentLocation;
    private Set<Forecast> forecasts;
}
