package com.rainett.javagram.controller.executor.async;

import com.rainett.javagram.controller.webhook.WebhookBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.meta.api.methods.stickers.SetStickerSetThumb;
import org.telegram.telegrambots.meta.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class BotExecutorAsyncImpl implements BotExecutorAsync {

    private final WebhookBot bot;

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> execute(Method method) {
        try {
            return bot.executeAsync(method);
        } catch (TelegramApiException e) {
            log.error("Error executing method", e);
            return null;
        }
    }

    @Override
    public CompletableFuture<Message> execute(SendAudio sendAudio) {
        return bot.executeAsync(sendAudio);
    }

    @Override
    public CompletableFuture<Message> execute(SendPhoto sendPhoto) {
        return bot.executeAsync(sendPhoto);
    }

    @Override
    public CompletableFuture<Message> execute(SendVideo sendVideo) {
        return bot.executeAsync(sendVideo);
    }

    @Override
    public CompletableFuture<Message> execute(SendVoice sendVoice) {
        return bot.executeAsync(sendVoice);
    }

    @Override
    public CompletableFuture<Message> execute(SendSticker sendSticker) {
        return bot.executeAsync(sendSticker);
    }

    @Override
    public CompletableFuture<Message> execute(SendDocument sendDocument) {
        return bot.executeAsync(sendDocument);
    }

    @Override
    public CompletableFuture<Boolean> execute(SetChatPhoto setChatPhoto) {
        return bot.executeAsync(setChatPhoto);
    }

    @Override
    public CompletableFuture<Message> execute(SendAnimation sendAnimation) {
        return bot.executeAsync(sendAnimation);
    }

    @Override
    public CompletableFuture<Message> execute(SendVideoNote sendVideoNote) {
        return bot.executeAsync(sendVideoNote);
    }

    @Override
    public CompletableFuture<List<Message>> execute(SendMediaGroup sendMediaGroup) {
        return bot.executeAsync(sendMediaGroup);
    }

    @Override
    public CompletableFuture<Boolean> execute(AddStickerToSet addStickerToSet) {
        return bot.executeAsync(addStickerToSet);
    }

    @Override
    public CompletableFuture<Serializable> execute(EditMessageMedia editMessageMedia) {
        return bot.executeAsync(editMessageMedia);
    }

    @Override
    public CompletableFuture<File> execute(UploadStickerFile uploadStickerFile) {
        return bot.executeAsync(uploadStickerFile);
    }

    @Override
    public CompletableFuture<Boolean> execute(SetStickerSetThumb setStickerSetThumb) {
        return bot.executeAsync(setStickerSetThumb);
    }

    @Override
    public CompletableFuture<Boolean> execute(CreateNewStickerSet createNewStickerSet) {
        return bot.executeAsync(createNewStickerSet);
    }


}
