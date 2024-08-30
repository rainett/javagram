package com.rainett.javagram.action.service;

import com.rainett.javagram.action.comparator.ActionComparator;
import com.rainett.javagram.update.type.UpdateType;

public interface ActionComparatorService {
    ActionComparator getActionComparator(UpdateType updateType);
}
