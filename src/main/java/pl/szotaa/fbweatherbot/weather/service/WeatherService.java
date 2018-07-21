package pl.szotaa.fbweatherbot.weather.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.szotaa.fbweatherbot.weather.domain.SearchResult;
import pl.szotaa.fbweatherbot.weather.domain.Weather;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;

    private String apiUrl = "https://www.metaweather.com/api/";

    public Weather getWeatherSkipSearch(String location){
        return null;
    }

    public Weather getWeatherByWoeid(long woeid){
        return null;
    }

    public Set<SearchResult> searchForLocations(String query){
        return null;
    }
}
