package com.rainett.javagram.update.appready.impl;

import com.rainett.javagram.annotations.command.Command;
import com.rainett.javagram.config.BotConfig;
import com.rainett.javagram.update.appready.CommandRegisterer;
import com.rainett.javagram.update.appready.ScopeMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.commands.DeleteMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandRegistererImpl implements CommandRegisterer {
    private static final int SKIP_CHARS = 1;
    private final DefaultAbsSender bot;
    private final BotConfig botConfig;
    private final ScopeMapper scopeMapper;

    @Override
    public void registerCommands(List<Command> commands) {
        deleteAllCommands();
        setAllCommands(commands);
    }

    private void setAllCommands(List<Command> commands) {
        List<SetMyCommands> setMyCommandsList = getSetMyCommands(commands);
        for (SetMyCommands setMyCommands : setMyCommandsList) {
            trySettingCommands(setMyCommands);
        }
        String registeredCommandsReport = getReport(setMyCommandsList);
        log.info("Registered commands: {}", registeredCommandsReport);
    }

    private void deleteAllCommands() {
        Map<String, CommandDto> commands = botConfig.getCommands();
        Set<String> languageCodes = new HashSet<>();
        languageCodes.add(null);
        if (commands != null && !commands.isEmpty()) {
            languageCodes.addAll(getLanguageCodes(commands));
        }
        List<? extends BotCommandScope> scopes = scopeMapper.getAllNoParameterScopes();
        for (BotCommandScope scope : scopes) {
            for (String languageCode : languageCodes) {
                tryDeletingCommands(scope, languageCode);
            }
        }
        log.info("Successfully deleted previous commands. "
                + "Keep in mind, some commands may still be present "
                + "due to unknown scopes or language codes. "
                + "In that case, try manually removing them with DeleteMyCommands.");
    }

    private void tryDeletingCommands(BotCommandScope scope, String languageCode) {
        try {
            bot.execute(new DeleteMyCommands(scope, languageCode));
            log.debug("Deleted commands with scope = [{}] and language = [{}]",
                    scope, languageCode);
        } catch (TelegramApiException e) {
            log.error("Failed to delete bot commands "
                    + "for scope = [{}] "
                    + "and language = [{}]", scope, languageCode, e);
            throw new RuntimeException("Failed to delete bot commands "
                    + "for scope = [" + scope + "] "
                    + "and language = [" + languageCode + "]", e);
        }
    }

    private static Set<String> getLanguageCodes(Map<String, CommandDto> commands) {
        return commands.values().stream()
                .filter(Objects::nonNull)
                .map(CommandDto::descriptions)
                .filter(Objects::nonNull)
                .flatMap(descriptions -> descriptions.keySet().stream())
                .collect(Collectors.toSet());
    }

    private void trySettingCommands(SetMyCommands setMyCommands) {
        try {
            bot.execute(setMyCommands);
            log.debug("Set commands for scope = [{}] and language = [{}]",
                    setMyCommands.getScope(), setMyCommands.getLanguageCode());
        } catch (TelegramApiException e) {
            log.error("Failed to register command [{}] with scope [{}] and language [{}]",
                    setMyCommands.getCommands(),
                    setMyCommands.getScope(),
                    setMyCommands.getLanguageCode(),
                    e);
            throw new RuntimeException("Failed to register commands = ["
                    + setMyCommands + "]", e);
        }
    }

    private static String getReport(List<SetMyCommands> setMyCommandsList) {
        return setMyCommandsList.stream()
                .map(setCommands -> String.format("%s - %s: %s",
                        setCommands.getScope(),
                        setCommands.getLanguageCode(),
                        setCommands.getCommands().stream()
                                .map(BotCommand::getCommand)
                                .collect(Collectors.joining(", "))))
                .collect(Collectors.joining("; "));
    }

    private List<SetMyCommands> getSetMyCommands(List<Command> commands) {
        Map<BotCommandScope, LanguagesCommands> scopeCodeMap = getScopeCodeMap(commands);
        List<SetMyCommands> setMyCommandsList = new ArrayList<>();
        for (BotCommandScope scope : scopeCodeMap.keySet()) {
            LanguagesCommands languagesCommandsMap = scopeCodeMap.get(scope);
            for (String languageCode : languagesCommandsMap.getLanguageCodes()) {
                BotCommands botCommands = languagesCommandsMap.getBotCommands(languageCode);
                SetMyCommands setMyCommands =
                        new SetMyCommands(botCommands.getCommands(), scope, languageCode);
                setMyCommandsList.add(setMyCommands);
            }
        }
        return setMyCommandsList;
    }

    private Map<BotCommandScope, LanguagesCommands> getScopeCodeMap(List<Command> commands) {
        ScopeMap scopeMap = new ScopeMap();
        for (Command command : commands) {
            if (command.hideFromMenu()) {
                continue;
            }
            String commandName = command.value();
            String description = command.description();
            MapCommand mapCommand =
                    MapCommand.initialMapCommand(commandName, description);
            Map<String, CommandDto> propertiesCommands = botConfig.getCommands();
            if (propertiesCommands == null || propertiesCommands.isEmpty()) {
                scopeMap.addCommand(mapCommand);
                continue;
            }
            CommandDto commandDto = propertiesCommands.get(commandName.substring(SKIP_CHARS));
            if (commandDto == null) {
                scopeMap.addCommand(mapCommand);
                continue;
            }
            handleCommandDto(scopeMap, mapCommand, commandDto);
        }
        return scopeMap.getScopeMap();
    }

    private void handleCommandDto(ScopeMap scopeMap,
                                  MapCommand mapCommand,
                                  CommandDto commandDto) {
        List<ScopeDto> scopes = commandDto.scopes();
        Map<String, String> descriptions = commandDto.descriptions();
        if (scopes == null || scopes.isEmpty()) {
            scopeMap.getDescriptionAndAdd(mapCommand, descriptions);
            return;
        }
        for (ScopeDto scopeDto : scopes) {
            BotCommandScope scope = scopeMapper.getScopeFromDto(scopeDto);
            mapCommand.setScope(scope);
            scopeMap.getDescriptionAndAdd(mapCommand, descriptions);
        }
    }

    @Data
    private static class ScopeMap {
        private Map<BotCommandScope, LanguagesCommands> scopeMap;

        public ScopeMap() {
            this.scopeMap = new HashMap<>();
        }

        public void getDescriptionAndAdd(MapCommand mapCommand,
                                         Map<String, String> descriptions) {
            if (descriptions == null || descriptions.isEmpty()) {
                this.addCommand(mapCommand);
                return;
            }
            for (Map.Entry<String, String> entry : descriptions.entrySet()) {
                mapCommand.setLanguageCode(entry.getKey());
                mapCommand.setDescription(entry.getValue());
                this.addCommand(mapCommand);
            }
        }

        public void addCommand(MapCommand mapCommand) {
            String commandName = mapCommand.getCommandName();
            String description = mapCommand.getDescription();
            BotCommandScope scope = mapCommand.getScope();
            String languageCode = mapCommand.getLanguageCode();

            BotCommand botCommand = new BotCommand(commandName, description);
            scopeMap.putIfAbsent(scope, new LanguagesCommands());
            LanguagesCommands languagesCommands = scopeMap.get(scope);
            languagesCommands.putIfAbsent(languageCode, new BotCommands());
            BotCommands botCommands = languagesCommands.getBotCommands(languageCode);
            botCommands.addCommand(botCommand);
        }
    }

    @Data
    private static class LanguagesCommands {
        private Map<String, BotCommands> descriptions;

        public LanguagesCommands() {
            this.descriptions = new HashMap<>();
        }

        public Set<String> getLanguageCodes() {
            return descriptions.keySet();
        }

        public BotCommands getBotCommands(String languageCode) {
            return descriptions.get(languageCode);
        }

        public void putIfAbsent(String languageCode, BotCommands botCommands) {
            descriptions.putIfAbsent(languageCode, botCommands);
        }
    }

    @Data
    private static class BotCommands {
        private List<BotCommand> commands;

        public BotCommands() {
            this.commands = new ArrayList<>();
        }

        public void addCommand(BotCommand command) {
            commands.add(command);
        }

        public List<BotCommand> getCommands() {
            return commands;
        }
    }

    @Data
    private static class MapCommand {
        private BotCommandScope scope;
        private String languageCode;
        private String commandName;
        private String description;

        public static MapCommand initialMapCommand(String commandName, String description) {
            MapCommand mapCommand = new MapCommand();
            mapCommand.setScope(null);
            mapCommand.setLanguageCode(null);
            mapCommand.setCommandName(commandName);
            mapCommand.setDescription(description);
            return mapCommand;
        }
    }
}
