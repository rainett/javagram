package com.rainett.javagram.action.comparator;

import com.rainett.javagram.action.ActionType;
import com.rainett.javagram.annotations.command.Command;
import com.rainett.javagram.update.type.UpdateType;

@ActionType(UpdateType.COMMAND)
public class CommandComparator implements ActionComparator {
    @Override
    public int compare(Object c1, Object c2) {
        Command c1Annotation = c1.getClass().getDeclaredAnnotation(Command.class);
        Command c2Annotation = c2.getClass().getDeclaredAnnotation(Command.class);
        return c2Annotation.value().length() - c1Annotation.value().length();
    }
}
