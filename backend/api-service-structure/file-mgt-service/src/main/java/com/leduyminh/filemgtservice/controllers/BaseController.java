package com.leduyminh.filemgtservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public abstract class BaseController {
    @Autowired
    MessageSource messageSource;

    protected String getMessage(String messageKey) {
        return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }
}
