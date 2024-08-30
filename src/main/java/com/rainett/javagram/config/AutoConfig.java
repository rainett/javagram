package com.rainett.javagram.config;

import com.rainett.javagram.bot.LongPollingBot;
import com.rainett.javagram.bot.WebhookBot;
import com.rainett.javagram.update.service.UpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Slf4j
@Configuration
@ComponentScan(basePackages = "com.rainett.javagram")
@EnableConfigurationProperties(BotConfig.class)
public class AutoConfig {
    @Bean
    @ConditionalOnProperty(name = "bot.path")
    public DefaultAbsSender webhookBot(BotConfig botConfig) {
        log.info("Webhook enabled, creating WebhookBot instance");
        return new WebhookBot(new SetWebhook(botConfig.getPath()), botConfig);
    }

    @Bean
    @ConditionalOnMissingBean(DefaultAbsSender.class)
    public DefaultAbsSender longPollingBot(BotConfig botConfig,
                                           ObjectProvider<UpdateService> updateService) {
        log.info("Webhook disabled, creating LongPollingBot instance");
        return new LongPollingBot(botConfig, updateService);
    }
}
