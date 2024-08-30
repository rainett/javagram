package com.rainett.javagram.action.container;

import com.rainett.javagram.annotations.Command;
import java.util.List;

public interface CommandRegisterer {
    void registerCommands(List<Command> commands);
}
