package com.rainett.javagram.update.appready;

import com.rainett.javagram.annotations.command.Command;
import java.util.List;

public interface CommandRegisterer {
    void registerCommands(List<Command> commands);
}
