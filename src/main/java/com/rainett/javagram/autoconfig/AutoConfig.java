package com.rainett.javagram.autoconfig;

import com.rainett.javagram.Bot;
import com.rainett.javagram.action.ActionContainer;
import com.rainett.javagram.action.ActionTypeService;
import com.rainett.javagram.action.ComparatorService;
import com.rainett.javagram.config.BotConfig;
import com.rainett.javagram.defaults.DefaultAction;
import com.rainett.javagram.update.ApplicationReadyHandler;
import com.rainett.javagram.update.UpdateController;
import com.rainett.javagram.update.UpdateService;
import com.rainett.javagram.update.matcher.ActionMatcherService;
import com.rainett.javagram.update.matcher.CommandMatcher;
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
  public ActionContainer actionContainer(ApplicationContext context,
      ComparatorService comparatorService, ActionMatcherService actionMatcherService,
      ActionTypeService actionTypeService, Bot bot, DefaultAction defaultAction) {
    return new ActionContainer(context, comparatorService, actionMatcherService,
        actionTypeService, bot, defaultAction);
  }

  @Bean
  public ActionTypeService actionTypeService() {
    return new ActionTypeService();
  }

  @Bean
  public ComparatorService comparatorService() {
    return new ComparatorService();
  }

  @Bean
  public ActionMatcherService actionMatcherService(ApplicationContext context) {
    return new ActionMatcherService(context);
  }

  @Bean
  public CommandMatcher commandMatcher(BotConfig botConfig) {
    return new CommandMatcher(botConfig.getUsername());
  }

  @Bean
  public ApplicationReadyHandler applicationReadyHandler(BotConfig botConfig) {
    return new ApplicationReadyHandler(botConfig);
  }

  @Bean
  public UpdateController updateController(UpdateService updateService) {
    return new UpdateController(updateService);
  }

  @Bean
  public UpdateService updateService(ActionContainer actionContainer) {
    return new UpdateService(actionContainer);
  }

  @Bean
  public Bot bot(BotConfig botConfig) {
    return new Bot(new SetWebhook(botConfig.getPath()), botConfig);
  }

  @Bean
  @ConditionalOnMissingBean
  public DefaultAction defaultAction() {
    return new DefaultAction();
  }

}
