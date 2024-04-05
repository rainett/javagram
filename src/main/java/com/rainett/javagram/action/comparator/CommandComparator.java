package com.rainett.javagram.action.comparator;


import com.rainett.javagram.action.ActionType;
import com.rainett.javagram.annotations.Command;
import com.rainett.javagram.update.UpdateType;
import java.util.Comparator;

@ActionType(UpdateType.COMMAND)
public class CommandComparator implements ActionComparator {

  /**
   * Compares two commands.
   * {@link ActionComparatorService} is using {@link Comparator#reversed()}.
   *
   * @param c1 the first object to be compared.
   * @param c2 the second object to be compared.
   * @return comparison result
   */
  @Override
  public int compare(Object c1, Object c2) {
    Command c1Annotation = c1.getClass().getDeclaredAnnotation(Command.class);
    Command c2Annotation = c2.getClass().getDeclaredAnnotation(Command.class);
    return c2Annotation.value().length() - c1Annotation.value().length();
  }
}
