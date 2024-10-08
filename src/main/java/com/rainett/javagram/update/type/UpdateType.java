package com.rainett.javagram.update.type;

public enum UpdateType {

  // Updates
  CHAT_JOIN_REQUEST, CHAT_MEMBER, MY_CHAT_MEMBER,
  POLL_ANSWER, POLL_UPDATE, PRE_CHECKOUT_QUERY, SHIPPING_QUERY,
  CALLBACK_QUERY, CHOSEN_INLINE_QUERY, INLINE_QUERY,
  EDITED_CHANNEL_POST, CHANNEL_POST, EDITED_MESSAGE,

  // Messages
  TEXT, DELETE_CHAT_PHOTO, GROUP_CHAT_CREATED, SUPER_GROUP_CREATED,
  CHANNEL_CHAT_CREATED, NEW_CHAT_MEMBERS, LEFT_CHAT_MEMBER, PINNED_MESSAGE,
  PHOTO, NEW_CHAT_PHOTO, AUDIO, DOCUMENT, STICKER, VIDEO, CONTACT, LOCATION,
  VENUE, ANIMATION, VOICE, GAME, INVOICE, SUCCESSFUL_PAYMENT, VIDEO_NOTE,
  PASSPORT_DATA, POLL_MESSAGE, DICE, PROXIMITY_ALERT_TRIGGERED, MESSAGE_AUTO_DELETE_TIMER_CHANGED,
  WEB_APP_DATA, VIDEO_CHAT_STARTED, VIDEO_CHAT_ENDED, VIDEO_CHAT_PARTICIPANTS_INVITED,
  VIDEO_CHAT_SCHEDULED, FORUM_TOPIC_CREATED, FORUM_TOPIC_CLOSED, FORUM_TOPIC_REOPENED,
  FORUM_TOPIC_EDITED, GENERAL_FORUM_TOPIC_HIDDEN, GENERAL_FORUM_TOPIC_UNHIDDEN,
  WRITE_ACCESS_ALLOWED, USER_SHARED, CHAT_SHARED, STORY, COMMAND,

}
