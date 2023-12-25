package com.fpt.egov.apigateway.configs.security.csrf;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/csrf", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(exposedHeaders = "csrf-token,csrf-session")
public class CsrfGenTokenServlet {
    @GetMapping(value = "/get")
    public ResponseEntity genCsrfToken() {
        return ResponseEntity.ok(null);
    }
}
