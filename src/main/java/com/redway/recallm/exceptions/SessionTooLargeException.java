package com.redway.recallm.exceptions;

public class SessionTooLargeException extends RuntimeException {
  public SessionTooLargeException(String message) {
    super(message);
  }
}