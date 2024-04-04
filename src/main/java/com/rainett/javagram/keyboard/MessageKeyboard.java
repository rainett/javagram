package com.rainett.javagram.keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 * More usable wrapper for {@link InlineKeyboardMarkup}. Prefer using this instead of default
 * Telegram keyboards
 */
public class MessageKeyboard {

  private final List<MessageKeyboardRow> rows;

  /**
   * Generates keyboard based on list of rows.
   *
   * @param rows rows to be used
   */
  public MessageKeyboard(List<MessageKeyboardRow> rows) {
    this.rows = rows;
  }

  /**
   * Generates keyboard based on array of rows.
   *
   * @param messageKeyboardRows rows to be used
   */
  public MessageKeyboard(MessageKeyboardRow... messageKeyboardRows) {
    rows = new ArrayList<>();
    rows.addAll(Arrays.asList(messageKeyboardRows));
  }

  public void addRow(MessageKeyboardRow row) {
    rows.add(row);
  }

  public InlineKeyboardMarkup toInlineKeyboardMarkup() {
    if (rows.isEmpty()) {
      return null;
    }
    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> keyboard = getKeyboardFromRows();
    markup.setKeyboard(keyboard);
    return markup;
  }

  private List<List<InlineKeyboardButton>> getKeyboardFromRows() {
    List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    rows.forEach(r -> keyboard.add(r.toInlineKeyboardButtonsList()));
    return keyboard;
  }

}
