package com.rainett.javagram.update;

import com.rainett.javagram.action.ActionContainer;
import com.rainett.javagram.exceptions.RunnableMethodNotFound;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
public class UpdateService {

  private final ActionContainer actionContainer;


  public void handleUpdate(Update update) {
    Object botAction = actionContainer.findByUpdate(update);
    Method method = getRunnableMethod(botAction);
    try {
      method.invoke(botAction, update);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private static Method getRunnableMethod(Object botAction) {
    Method[] methods = botAction.getClass().getDeclaredMethods();
    return Arrays.stream(methods)
      .filter(m -> m.getParameterCount() == 1
        && m.getParameterTypes()[0].equals(Update.class))
      .findAny()
      .orElseThrow(() -> new RunnableMethodNotFound(botAction));
  }
}
