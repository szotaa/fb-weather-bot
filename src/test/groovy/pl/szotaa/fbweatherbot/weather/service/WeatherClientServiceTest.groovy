package pl.szotaa.fbweatherbot.weather.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import pl.szotaa.fbweatherbot.config.RestTemplateConfig
import pl.szotaa.fbweatherbot.weather.domain.SearchResult
import pl.szotaa.fbweatherbot.weather.domain.Weather
import pl.szotaa.fbweatherbot.weather.exception.LocationNotFoundException
import pl.szotaa.fbweatherbot.weather.exception.NoForecastException
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RestClientTest([WeatherClientService, RestTemplateConfig])
class WeatherClientServiceTest extends Specification {

    @Autowired
    private WeatherClientService weatherService

    @Autowired
    private RestTemplate restTemplate

    private MockRestServiceServer server

    def emptyJsonArray = "[]"
    def warsawLocation = "[{\n" +
            "      \"title\": \"Warsaw\",\n" +
            "      \"location_type\": \"City\",\n" +
            "      \"woeid\": 1,\n" +
            "      \"latt_long\": \"52.235352,21.009390\"\n" +
            "   }]\n"
    def emptyWeather = "{\"detail\":\"Not found.\"}"
    def exampleWeather = "{\n" +
            "   \"consolidated_weather\": [\n" +
            "      {\n" +
            "         \"weather_state_name\": \"Heavy Cloud\",\n" +
            "         \"weather_state_abbr\": \"hc\",\n" +
            "         \"applicable_date\": \"2018-07-21\",\n" +
            "         \"min_temp\": 17.6575,\n" +
            "         \"max_temp\": 27.6025,\n" +
            "         \"the_temp\": 25.93,\n" +
            "         \"air_pressure\": 1017.03,\n" +
            "         \"humidity\": 58\n" +
            "      },\n" +
            "      {\n" +
            "         \"weather_state_name\": \"Heavy Cloud\",\n" +
            "         \"weather_state_abbr\": \"hc\",\n" +
            "         \"applicable_date\": \"2018-07-22\",\n" +
            "         \"min_temp\": 18.4425,\n" +
            "         \"max_temp\": 26.61,\n" +
            "         \"the_temp\": 25.615,\n" +
            "         \"air_pressure\": 1019.345,\n" +
            "         \"humidity\": 53\n" +
            "      }\n" +
            "   ],\n" +
            "   \"parent\": {\n" +
            "      \"title\": \"Poland\",\n" +
            "      \"location_type\": \"Region / State / Province\",\n" +
            "      \"woeid\": 24554868,\n" +
            "      \"latt_long\": \"52.883560,-1.974060\"\n" +
            "   },\n" +
            "   \"title\": \"Warsaw\",\n" +
            "   \"location_type\": \"City\",\n" +
            "   \"woeid\": 1,\n" +
            "   \"latt_long\": \"51.506321,-0.12714\",\n" +
            "   \"timezone\": \"Europe/Warsaw\"\n" +
            "}"

    def setup(){
        server = MockRestServiceServer.createServer(restTemplate)
    }

    def "When no location is found, exception is thrown"() {
        given:
            server.expect(requestTo("https://www.metaweather.com/api/location/search/?query=nothing"))
                .andRespond(withSuccess(emptyJsonArray, MediaType.APPLICATION_JSON_UTF8))

            def location = "nothing"

        when:
            weatherService.searchForLocations(location)

        then:
            thrown(LocationNotFoundException)
    }

    def "When correct location is specified, response is returned"() {
        given:
            server.expect(requestTo("https://www.metaweather.com/api/location/search/?query=warsaw"))
                .andRespond(withSuccess(warsawLocation, MediaType.APPLICATION_JSON_UTF8))

            def location = "warsaw"

        when:
            def result = weatherService.searchForLocations(location)

        then:
            result != null
            result instanceof SearchResult[]
            result[0].title == "Warsaw"
            result[0].woeid == 1
    }

    def "When correct woeid is passed, correct weather response received"() {
        given:
            server.expect(requestTo("https://www.metaweather.com/api/location/1"))
                .andRespond(withSuccess(exampleWeather, MediaType.APPLICATION_JSON_UTF8))

            def location = 1

        when:
            def response = weatherService.getWeatherByWoeid(location)

        then:
            response.getForecasts().size() == 2
            response.location == ("Warsaw")
            response.parentLocation == ("Poland")
            def forecast1 = response.getForecasts().get(0)
            forecast1.getApplicableDate() == (LocalDate.of(2018, 7, 21))
            forecast1.getWeatherState() == ("Heavy Cloud")
            forecast1.getWeatherStateAbbr() == ("hc")
            forecast1.getMaxTemp() == (27.6025d)
            forecast1.getMinTemp() == (17.6575d)
            forecast1.getTemp() == (25.93d)
            forecast1.getPressure() == (1017.03d)
            forecast1.getHumidity() == (58d)
    }

    def "When incorrect woeid is passed, exception is thrown"(){
        given:
            server.expect(requestTo("https://www.metaweather.com/api/location/0"))
                .andRespond(withSuccess(emptyWeather, MediaType.APPLICATION_JSON_UTF8))

            def location = 0

        when:
            weatherService.getWeatherByWoeid(location)

        then:
            thrown(NoForecastException)
    }

    def "When getWeatherSkipSearch is called, weather for first result in searchForLocations is returned"(){
        given:
            server.expect(requestTo("https://www.metaweather.com/api/location/search/?query=warsaw"))
                .andRespond(withSuccess(warsawLocation, MediaType.APPLICATION_JSON_UTF8))
            server.expect(requestTo("https://www.metaweather.com/api/location/1"))
                .andRespond(withSuccess(exampleWeather, MediaType.APPLICATION_JSON_UTF8))

            def location = "warsaw"

        when:
            Weather response = weatherService.getWeatherSkipSearch(location)

        then:
            response != null
            response instanceof Weather
            response.location == "Warsaw"

    }
}
