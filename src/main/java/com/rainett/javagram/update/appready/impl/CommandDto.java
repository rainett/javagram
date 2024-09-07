package com.rainett.javagram.update.appready.impl;

import java.util.List;
import java.util.Map;

public record CommandDto(Map<String, String> descriptions, List<ScopeDto> scopes) {
}
