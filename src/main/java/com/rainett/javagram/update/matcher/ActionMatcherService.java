package com.rainett.javagram.update.matcher;

import com.rainett.javagram.annotations.ActionMatcherType;
import com.rainett.javagram.exceptions.ActionMatcherNotFound;
import com.rainett.javagram.update.UpdateType;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationContext;

public class ActionMatcherService {

  private final Map<UpdateType, ActionMatcher> matchersMap;

  public ActionMatcherService(ApplicationContext context) {
    this.matchersMap = context.getBeansWithAnnotation(ActionMatcherType.class).values()
        .stream()
        .filter(obj -> obj instanceof ActionMatcher)
        .map(matcher -> (ActionMatcher) matcher)
        .collect(
          Collectors.toMap(ActionMatcherService::getUpdateTypeFromAction, Function.identity()));
  }

  private static UpdateType getUpdateTypeFromAction(Object matcher) {
    return matcher.getClass().getDeclaredAnnotation(ActionMatcherType.class).value();
  }

  public ActionMatcher getMatcher(UpdateType updateType) {
    return Optional.ofNullable(matchersMap.get(updateType))
        .orElseThrow(() -> new ActionMatcherNotFound(updateType));
  }
}
