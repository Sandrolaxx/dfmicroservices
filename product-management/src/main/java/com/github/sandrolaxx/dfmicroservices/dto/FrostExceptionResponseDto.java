package com.github.sandrolaxx.dfmicroservices.dto;

/**
 *
 * @author SRamos
 */
public class FrostExceptionResponseDto {

    private String errorDate;

    private String errorMessage;

    private String errorCode;

    private String httpCodeMessage;

    public String getErrorDate() {
        return this.errorDate;
    }

    public void setErrorDate(String errorDate) {
        this.errorDate = errorDate;
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getHttpCodeMessage() {
        return this.httpCodeMessage;
    }

    public void setHttpCodeMessage(String httpCodeMessage) {
        this.httpCodeMessage = httpCodeMessage;
    }

}
