package com.rainett.javagram.exceptions;

/**
 * Occurs if given update had neither command, nor update
 */
public class UnknownUpdateException extends RuntimeException {
  public UnknownUpdateException(String s) {
    super(s);
  }
}
