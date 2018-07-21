package pl.szotaa.fbweatherbot.weather.domain.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import pl.szotaa.fbweatherbot.weather.domain.Forecast;
import pl.szotaa.fbweatherbot.weather.domain.Weather;

public class MetaWeatherDeserializer extends JsonDeserializer<Weather> {

    @Override
    public Weather deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode json = codec.readTree(jsonParser);
        JsonNode forecastsJson = json.get("consolidated_weather");
        Set<Forecast> forecasts = StreamSupport.stream(forecastsJson.spliterator(), false)
                .map(this::buildForecast)
                .collect(Collectors.toSet());

        return Weather.builder()
                .location(this.getLocation(json))
                .parentLocation(this.getParentLocation(json))
                .forecasts(forecasts)
                .build();

    }

    private Forecast buildForecast(JsonNode node){
        return Forecast.builder()
                .applicableDate(LocalDate.parse(node.get("applicable_date").asText()))
                .humidity(node.get("humidity").asDouble())
                .maxTemp(node.get("max_temp").asDouble())
                .minTemp(node.get("min_temp").asDouble())
                .temp(node.get("the_temp").asDouble())
                .pressure(node.get("air_pressure").asDouble())
                .weatherState(node.get("weather_state_name").asText())
                .weatherStateAbbr(node.get("weather_state_abbr").asText())
                .build();
    }

    private String getLocation(JsonNode node){
        return node.get("title").asText();
    }

    private String getParentLocation(JsonNode node){
        return node.get("parent").get("title").asText();
    }
}

