package com.rainett.javagram.update.appready.impl;

import com.rainett.javagram.update.appready.ScopeMapper;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllGroupChats;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllPrivateChats;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChat;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChatMember;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;

@Service
public class ScopeMapperImpl implements ScopeMapper {
    private static final Map<String,
            BiFunction<Long, Long, ? extends BotCommandScope>> scopeMap = Map.of(
            "default", (chatId, userId) ->
                    new BotCommandScopeDefault(),
            "all_private_chats", (chatId, userId) ->
                    new BotCommandScopeAllPrivateChats(),
            "all_group_chats", (chatId, userId) ->
                    new BotCommandScopeAllGroupChats(),
            "all_chat_administrators", (chatId, userId) ->
                    new BotCommandScopeAllChatAdministrators(),
            "chat", (chatId, userId) ->
                    new BotCommandScopeChat(chatId.toString()),
            "chat_administrators", (chatId, userId) ->
                    new BotCommandScopeChatAdministrators(chatId.toString()),
            "chat_member", (chatId, userId) ->
                    new BotCommandScopeChatMember(chatId.toString(), userId)
    );

    @Override
    public BotCommandScope getScopeFromDto(ScopeDto scopeDto) {
        if (!scopeMap.containsKey(scopeDto.type())) {
            return scopeMap.get("default").apply(0L, 0L);
        }
        return scopeMap.get(scopeDto.type()).apply(scopeDto.chatId(), scopeDto.userId());
    }

    @Override
    public List<? extends BotCommandScope> getAllNoParameterScopes() {
        return scopeMap.values().stream()
                .map(biFunction -> biFunction.apply(1L, 1L))
                .filter(ScopeMapperImpl::hasSingleField)
                .collect(Collectors.toList());
    }

    private static boolean hasSingleField(BotCommandScope scope) {
        return scope.getClass().getDeclaredFields().length == 2;
    }
}
