package com.rainett.javagram.action.service;

import com.rainett.javagram.action.matcher.ActionMatcher;
import com.rainett.javagram.update.type.UpdateType;

public interface ActionMatcherService {
    ActionMatcher getMatcher(UpdateType updateType);
}
