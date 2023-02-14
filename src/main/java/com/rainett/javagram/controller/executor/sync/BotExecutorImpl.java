package com.rainett.javagram.controller.executor.sync;

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

@RequiredArgsConstructor
@Slf4j
public class BotExecutorImpl implements BotExecutor {

    private final WebhookBot bot;

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) {

        try {
            return bot.execute(method);
        } catch (TelegramApiException e) {
            log.error("Error executing method", e);
            return null;
        }
    }

    @Override
    public Message execute(SendAudio sendAudio) {
        try {
            return bot.execute(sendAudio);
        } catch (TelegramApiException e) {
            log.error("Error sending audio", e);
            return null;
        }
    }

    @Override
    public Message execute(SendPhoto sendPhoto) {
        try {
            return bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("Error sending photo", e);
            return null;
        }
    }

    @Override
    public Message execute(SendVideo sendVideo) {
        try {
            return bot.execute(sendVideo);
        } catch (TelegramApiException e) {
            log.error("Error sending video", e);
            return null;
        }
    }

    @Override
    public Message execute(SendVoice sendVoice) {
        try {
            return bot.execute(sendVoice);
        } catch (TelegramApiException e) {
            log.error("Error sending voice", e);
            return null;
        }
    }

    @Override
    public Message execute(SendSticker sendSticker) {
        try {
            return bot.execute(sendSticker);
        } catch (TelegramApiException e) {
            log.error("Error sending sticker", e);
            return null;
        }
    }

    @Override
    public Message execute(SendDocument sendDocument) {
        try {
            return bot.execute(sendDocument);
        } catch (TelegramApiException e) {
            log.error("Error sending document", e);
            return null;
        }
    }

    @Override
    public Boolean execute(SetChatPhoto setChatPhoto) {
        try {
            return bot.execute(setChatPhoto);
        } catch (TelegramApiException e) {
            log.error("Error setting chat photo", e);
            return false;
        }
    }

    @Override
    public Message execute(SendAnimation sendAnimation) {
        try {
            return bot.execute(sendAnimation);
        } catch (TelegramApiException e) {
            log.error("Error sending animation", e);
            return null;
        }
    }

    @Override
    public Message execute(SendVideoNote sendVideoNote) {
        try {
            return bot.execute(sendVideoNote);
        } catch (TelegramApiException e) {
            log.error("Error sending video note", e);
            return null;
        }
    }

    @Override
    public List<Message> execute(SendMediaGroup sendMediaGroup) {
        try {
            return bot.execute(sendMediaGroup);
        } catch (TelegramApiException e) {
            log.error("Error sending media group", e);
            return List.of();
        }
    }

    @Override
    public Boolean execute(AddStickerToSet addStickerToSet) {
        try {
            return bot.execute(addStickerToSet);
        } catch (TelegramApiException e) {
            log.error("Error adding sticker to set", e);
            return false;
        }
    }

    @Override
    public Serializable execute(EditMessageMedia editMessageMedia) {
        try {
            return bot.execute(editMessageMedia);
        } catch (TelegramApiException e) {
            log.error("Error editing message media", e);
            return false;
        }
    }

    @Override
    public File execute(UploadStickerFile uploadStickerFile) {
        try {
            return bot.execute(uploadStickerFile);
        } catch (TelegramApiException e) {
            log.error("Error uploading sticker file", e);
            return null;
        }
    }

    @Override
    public Boolean execute(SetStickerSetThumb setStickerSetThumb) {
        try {
            return bot.execute(setStickerSetThumb);
        } catch (TelegramApiException e) {
            log.error("Error setting sticker set thumb", e);
            return false;
        }
    }

    @Override
    public Boolean execute(CreateNewStickerSet createNewStickerSet) {
        try {
            return bot.execute(createNewStickerSet);
        } catch (TelegramApiException e) {
            log.error("Error creating new sticker set", e);
            return false;
        }
    }



}
