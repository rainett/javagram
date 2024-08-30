package com.rainett.javagram.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configures bot credentials. Looks for properties in application .properties/.yaml file in
 * resources folder
 */
@Getter
@Setter
@ToString
@Configuration
@PropertySource(value =
        {"classpath:application.yml", "classpath:application.properties"},
        ignoreResourceNotFound = true
)
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    /**
     * Bot webhook path. E.g. if you are using ngrok, place here ngrok redirect URL
     */
    private String path;

    /**
     * Bot username. "@" symbol should be placed too
     */
    private String username;

    /**
     * Bot token. Copy it from BotFather in telegram
     */
    private String token;
}
