package com.rainett.javagram.controller.executor.async;

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

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Wrapper for Telegram {@link com.rainett.javagram.controller.webhook.WebhookBot}
 * Prefer this over default WebhookBot, if you don't care about {@link org.telegram.telegrambots.meta.exceptions.TelegramApiException} processing.
 */
public interface BotExecutorAsync {

    <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> execute(Method method);

    CompletableFuture<Message> execute(SendAudio sendAudio);

    CompletableFuture<Message> execute(SendAnimation sendAnimation);

    CompletableFuture<Message> execute(SendPhoto sendPhoto);

    CompletableFuture<Message> execute(SendVideo sendVideo);

    CompletableFuture<Message> execute(SendDocument sendDocument);

    CompletableFuture<Message> execute(SendVoice sendVoice);

    CompletableFuture<Message> execute(SendSticker sendSticker);

    CompletableFuture<Message> execute(SendVideoNote sendVideoNote);

    CompletableFuture<List<Message>> execute(SendMediaGroup sendMediaGroup);

    CompletableFuture<Boolean> execute(AddStickerToSet addStickerToSet);

    CompletableFuture<Boolean> execute(CreateNewStickerSet createNewStickerSet);

    CompletableFuture<Boolean> execute(SetChatPhoto setChatPhoto);

    CompletableFuture<Boolean> execute(SetStickerSetThumb setStickerSetThumb);

    CompletableFuture<File> execute(UploadStickerFile uploadStickerFile);

    CompletableFuture<Serializable> execute(EditMessageMedia editMessageMedia);
}
