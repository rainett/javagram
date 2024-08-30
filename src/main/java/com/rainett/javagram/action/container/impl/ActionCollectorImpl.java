package com.rainett.javagram.action.container.impl;

import com.rainett.javagram.action.Action;
import com.rainett.javagram.action.comparator.ActionComparator;
import com.rainett.javagram.action.container.ActionCollector;
import com.rainett.javagram.action.service.ActionComparatorService;
import com.rainett.javagram.annotations.BotAction;
import com.rainett.javagram.update.type.UpdateType;
import com.rainett.javagram.update.type.UpdateTypeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionCollectorImpl implements ActionCollector {
    private final ApplicationContext context;
    private final ActionComparatorService actionComparatorService;
    private final UpdateTypeService updateTypeService;

    @Override
    public Map<UpdateType, List<Action>> collectActions() {
        Map<UpdateType, List<Action>> actions = context
                .getBeansWithAnnotation(BotAction.class)
                .values()
                .stream()
                .map(this::objectToAction)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(updateTypeService::getUpdateTypeFromAction,
                        Collectors.toCollection(ArrayList::new)));

        actions.forEach(this::sortList);
        return actions;
    }

    private Action objectToAction(Object object) {
        if (object instanceof Action) {
            return (Action) object;
        } else {
            log.warn("Bean with @BotAction is not of type Action: {}", object.getClass());
            return null;
        }
    }

    private void sortList(UpdateType updateType, List<Action> list) {
        ActionComparator comparator = actionComparatorService.getActionComparator(updateType);
        list.sort(comparator);
    }
}
