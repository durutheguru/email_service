package com.julianduru.email.emailservice.api;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

/**
 * created by julian
 */
public class ApiErrorResponse extends ApiResponse<String> {

    public Integer code;


    @JsonIgnore
    private Exception exception;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    public LocalDateTime timeStamp;


    public ApiErrorResponse() {
        super(Status.ERROR, null);
        initialize();
    }


    public ApiErrorResponse(String message) {
        super(Status.ERROR, message);
        initialize();
    }


    public ApiErrorResponse(String message, String data) {
        super(Status.ERROR, message, data);
        initialize();
    }


    public ApiErrorResponse(Exception exception) {
        super(Status.ERROR, ApiBodySanitizer.sanitizeMessage(exception));
        this.exception = exception;
        initialize();
    }


    private void initialize() {
        timeStamp = LocalDateTime.now();
    }


}

