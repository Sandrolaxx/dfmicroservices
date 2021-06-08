package com.github.sandrolaxx.dfmicroservices;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

import org.approvaltests.Approvals;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response.Status;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.sandrolaxx.dfmicroservices.dto.ProductCreateDto;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.util.TokenUtils;

@DBRider
//Tabela no postgres é minusculo, por default na criação 
//da base de testes o DBRider deixa maiusculo, correção abaixo
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(ProductTestLifecycleManager.class)
public class ProductControllerTest {

    private String token;

    @BeforeEach
    public void genereteToken() throws Exception {
        token = TokenUtils.generateTokenString("/JWTDonaFrostClaims.json", null);
    }

    private RequestSpecification givenNoToken() {
        return RestAssured.given()
        .contentType(ContentType.JSON);
    }

    private RequestSpecification given() {
        return RestAssured.given()
        .contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + token));
    }

    @Test
    @DataSet("products-cenario1.json")
    public void testFindProducts() {
        String result = given()
            .when().get("/dona-frost/v1/product")
            .then()
            .statusCode(200)
            .extract().asString();

            Approvals.verifyJson(result);
    }

    @Test
    @DataSet("products-cenario1.json")
    public void testFindUsersNoToken() {
        givenNoToken()
            .when().get("/dona-frost/v1/product")
            .then()
            .statusCode(401)
            .extract().asString();
    }

    @Test
    @DataSet(value = "products-cenario1.json")
    public void testCreateProduct() {
        ProductCreateDto dto = new ProductCreateDto();

        dto.setName("Lasanha Italiana");
        dto.setPrice(22.90);
        dto.setDescription("A descripton for the test");
        dto.setImageUri("A new uri");
        dto.setActive(true);

        String result = given()
            .body(dto)
            .when().post("/dona-frost/v1/product")
            .then()
            .statusCode(201)
            .extract().asString();

        //Somente aplicação rodando, não é possível criar transação no test    
        Approvals.verifyJson(result);
    }

    @Test
    @DataSet("products-cenario1.json")
    public void testChangeProduct() {
        ProductCreateDto dto = new ProductCreateDto();

        dto.setName("Lasanha Italiana");
        dto.setPrice(22.90);
        dto.setDescription("A descripton for the test");
        dto.setImageUri("A new uri");
        dto.setActive(true);

        Integer idProductToChange = 1;

        given()
            .with().pathParam("id", idProductToChange)
            .body(dto)
            .when().put("/dona-frost/v1/product/{id}")
            .then()
            .statusCode(Status.NO_CONTENT.getStatusCode())
            .extract().asString();

        Product findById = Product.findById(idProductToChange);
        
        Assert.assertEquals(dto.getName(), findById.getName());
        Assert.assertEquals(dto.getDescription(),findById.getDescription());
        Assert.assertEquals(dto.getPrice(), findById.getPrice());
    }

    @Test
    @DataSet("products-cenario1.json")
    public void testDeleteProduct() {

        Integer idProductToDelete = 1;

        given()
            .with().pathParam("id", idProductToDelete)
            .when().delete("/dona-frost/v1/product/{id}")
            .then()
            .statusCode(Status.NO_CONTENT.getStatusCode())
            .extract().asString();

            Product findById = Product.findById(idProductToDelete);
        
        Assert.assertEquals(findById, null);

    }

}