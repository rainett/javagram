package com.rainett.javagram.autoconfig;

import com.rainett.javagram.config.AppConfig;
import com.rainett.javagram.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AppAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public AppConfig appConfig(BotConfig botConfig) {
        log.info("BotConfig: {}", botConfig);
        return new AppConfig(botConfig);
    }

}
