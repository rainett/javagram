package com.rainett.javagram.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Executable
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String value();
    String description() default "ㅤ";
    boolean hideFromMenu() default false;
}
