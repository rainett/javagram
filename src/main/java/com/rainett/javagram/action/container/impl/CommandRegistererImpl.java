package com.rainett.javagram.action.container.impl;

import com.rainett.javagram.action.container.CommandRegisterer;
import com.rainett.javagram.annotations.Command;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandRegistererImpl implements CommandRegisterer {
    private final DefaultAbsSender bot;

    @Override
    public void registerCommands(List<Command> commands) {
        List<BotCommand> botCommands = filterAndMap(commands);
        if (botCommands.isEmpty()) {
            log.info("No commands to register");
            return;
        }
        SetMyCommands setCommands = new SetMyCommands(botCommands, null, null);
        try {
            bot.execute(setCommands);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to register bot commands", e);
        }
        log.info("Successfully registered {} bot commands", botCommands.size());
    }

    private static List<BotCommand> filterAndMap(List<Command> commands) {
        return commands.stream()
                .filter(c -> !c.hideFromMenu())
                .map(c -> new BotCommand(c.value(), c.description()))
                .toList();
    }
}
