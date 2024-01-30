package com.example.instagram_project.global.error.exception;

import com.example.instagram_project.global.error.Error;

public class PasswordUncheckedException extends RuntimeException {

    public PasswordUncheckedException() {
        super(Error.PASSWORD_UNCHECK.getMessage());
    }
}
