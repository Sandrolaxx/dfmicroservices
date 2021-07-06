package com.github.sandrolaxx.dfmicroservices.utils;

import java.util.Base64;

/**
 *
 * @author SRamos
 */
public class RequestUtil {
    
    public static String bearerToken(String token) {
        var tokenBearer = "Bearer " + token;

        return tokenBearer;
    }

    public static String onlyBasic(String token) {
        var tokenBearer = "Basic " + token;

        return tokenBearer;
    }

    public static String basicToken(String username, String password) {

        var basic = username + ":" + password;
        var base64encoder = Base64.getEncoder();
        var basicToken = "Basic " + base64encoder.encodeToString(basic.getBytes());

        return basicToken;

    }

}