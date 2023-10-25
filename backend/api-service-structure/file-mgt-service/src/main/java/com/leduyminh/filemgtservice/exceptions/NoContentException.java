package com.leduyminh.filemgtservice.exceptions;

public class NoContentException extends RuntimeException {

    public NoContentException(String msg, Throwable t) {
        super(msg, t);
    }

    public NoContentException(String msg) {
        super(msg);
    }

}
