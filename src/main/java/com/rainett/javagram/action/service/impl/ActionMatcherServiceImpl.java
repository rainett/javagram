package com.rainett.javagram.action.service.impl;

import com.rainett.javagram.action.ActionType;
import com.rainett.javagram.action.matcher.ActionMatcher;
import com.rainett.javagram.action.service.ActionMatcherService;
import com.rainett.javagram.update.type.UpdateType;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ActionMatcherServiceImpl implements ActionMatcherService {
    private final Map<UpdateType, ActionMatcher> matchersMap;

    public ActionMatcherServiceImpl(ApplicationContext context) {
        this.matchersMap = getActionMatchersFromContext(context);
    }

    @Override
    public ActionMatcher getMatcher(UpdateType updateType) {
        return Optional.ofNullable(matchersMap.get(updateType))
                .orElseThrow(() -> new NoSuchElementException("Action matcher not found for "
                        + "update type = [" + updateType + "]"));
    }

    private static Map<UpdateType, ActionMatcher> getActionMatchersFromContext(
            ApplicationContext context) {
        return context.getBeansWithAnnotation(ActionType.class).values()
                .stream()
                .filter(obj -> obj instanceof ActionMatcher)
                .map(matcher -> (ActionMatcher) matcher)
                .collect(Collectors.toMap(ActionMatcherServiceImpl::getUpdateTypeFromAction,
                        Function.identity()));
    }

    private static UpdateType getUpdateTypeFromAction(Object matcher) {
        return matcher.getClass().getDeclaredAnnotation(ActionType.class).value();
    }
}
