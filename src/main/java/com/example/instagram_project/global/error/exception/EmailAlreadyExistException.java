package com.example.instagram_project.global.error.exception;

import com.example.instagram_project.global.error.Error;

public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException() {
        super(Error.EMAIL_ALREADY_EXIST.getMessage());
    }
}
