package com.rainett.javagram.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@ToString
@ConditionalOnMissingBean
@PropertySource({"classpath:application.yml", "classpath:default.yml"})
@ConfigurationProperties(prefix = "bot")
public class BotConfig {

    private String path;

    private String username;

    private String token;

}
