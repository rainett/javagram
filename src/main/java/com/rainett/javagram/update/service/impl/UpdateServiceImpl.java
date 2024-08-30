package com.rainett.javagram.update.service.impl;

import com.rainett.javagram.action.Action;
import com.rainett.javagram.action.container.ActionContainer;
import com.rainett.javagram.update.service.UpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateServiceImpl implements UpdateService {
    private final ActionContainer actionContainer;

    public void handleUpdate(Update update) {
        Action botAction = actionContainer.findByUpdate(update);
        botAction.run(update);
    }
}
