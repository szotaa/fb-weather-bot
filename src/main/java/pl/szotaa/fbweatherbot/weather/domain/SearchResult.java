package pl.szotaa.fbweatherbot.weather.domain;

import lombok.Data;

@Data
public class SearchResult {

    private String title;

    /**
     * WOEID - Where On Earth IDentifier
     * @see <a href="https://en.wikipedia.org/wiki/WOEID">WOEID on Wikipedia</a>
     */

    private long woeid;
}
