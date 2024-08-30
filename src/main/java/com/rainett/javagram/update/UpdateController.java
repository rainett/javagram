package com.rainett.javagram.update;

import com.rainett.javagram.update.service.UpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Main controller. Receives Telegram updates as HTTP POST requests
 */
@Slf4j
@ConditionalOnProperty(name = "bot.path")
@RestController
@RequiredArgsConstructor
public class UpdateController {
    private static final String NULL_VALUES_REGEX = "\\w+=null,? ?";
    private static final String REPLACEMENT = "";
    private final UpdateService updateService;

    @PostMapping
    public void onUpdateReceived(@RequestBody Update update) {
        String nullFreeUpdate = update.toString().replaceAll(NULL_VALUES_REGEX, REPLACEMENT);
        log.info("Received an update, {}", nullFreeUpdate);
        updateService.handleUpdate(update);
    }

    @GetMapping
    public String getString() {
        return "Yes, it's running";
    }
}
