package com.rainett.javagram.update.matcher;

import com.rainett.javagram.annotations.ActionMatcherType;
import com.rainett.javagram.annotations.Command;
import com.rainett.javagram.update.UpdateType;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@ActionMatcherType(UpdateType.COMMAND)
public class CommandMatcher implements ActionMatcher {

  private final String botUsername;

  @Override
  public boolean match(Object action, Update update) {
    return isCommand(update) && commandMatches(action, update);
  }

  private boolean commandMatches(Object action, Update update) {
    String actionCommand = action.getClass().getDeclaredAnnotation(Command.class).value();
    String updateText = update.getMessage().getText();
    String updateCommand;
    if (updateText.contains(botUsername)) {
      updateCommand = updateText.split("@")[0];
    } else {
      updateCommand = updateText.split(" ")[0];
    }
    return actionCommand.equals(updateCommand);
  }

  private static boolean isCommand(Update update) {
    return update.hasMessage() && update.getMessage().isCommand();
  }
}
