package com.leduyminh.filemgtservice.configs;

import com.leduyminh.commons.dtos.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException, ServletException {
        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "180");
        DataResponse dataResponse = DataResponse
                .withCode(HttpStatus.FORBIDDEN.value())
                .withMessage("Không có quyền thực hiện")
                .withDescription("Không có quyền thực hiện")
                .build();
        response.getWriter().write(dataResponse.toString());
        exc.printStackTrace();
    }
}
