package com.leduyminh.filemgtservice.exceptions;

public class NotAcceptableException extends RuntimeException {

    public NotAcceptableException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotAcceptableException(String msg) {
        super(msg);
    }

}
