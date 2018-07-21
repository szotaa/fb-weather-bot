package pl.szotaa.fbweatherbot.weather.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.szotaa.fbweatherbot.weather.domain.json.MetaWeatherDeserializer;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@JsonDeserialize(using = MetaWeatherDeserializer.class)
public class Weather {

    private String location;
    private String parentLocation;
    private List<Forecast> forecasts;

    @Override
    public String toString() {
        Collections.sort(this.forecasts);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Weather forecast for ")
                .append(this.location).append(", ")
                .append(this.parentLocation)
                .append(":\n");

        forecasts.forEach(forecast -> {
            if(forecast.getApplicableDate().isEqual(LocalDate.now())){
                stringBuilder.append("Today:\n\n");
            } else if (forecast.getApplicableDate().isEqual(LocalDate.now().plusDays(1))) {
                stringBuilder.append("Tomorrow:\n\n");
            } else {
                stringBuilder.append(String.format("%tA", forecast.getApplicableDate().getDayOfWeek())).append(":\n\n");
            }
            stringBuilder.append(forecast.getWeatherState()).append("\n");
            stringBuilder.append(String.format("Temperature: %.2f", forecast.getTemp())).append(" °C\n");
            stringBuilder.append(String.format("Max temperature: %.2f", forecast.getMaxTemp())).append(" °C\n");
            stringBuilder.append(String.format("Min temperature: %.2f", forecast.getMinTemp())).append(" °C\n");
            stringBuilder.append(String.format("Humidity: %.0f", forecast.getHumidity())).append(" %\n");
            stringBuilder.append(String.format("Pressure: %.2f", forecast.getPressure())).append(" hPa\n");
            stringBuilder.append("\n\n");
        });
        return stringBuilder.toString();
    }
}
