package com.rainett.javagram.exceptions;

import com.rainett.javagram.update.UpdateType;

public class ActionMatcherNotFound extends RuntimeException {
  public ActionMatcherNotFound(UpdateType updateType) {
    super("No action matcher found for update type = [" + updateType + "]");
  }
}
