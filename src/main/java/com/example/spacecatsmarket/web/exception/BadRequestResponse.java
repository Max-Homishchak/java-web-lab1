package com.example.spacecatsmarket.web.exception;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class BadRequestResponse {
    int status;
    String error;
    String message;
    String path;
    List<ParamsViolationDetails> invalidParams;
}
