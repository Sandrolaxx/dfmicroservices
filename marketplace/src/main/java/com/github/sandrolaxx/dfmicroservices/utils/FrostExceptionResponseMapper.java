package com.github.sandrolaxx.dfmicroservices.utils;

import java.util.Calendar;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.github.sandrolaxx.dfmicroservices.dto.FrostExceptionResponseDto;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumErrorCode;

import org.jboss.resteasy.util.DateUtil;

/**
 *
 * @author SRamos
 */
@Provider
public class FrostExceptionResponseMapper implements ExceptionMapper<FrostException> {

    @Override
    public Response toResponse(FrostException ex) {

        FrostExceptionResponseDto exceptionResponse = new FrostExceptionResponseDto();

        var error = EnumErrorCode.parseByKey(ex.getMessage());
        int httpStatus = error.getHttpStatus();

        var formattedDate = DateUtil.formatDate(Calendar.getInstance().getTime(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        var errorPhrase = Status.fromStatusCode(httpStatus).getReasonPhrase();

        exceptionResponse.setErrorCode(error.getKey());
        exceptionResponse.setHttpCodeMessage(errorPhrase);
        
        exceptionResponse.setErrorDate(formattedDate);
        exceptionResponse.setErrorMessage(error.getValue());

        return Response.status(httpStatus).entity(exceptionResponse).build();

    }

}
