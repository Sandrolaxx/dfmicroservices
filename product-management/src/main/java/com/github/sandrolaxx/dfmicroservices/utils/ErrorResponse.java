package com.github.sandrolaxx.dfmicroservices.utils;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

public class ErrorResponse {

  private final List<ErrorResponseImpl> errors = new ArrayList<>();

  private ErrorResponse(ConstraintViolationException exception) {
    exception.getConstraintViolations().forEach(violation -> 
    errors.add(ErrorResponseImpl.of(violation)));
  }

  public static ErrorResponse of(ConstraintViolationException exception) {
    return new ErrorResponse(exception);
  }

}
