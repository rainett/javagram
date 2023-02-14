package com.rainett.javagram.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * App configuration class
 */
public class AppConfig {

    /**
     * Configures MessageSource bean.
     * Default locale is set to en-US
     * Basename - "messages", plus language code, like "uk-UA"
     * @return {@link MessageSource} bean
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultLocale(new Locale("en", "US"));
        resourceBundleMessageSource.setBasename("messages");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return resourceBundleMessageSource;
    }

}
