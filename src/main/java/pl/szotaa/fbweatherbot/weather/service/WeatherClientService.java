package pl.szotaa.fbweatherbot.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.szotaa.fbweatherbot.weather.domain.SearchResult;
import pl.szotaa.fbweatherbot.weather.domain.Weather;
import pl.szotaa.fbweatherbot.weather.exception.LocationNotFoundException;
import pl.szotaa.fbweatherbot.weather.exception.NoForecastException;

/**
 * MetaWeather API client.
 *
 * @author szotaa
 */

@Service
@RequiredArgsConstructor
public class WeatherClientService {

    private final RestTemplate restTemplate;

    private final String apiUrl = "https://www.metaweather.com/api/";

    /**
     * Searches for the specified query and returns weather data for first found location.
     */

    public Weather getWeatherSkipSearch(String location) throws LocationNotFoundException, NoForecastException {
        SearchResult[] searchResults = this.searchForLocations(location);
        return this.getWeatherByWoeid(searchResults[0].getWoeid());
    }

    public SearchResult[] searchForLocations(String query) throws LocationNotFoundException {
        SearchResult[] searchResults = this.restTemplate.getForObject(this.apiUrl + "location/search/?query=" + query, SearchResult[].class);
        if(searchResults == null || searchResults.length == 0){
            throw new LocationNotFoundException();
        }
        return searchResults;
    }

    public Weather getWeatherByWoeid(long woeid) throws NoForecastException {
        Weather weather = this.restTemplate.getForObject(this.apiUrl + "location/" + woeid, Weather.class);
        if(weather == null || weather.getForecasts().isEmpty()) {
            throw new NoForecastException();
        }
        return weather;
    }
}
