package com.example.instagram_project.global.error.exception;

import com.example.instagram_project.global.error.Error;

public class FollowNestingException extends RuntimeException {

    public FollowNestingException() {
        super(Error.FOLLOW_NESTING.getMessage());
    }
}
