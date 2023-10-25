package com.leduyminh.filemgtservice.exceptions;

import java.util.Map;

public class AuthenticationException extends RuntimeException {
    private Map<String, String> msgRepacle;

    public AuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String msg, Map<String, String> msgRepacle) {
        super(msg);
        this.msgRepacle = msgRepacle;
    }

    public Map<String, String> getMsgRepacle() {
        return msgRepacle;
    }

}
