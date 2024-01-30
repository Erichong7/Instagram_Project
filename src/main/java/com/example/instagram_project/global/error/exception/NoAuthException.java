package com.example.instagram_project.global.error.exception;

import com.example.instagram_project.global.error.Error;

public class NoAuthException extends RuntimeException{

    public NoAuthException() {
        super(Error.NO_AUTH_MEMBER.getMessage());
    }
}
