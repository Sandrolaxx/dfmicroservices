package com.github.sandrolaxx.dfmicroservices.util;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

/**
 * Utilities for generating a JWT for testing
 */
public class TokenUtils {

  private TokenUtils() {
    // no-op: utility class
  }

  /**
   * Utility method to generate a JWT string from a JSON resource file that is
   * signed by the privateKey.pem test resource key, possibly with invalid fields.
   *
   * @param jsonResName - name of test resources file
   * @param timeClaims  - used to return the exp, iat, auth_time claims
   * @return the JWT string
   * @throws Exception on parse failure
   */
  public static String generateTokenString(String jsonResName, Map<String, Long> timeClaims) throws Exception {
    // Use the test private key associated with the test public key for a valid
    // signature
    PrivateKey pk = readPrivateKey("/privateKey.pem");
    return generateTokenString(pk, "/privateKey.pem", jsonResName, timeClaims);
  }

  public static String generateTokenString(PrivateKey privateKey, String kid, String jsonResName,
      Map<String, Long> timeClaims) throws Exception {

    JwtClaimsBuilder claims = Jwt.claims(jsonResName);
    long currentTimeInSecs = currentTimeInSecs();
    long exp = timeClaims != null && timeClaims.containsKey(Claims.exp.name()) ? timeClaims.get(Claims.exp.name())
        : currentTimeInSecs + 300;

    claims.issuedAt(currentTimeInSecs);
    claims.claim(Claims.auth_time.name(), currentTimeInSecs);
    claims.expiresAt(exp);

    return claims.jws().keyId(kid).sign(privateKey);
  }

  /**
   * Read a PEM encoded private key from the classpath
   *
   * @param pemResName - key file resource name
   * @return PrivateKey
   * @throws Exception on decode failure
   */
  public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
    try (InputStream contentIS = TokenUtils.class.getResourceAsStream(pemResName)) {
      byte[] tmp = new byte[4096];
      int length = contentIS.read(tmp);
      return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
    }
  }

  /**
   * Decode a PEM encoded private key string to an RSA PrivateKey
   *
   * @param pemEncoded - PEM string for private key
   * @return PrivateKey
   * @throws Exception on decode failure
   */
  public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
    byte[] encodedBytes = toEncodedBytes(pemEncoded);

    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePrivate(keySpec);
  }

  private static byte[] toEncodedBytes(final String pemEncoded) {
    final String normalizedPem = removeBeginEnd(pemEncoded);
    return Base64.getDecoder().decode(normalizedPem);
  }

  private static String removeBeginEnd(String pem) {
    pem = pem.replaceAll("-----BEGIN (.*)-----", "");
    pem = pem.replaceAll("-----END (.*)----", "");
    pem = pem.replaceAll("\r\n", "");
    pem = pem.replaceAll("\n", "");
    return pem.trim();
  }

  /**
   * @return the current time in seconds since epoch
   */
  public static int currentTimeInSecs() {
    long currentTimeMS = System.currentTimeMillis();
    return (int) (currentTimeMS / 1000);
  }

}
