package com.leduyminh.filemgtservice.configs;

import com.leduyminh.commons.dtos.DataResponse;
import com.leduyminh.commons.exceptions.LogicException;
import com.leduyminh.commons.utils.BusinessCommon;
import com.leduyminh.filemgtservice.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    protected final MessageSource messageSource;

    private String getMessage(String messageKey) {
        return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataResponse> exception(Exception ex) {
        ex.printStackTrace();
        return BusinessCommon.createResponse(null, getMessage("message.action.error.commons"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<DataResponse> notAuthenticationException(AuthenticationException ex) {
        ex.printStackTrace();
        String msg = getMessage(ex.getMessage());
        if (ex.getMsgRepacle() != null && !ex.getMsgRepacle().isEmpty()) {
            for (Map.Entry<String, String> set :
                    ex.getMsgRepacle().entrySet()) {
                msg = msg.replace(set.getKey(), set.getValue());
            }
        }
        return BusinessCommon.createResponse(null, msg, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({BadCredentialsException.class, NotAcceptableException.class})
    public ResponseEntity<DataResponse> notAcceptableException(Exception ex) {
        ex.printStackTrace();
        return BusinessCommon.createResponse(null, getMessage(ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({BadRequestException.class, NoContentException.class, NotFoundException.class})
    public ResponseEntity<DataResponse> badRequestException(Exception ex) {
        ex.printStackTrace();
        return BusinessCommon.createResponse(null, getMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LogicException.class)
    public ResponseEntity<DataResponse> logicException(Exception ex) {
        ex.printStackTrace();
        return BusinessCommon.createResponse(null, getMessage(ex.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    // for default bad request exceptions
    @Override
    protected @NotNull ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                   @NotNull HttpHeaders headers,
                                                                   @NotNull HttpStatus status,
                                                                   @NotNull WebRequest request) {
        ex.printStackTrace();
        return BusinessCommon.createResponse(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
