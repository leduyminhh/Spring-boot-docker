package com.leduyminh.commons.exceptions;

public class LogicException extends RuntimeException {

    private int code;

    public LogicException(String msg, Throwable t) {
        super(msg, t);
    }

    public LogicException(String msg) {
        super(msg);
    }

    public LogicException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
