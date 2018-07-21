package pl.szotaa.fbweatherbot.weather.domain.json

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import pl.szotaa.fbweatherbot.weather.domain.SearchResult
import spock.lang.Specification

@JsonTest
class SearchResultDeserializerTest extends Specification {

    @Autowired
    JacksonTester<SearchResult> jacksonTester

    def "SearchResult should properly deserialize from JSON"() {
        given:
            def json =
                    "   {\n" +
                    "      \"title\": \"Warsaw\",\n" +
                    "      \"location_type\": \"City\",\n" +
                    "      \"woeid\": 523920,\n" +
                    "      \"latt_long\": \"52.235352,21.009390\"\n" +
                    "   }\n"
        when:
            def searchResult = jacksonTester.parseObject(json)

        then:
            searchResult.title.equals("Warsaw")
            searchResult.woeid.equals(523920l)
    }
}
