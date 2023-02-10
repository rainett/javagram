package com.rainett.javagram.controller.executor;

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

public interface BotExecutor {

    <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method);

    Message execute(SendAudio sendAudio);

    Message execute(SendAnimation sendAnimation);

    Message execute(SendPhoto sendPhoto);

    Message execute(SendVideo sendVideo);

    Message execute(SendDocument sendDocument);

    Message execute(SendVoice sendVoice);

    Message execute(SendSticker sendSticker);

    Message execute(SendVideoNote sendVideoNote);

    List<Message> execute(SendMediaGroup sendMediaGroup);

    Boolean execute(AddStickerToSet addStickerToSet);

    Boolean execute(CreateNewStickerSet createNewStickerSet);

    Boolean execute(SetChatPhoto setChatPhoto);

    Boolean execute(SetStickerSetThumb setStickerSetThumb);

    File execute(UploadStickerFile uploadStickerFile);

    Serializable execute(EditMessageMedia editMessageMedia);
}
