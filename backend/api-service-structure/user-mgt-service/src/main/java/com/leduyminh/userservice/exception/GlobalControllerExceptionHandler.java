package com.leduyminh.userservice.exception;

import com.leduyminh.commons.dtos.DataResponse;
import com.leduyminh.commons.exceptions.AuthenticationException;
import com.leduyminh.commons.exceptions.LogicException;
import com.leduyminh.commons.utils.BusinessCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    MessageSource messageSource;

    protected String getMessage(String messageKey) {
        return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataResponse> exception(Exception ex) {
        System.out.print(ex.getMessage());
        return BusinessCommon.createResponse(null, getMessage("message.error.commons"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LogicException.class)
    public ResponseEntity<DataResponse> logicException(Exception ex) {
        ex.printStackTrace();
        return BusinessCommon.createResponse(null, ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<DataResponse> authenticationException(Exception ex) {
        ex.printStackTrace();
        return BusinessCommon.createResponse(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
