package com.rainett.javagram.action.comparator;

import com.rainett.javagram.action.ActionType;
import com.rainett.javagram.update.UpdateType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ActionComparatorService {

  private final Map<UpdateType, ActionComparator> comparatorsMap;

  public ActionComparatorService(List<ActionComparator> comparatorList) {
    comparatorsMap = comparatorList.stream()
      .filter(c -> c.getClass().isAnnotationPresent(ActionType.class))
      .collect(Collectors.toMap(ActionComparator::getUpdateType, Function.identity()));
  }

  public int compare(Object o1, Object o2, UpdateType updateType) {
    if (!comparatorsMap.containsKey(updateType)) {
      return 0;
    }
    return comparatorsMap.get(updateType).compare(o1, o2);
  }
}
