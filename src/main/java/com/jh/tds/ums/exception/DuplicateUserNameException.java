package com.jh.tds.ums.exception;

public class DuplicateUserNameException extends RuntimeException {

    public DuplicateUserNameException(String user) {
        super("Username '" + user + "' already exists.");

    }
}
