package com.rainett.javagram.action;

import com.rainett.javagram.Bot;
import com.rainett.javagram.annotations.BotAction;
import com.rainett.javagram.annotations.Command;
import com.rainett.javagram.defaults.DefaultAction;
import com.rainett.javagram.exceptions.CommandRegistrationException;
import com.rainett.javagram.update.UpdateType;
import com.rainett.javagram.update.matcher.ActionMatcher;
import com.rainett.javagram.update.matcher.ActionMatcherService;
import jakarta.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RequiredArgsConstructor
public class ActionContainer {

  private final ApplicationContext context;
  private final ComparatorService comparatorService;
  private final ActionMatcherService actionMatcherService;
  private final ActionTypeService actionTypeService;
  private final Bot bot;
  private final DefaultAction defaultAction;
  private Map<UpdateType, List<Object>> botActions;


  @PostConstruct
  private void init() {
    this.botActions = context.getBeansWithAnnotation(BotAction.class).values().stream()
        .collect(Collectors.groupingBy(actionTypeService::getUpdateTypeFromAction,
            getBotActionQueueCollector()));
    this.botActions.values().forEach(a -> a.sort(comparatorService::compare));
    log.info("Actions created successfully: {}", botActions);
    registerBotCommands();
  }

  public Object findByUpdate(Update update) {
    UpdateType updateType = actionTypeService.getUpdateTypeFromUpdate(update);
    List<Object> typeBotActions = this.botActions.get(updateType);
    ActionMatcher actionMatcher = actionMatcherService.getMatcher(updateType);
    return getBotActionFromList(update, typeBotActions, actionMatcher);
  }

  private Collector<Object, ?, List<Object>> getBotActionQueueCollector() {
    return Collectors.toCollection(LinkedList::new);
  }

  private void registerBotCommands() {
    List<BotCommand> botCommands = getBotCommands();
    try {
      bot.executeAsync(new SetMyCommands(botCommands, null, null));
    } catch (TelegramApiException e) {
      throw new CommandRegistrationException(botCommands);
    }
  }

  private List<BotCommand> getBotCommands() {
    List<BotCommand> commands = new LinkedList<>();
    if (!this.botActions.containsKey(UpdateType.COMMAND)) {
      return commands;
    }
    for (Object c : this.botActions.get(UpdateType.COMMAND)) {
      Command command = c.getClass().getDeclaredAnnotation(Command.class);
      if (!command.hideFromMenu()) {
        commands.add(new BotCommand(command.value(), command.description()));
      }
    }
    return commands;
  }

  private Object getBotActionFromList(Update update,
      List<Object> botActions,
      ActionMatcher actionMatcher) {
    if (botActions != null && !botActions.isEmpty()) {
      for (Object action : botActions) {
        if (actionMatcher.match(action, update)) {
          return action;
        }
      }
    }
    return defaultAction;
  }

}
