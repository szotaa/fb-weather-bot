package pl.szotaa.fbweatherbot.weather.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.szotaa.fbweatherbot.weather.domain.json.SearchResultDeserializer;

@Getter
@Setter
@AllArgsConstructor
@JsonDeserialize(using = SearchResultDeserializer.class)
public class SearchResult {

    private String title;

    /**
     * WOEID - Where On Earth IDentifier
     * @see <a href="https://en.wikipedia.org/wiki/WOEID">WOEID on Wikipedia</a>
     */

    private long woeid;

    @Override
    public int hashCode() {
        int result = Long.hashCode(woeid);
        result *= 31;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(!(obj instanceof SearchResult)){
            return false;
        }
        SearchResult compared = (SearchResult) obj;
        return this.woeid == compared.getWoeid();
    }
}
