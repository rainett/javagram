package com.rainett.javagram.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Parent annotation for update processors,
 * like {@link com.rainett.javagram.annotations.Callback}, or {@link com.rainett.javagram.annotations.Command}.
 * Should not be used in your bot project
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Executable {
}
