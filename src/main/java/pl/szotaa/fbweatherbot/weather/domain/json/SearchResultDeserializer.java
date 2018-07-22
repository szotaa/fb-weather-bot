package pl.szotaa.fbweatherbot.weather.domain.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import pl.szotaa.fbweatherbot.weather.domain.SearchResult;

/**
 * Deserializes JSON object to Java {@link SearchResult}
 *
 * @author szotaa
 */

public class SearchResultDeserializer extends JsonDeserializer<SearchResult> {

    @Override
    public SearchResult deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        JsonNode locationNode = node.get("title");
        if(locationNode == null){
            return null;
        }
        String location = locationNode.asText();
        long woeid = node.get("woeid").asLong();
        return new SearchResult(location, woeid);
    }
}
