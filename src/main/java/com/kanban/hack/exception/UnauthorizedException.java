package com.kanban.hack.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String massage){
        super(massage);
    }
}
