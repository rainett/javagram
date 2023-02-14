package com.rainett.javagram.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Class-annotation which processes telegram commands
 */
@Executable
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * Command name, "/" symbol should be placed too
     * @return command name
     */
    String value();

    /**
     * Description of the command, displayed in the commands menu of your bot
     * @return command description
     */
    String description() default "ㅤ";

    /**
     * Weather the command should be hidden from the commands menu of your bot
     * @return true, if command is hidden from the menu
     */
    boolean hideFromMenu() default false;
}
