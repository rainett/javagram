package com.rainett.javagram.exceptions;

import java.util.List;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class CommandRegistrationException extends RuntimeException {
  public CommandRegistrationException(List<BotCommand> commands) {
    super("Failed to set bot commands = [" + commands + "]");
  }
}
