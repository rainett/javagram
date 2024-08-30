package com.rainett.javagram.action.service.impl;

import com.rainett.javagram.action.ActionType;
import com.rainett.javagram.action.comparator.ActionComparator;
import com.rainett.javagram.action.service.ActionComparatorService;
import com.rainett.javagram.update.type.UpdateType;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ActionComparatorServiceImpl implements ActionComparatorService {
    private final Map<UpdateType, ActionComparator> comparatorsMap;

    public ActionComparatorServiceImpl(List<ActionComparator> comparatorList) {
        comparatorsMap = comparatorList.stream()
                .filter(c -> c.getClass().isAnnotationPresent(ActionType.class))
                .collect(Collectors.toMap(ActionComparator::getUpdateType, Function.identity()));
    }

    public ActionComparator getActionComparator(UpdateType updateType) {
        return Optional.ofNullable(comparatorsMap.get(updateType))
                .orElseThrow(() -> new NoSuchElementException("Action comparator not found for "
                        + "update type = [" + updateType + "]"));
    }
}
