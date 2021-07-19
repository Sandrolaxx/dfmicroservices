package com.github.sandrolaxx.dfmicroservices.utils;

import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumErrorCode;

/**
 *
 * @author SRamos
 */
public class FrostException extends RuntimeException {
    
    public FrostException(EnumErrorCode error) {
        super(error.getKey());
    }

}
