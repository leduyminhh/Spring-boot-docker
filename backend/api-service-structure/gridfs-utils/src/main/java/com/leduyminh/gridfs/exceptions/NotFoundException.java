package com.leduyminh.gridfs.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotFoundException(String msg) {
        super(msg);
    }

}
