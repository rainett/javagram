package com.rainett.javagram.update.appready;

import com.rainett.javagram.update.appready.impl.ScopeDto;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;

public interface ScopeMapper {
    BotCommandScope getScopeFromDto(ScopeDto scopeDto);

    List<? extends BotCommandScope> getAllNoParameterScopes();
}
