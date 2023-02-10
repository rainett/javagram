package com.rainett.javagram.controller.processor;

import com.rainett.javagram.annotations.Command;
import com.rainett.javagram.annotations.Run;
import com.rainett.javagram.controller.webhook.WebhookBot;
import com.rainett.javagram.controller.executables.container.ExecutablesContainer;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UpdateProcessorImpl implements UpdateProcessor {

    private final ExecutablesContainer executablesContainer;
    private final WebhookBot bot;

    public UpdateProcessorImpl(ExecutablesContainer executablesContainer, WebhookBot bot) {
        this.executablesContainer = executablesContainer;
        this.bot = bot;
        setCommandsDescriptions();
    }

    private void setCommandsDescriptions() {
        List<BotCommand> commands = getBotCommands(executablesContainer);
        try {
            bot.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error during setting commands", e);
        }
    }

    private List<BotCommand> getBotCommands(ExecutablesContainer executablesContainer) {
        return executablesContainer.getExecutables().values().stream()
                .filter(exe -> exe.getClass().isAnnotationPresent(Command.class))
                .filter(exe -> !exe.getClass().getAnnotation(Command.class).hideFromMenu())
                .map(exe -> new BotCommand(
                        exe.getClass().getAnnotation(Command.class).value(),
                        exe.getClass().getAnnotation(Command.class).description()))
                .toList();
    }

    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Optional<Object> executableOptional = executablesContainer.getExecutable(update);
        if (executableOptional.isEmpty()) {
            log.error("No executable was found for update {}, Returning null", update.toString().replaceAll("\\w+=null,? ?", ""));
            return null;
        }
        Object executable = executableOptional.get();
        LocalTime start = LocalTime.now();
        log.info("Executable received {}", executable.getClass().getName());
        Optional<Method> methodOptional = getRunnableMethod(executable);
        if (methodOptional.isEmpty()) {
            log.error("No method with annotation {} was found in the executable {}, Returning null",
                    Runnable.class.getSimpleName(), executable);
            return null;
        }
        Method method = methodOptional.get();
        invokeMethod(update, executable, method);
        int elapsed = LocalTime.now().minusNanos(start.getNano()).getNano() / 1_000_000;
        log.info("Executable {} ran, elapsed {} ms", executable.getClass().getName(), elapsed);
        return null;
    }

    private Optional<Method> getRunnableMethod(Object executable) {
        return Arrays.stream(executable.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Run.class))
                .filter(m -> m.getParameterCount() == 1)
                .filter(m -> m.getParameterTypes()[0].equals(Update.class))
                .findFirst();
    }

    private void invokeMethod(Update update, Object executable, Method method) {
        try {
            method.invoke(executable, update);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error executing method {} on executable {}", method, executable);
            log.error("", e);
        }
    }

}
