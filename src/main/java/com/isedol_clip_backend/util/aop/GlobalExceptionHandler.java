package com.isedol_clip_backend.util.aop;

import com.isedol_clip_backend.exception.ApiRequestException;
import com.isedol_clip_backend.exception.InvalidJwtException;
import com.isedol_clip_backend.exception.NoExistedDataException;
import com.isedol_clip_backend.util.MakeResp;
import com.isedol_clip_backend.web.model.response.CommonResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.isedol_clip_backend.web.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(NoExistedDataException.class)
    public ResponseEntity<CommonResponse> noExistedDataHandler() {
        return MakeResp.make(HttpStatus.NO_CONTENT, "No Existed Data");
    }

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<CommonResponse> apiRequestExceptionHandler(ApiRequestException e) {
        return MakeResp.make(e.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CommonResponse> expiredJwtExceptionHandler() {
        return MakeResp.make(HttpStatus.UNAUTHORIZED, "Need Refresh Access Token. /api/refresh");
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<CommonResponse> InvalidJwtException(InvalidJwtException e) {
        return MakeResp.make(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> serverExceptionHandler(Exception e) {
        log.error("!!! FATAL ERROR !!! Unknown Exception");
        e.printStackTrace();
        return MakeResp.make(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}