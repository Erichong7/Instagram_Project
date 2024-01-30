package com.example.instagram_project.global.error.exception;

import com.example.instagram_project.global.error.Error;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super(Error.WRONG_PASSWORD.getMessage());
    }
}
