package pl.szotaa.fbweatherbot.config;

import com.github.messenger4j.Messenger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Messenger4j configuration class.
 *
 * @author szotaa
 */

@Configuration
public class Messenger4JConfig {

    @Bean
    public Messenger getMessenger(
            @Value("${messenger4j.pageAccessToken}") String pageAccessToken,
            @Value("${messenger4j.appSecret}") String appSecret,
            @Value("${messenger4j.verifyToken}") String verifyToken) {

        return Messenger.create(pageAccessToken, appSecret, verifyToken);
    }
}
