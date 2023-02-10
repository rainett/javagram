package com.rainett.javagram.autoconfig;

import com.rainett.javagram.config.AppConfig;
import com.rainett.javagram.config.BotConfig;
import com.rainett.javagram.controller.executables.container.ExecutablesContainer;
import com.rainett.javagram.controller.executables.container.ExecutablesContainerImpl;
import com.rainett.javagram.controller.executor.BotExecutor;
import com.rainett.javagram.controller.executor.BotExecutorImpl;
import com.rainett.javagram.controller.processor.UpdateProcessor;
import com.rainett.javagram.controller.processor.UpdateProcessorImpl;
import com.rainett.javagram.controller.webhook.WebhookBot;
import com.rainett.javagram.controller.webhook.WebhookController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@EnableConfigurationProperties(BotConfig.class)
public class AutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public AppConfig appConfig() {
        return new AppConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExecutablesContainer executablesContainer(ApplicationContext appContext) {
        return new ExecutablesContainerImpl(appContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public BotExecutor botExecutor(WebhookBot bot) {
        return new BotExecutorImpl(bot);
    }

    @Bean
    @ConditionalOnMissingBean
    public UpdateProcessor updateProcessor(ExecutablesContainer container, WebhookBot bot) {
        return new UpdateProcessorImpl(container, bot);
    }

    @Bean
    @ConditionalOnMissingBean
    public SetWebhook setWebhook(BotConfig botConfig) {
        return SetWebhook.builder().url(botConfig.getPath()).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public WebhookBot webhookBot(SetWebhook webhook, BotConfig config) {
        return new WebhookBot(webhook, config);
    }

    @Bean
    @ConditionalOnMissingBean
    public WebhookController webhookController(UpdateProcessor processor) {
        return new WebhookController(processor);
    }


}
