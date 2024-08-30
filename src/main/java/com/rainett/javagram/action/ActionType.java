package com.rainett.javagram.action;

import com.rainett.javagram.update.type.UpdateType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ActionType {
    UpdateType value();
}
