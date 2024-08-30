package com.rainett.javagram.action.container;

import com.rainett.javagram.action.Action;
import com.rainett.javagram.update.type.UpdateType;
import java.util.List;
import java.util.Map;

public interface ActionCollector {
    Map<UpdateType, List<Action>> collectActions();
}
