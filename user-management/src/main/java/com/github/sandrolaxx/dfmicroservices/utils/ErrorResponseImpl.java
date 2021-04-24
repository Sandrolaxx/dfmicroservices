package com.github.sandrolaxx.dfmicroservices.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class ErrorResponseImpl {
  
  @Schema(description = "Path do atributo, ex: createdAt, product.createdAt", required = false)
  private final String attribute;

  @Schema(description = "Mesagem descritiva do erro possivelmente associado ao path", required = true)
  private final String message;

  private ErrorResponseImpl(ConstraintViolation<?> error) {
    this.message = error.getMessage();
    this.attribute = Stream.of(error.getPropertyPath().toString().split("\\."))
    .skip(2)
    .collect(Collectors.joining("."));
  }

  public ErrorResponseImpl(String attribute, String message) {
    this.attribute = attribute;
    this.message = message;
  }

  public static ErrorResponseImpl of(ConstraintViolation<?> error) {
    return new ErrorResponseImpl(error);
  }

}
