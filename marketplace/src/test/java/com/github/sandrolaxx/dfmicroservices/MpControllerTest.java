package com.github.sandrolaxx.dfmicroservices;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class MpControllerTest {

    @Test
    public void testListProducts() {
        given()
          .when().get("/dona-frost/v1/products")
          .then()
             .statusCode(200);
    }

}