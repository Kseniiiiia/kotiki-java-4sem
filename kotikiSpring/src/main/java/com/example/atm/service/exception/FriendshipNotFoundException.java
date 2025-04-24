package com.example.atm.service.exception;

public class FriendshipNotFoundException extends RuntimeException {
  public FriendshipNotFoundException(Long id) {
      super("Owner not found with id: " + id);
  }
}
