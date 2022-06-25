package org.test.restAPI.controller.advice;


import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.test.restAPI.controller.APIUserController;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class APIAdvice {

    @ExceptionHandler({APIUserController.APIUserNotFoundException.class})
    public ResponseEntity<Map<String, String>> findUserError(APIUserController.APIUserNotFoundException e) {

        log.error(e);
        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("TIME", ""+System.currentTimeMillis());
        errorMap.put("RESULT",  "USER ACCOUNT NOT FOUND");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler({APIUserController.BadRefreshRequestException.class})
    public ResponseEntity<Map<String, String>> refrehRequestError(APIUserController.BadRefreshRequestException e) {

        log.error(e);
        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("TIME", ""+System.currentTimeMillis());
        errorMap.put("RESULT",  "check grant_type & refresh_token parameter");

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorMap);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<Map<String, String>> expiredJWTError(ExpiredJwtException e) {

        log.error(e);
        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("TIME", ""+System.currentTimeMillis());
        errorMap.put("RESULT",  "EXPIRED REFRESH TOKEN");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
    }

    @ExceptionHandler({java.util.NoSuchElementException.class})
    public ResponseEntity<Map<String, String>> notFound(ExpiredJwtException e) {

        log.error(e);
        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("TIME", ""+System.currentTimeMillis());
        errorMap.put("RESULT",  "Cannot Find Data");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
    }




}
