package pl.szotaa.fbweatherbot.weather.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szotaa.fbweatherbot.weather.domain.json.SearchResultDeserializer;

@Data
@AllArgsConstructor
@JsonDeserialize(using = SearchResultDeserializer.class)
public class SearchResult {

    private String title;

    /**
     * WOEID - Where On Earth IDentifier
     * @see <a href="https://en.wikipedia.org/wiki/WOEID">WOEID on Wikipedia</a>
     */

    private long woeid;
}
