package com.rainett.javagram.action.container.impl;

import com.rainett.javagram.action.Action;
import com.rainett.javagram.action.container.ActionCollector;
import com.rainett.javagram.action.container.ActionContainer;
import com.rainett.javagram.action.matcher.ActionMatcher;
import com.rainett.javagram.action.service.ActionMatcherService;
import com.rainett.javagram.defaults.DefaultAction;
import com.rainett.javagram.update.type.UpdateType;
import com.rainett.javagram.update.type.UpdateTypeService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActionContainerImpl implements ActionContainer {
    private final ActionCollector actionCollector;
    private final ActionMatcherService actionMatcherService;
    private final UpdateTypeService updateTypeService;
    private final DefaultAction defaultAction;
    private Map<UpdateType, List<Action>> actions;

    @Override
    public Action findByUpdate(Update update) {
        UpdateType updateType;
        ActionMatcher actionMatcher;
        try {
            updateType = updateTypeService.getUpdateTypeFromUpdate(update);
            actionMatcher = actionMatcherService.getMatcher(updateType);
        } catch (RuntimeException e) {
            log.error("Failed to find update type or action matcher: {}", e.getMessage());
            return defaultAction;
        }
        List<Action> typeBotActions = this.actions.get(updateType);
        return getBotActionFromList(update, typeBotActions, actionMatcher);
    }

    @PostConstruct
    private void init() {
        actions = actionCollector.collectActions();
        log.info("Actions created successfully: {}", actions);
    }

    private Action getBotActionFromList(Update update,
                                        List<Action> botActions,
                                        ActionMatcher actionMatcher) {
        if (botActions != null && !botActions.isEmpty()) {
            for (Action action : botActions) {
                if (actionMatcher.match(action, update)) {
                    return action;
                }
            }
        }
        return defaultAction;
    }
}
