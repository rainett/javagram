package com.rainett.javagram.update;

import com.rainett.javagram.annotations.BotAction;
import com.rainett.javagram.exceptions.UnknownUpdateTypeException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class UpdateTypeService {

  private static final Map<Predicate<Update>, UpdateType> UPDATE_TYPE_MAP = new LinkedHashMap<>();

  static {
    UPDATE_TYPE_MAP.put(Update::hasChatJoinRequest, UpdateType.CHAT_JOIN_REQUEST);
    UPDATE_TYPE_MAP.put(Update::hasChatMember, UpdateType.CHAT_MEMBER);
    UPDATE_TYPE_MAP.put(Update::hasMyChatMember, UpdateType.MY_CHAT_MEMBER);
    UPDATE_TYPE_MAP.put(Update::hasPollAnswer, UpdateType.POLL_ANSWER);
    UPDATE_TYPE_MAP.put(Update::hasPoll, UpdateType.POLL_UPDATE);
    UPDATE_TYPE_MAP.put(Update::hasPreCheckoutQuery, UpdateType.PRE_CHECKOUT_QUERY);
    UPDATE_TYPE_MAP.put(Update::hasShippingQuery, UpdateType.SHIPPING_QUERY);
    UPDATE_TYPE_MAP.put(Update::hasCallbackQuery, UpdateType.CALLBACK_QUERY);
    UPDATE_TYPE_MAP.put(Update::hasChosenInlineQuery, UpdateType.CHOSEN_INLINE_QUERY);
    UPDATE_TYPE_MAP.put(Update::hasInlineQuery, UpdateType.INLINE_QUERY);
    UPDATE_TYPE_MAP.put(Update::hasEditedChannelPost, UpdateType.EDITED_CHANNEL_POST);
    UPDATE_TYPE_MAP.put(Update::hasChannelPost, UpdateType.CHANNEL_POST);
    UPDATE_TYPE_MAP.put(Update::hasEditedMessage, UpdateType.EDITED_MESSAGE);

    UPDATE_TYPE_MAP.put(u -> u.getMessage().isCommand(), UpdateType.COMMAND);
    UPDATE_TYPE_MAP.put(u -> u.getMessage().hasPoll(), UpdateType.POLL_MESSAGE);
    UPDATE_TYPE_MAP.put(u -> u.getMessage().hasText(), UpdateType.TEXT);
  }

  public UpdateType getUpdateTypeFromUpdate(Update update) {
    for (Predicate<Update> predicate : UPDATE_TYPE_MAP.keySet()) {
      if (predicate.test(update)) {
        return getUpdateType(predicate);
      }
    }
    throw new UnknownUpdateTypeException(update);
  }

  public UpdateType getUpdateTypeFromAction(Object action) {
    return Objects.requireNonNull(AnnotationUtils
      .findAnnotation(action.getClass(), BotAction.class)).value();
  }

  private UpdateType getUpdateType(Predicate<Update> predicate) {
    return UPDATE_TYPE_MAP.get(predicate);
  }

}
