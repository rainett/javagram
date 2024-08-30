package com.rainett.javagram.action.comparator;

import com.rainett.javagram.action.ActionType;
import com.rainett.javagram.update.type.UpdateType;
import java.util.Comparator;

public interface ActionComparator extends Comparator<Object> {
    default UpdateType getUpdateType() {
        return this.getClass().getDeclaredAnnotation(ActionType.class).value();
    }
}
