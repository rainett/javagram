package com.rainett.javagram.annotations;

import com.rainett.javagram.update.UpdateType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ActionMatcherType {
  UpdateType value();
}
