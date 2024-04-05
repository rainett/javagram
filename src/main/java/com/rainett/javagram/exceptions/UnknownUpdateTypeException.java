package com.rainett.javagram.exceptions;

import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownUpdateTypeException extends RuntimeException {

  public UnknownUpdateTypeException(Update update) {
    super("Unknown update type = [" + update.getUpdateId() + "]");
  }

}
