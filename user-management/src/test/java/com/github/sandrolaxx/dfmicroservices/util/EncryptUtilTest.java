package com.github.sandrolaxx.dfmicroservices.util;

import com.github.sandrolaxx.dfmicroservices.utils.EncryptUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EncryptUtilTest {

    @Test
    void givenPassword_whenEncrypt_thenSuccess() throws Exception {

        String plainText = "justMoreTest";
        String password = "sandrolatesttest";

        String cipherText = EncryptUtil.textEncrypt(plainText, password);
        String decryptedCipherText = EncryptUtil.textDecrypt(cipherText, password);

        Assertions.assertEquals(plainText, decryptedCipherText);
    }

}
