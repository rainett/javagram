package com.rainett.javagram.exceptions;

public class RunnableMethodNotFound extends RuntimeException {

  public RunnableMethodNotFound(Object object) {
    super("No runnable method found in object = [" + object + "]");
  }
}
