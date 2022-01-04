package com.github.sandrolaxx.dfmicroservices;

import javax.ws.rs.core.Response.Status;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.sandrolaxx.dfmicroservices.dto.CreateAddressDto;
import com.github.sandrolaxx.dfmicroservices.dto.CreateUserDto;
import com.github.sandrolaxx.dfmicroservices.entities.User;
import com.github.sandrolaxx.dfmicroservices.util.TokenUtils;

import org.approvaltests.Approvals;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(UserTestLifecycleManager.class)
public class UserControllerTest {

    private String token;

    @BeforeEach
    public void genereteToken() throws Exception {
        token = TokenUtils.generateTokenString("/JWTDonaFrostClaims.json", null);
    }
    
    private RequestSpecification given() {
        return RestAssured.given()
        .contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + token));
    }

    private RequestSpecification givenNoToken() {
        return RestAssured.given()
        .contentType(ContentType.JSON);
    }

    @Test
    @DataSet("user-cenario1.json")
    public void testFindUsers() {
        String result = given()
            .when().get("/dona-frost/v1/user")
            .then()
            .statusCode(200)
            .extract().asString();

            Approvals.verifyJson(result);
    }

    @Test
    @DataSet("user-cenario1.json")
    public void testFindUsersNoToken() {
        givenNoToken()
            .when().get("/dona-frost/v1/user")
            .then()
            .statusCode(401)
            .extract().asString();
    }

    @Test
    @DataSet(value = "user-cenario1.json")
    public void testCreateUser() {
        CreateUserDto dto = new CreateUserDto();
        CreateAddressDto addressDto = new CreateAddressDto();

        addressDto.setState("PR");
        addressDto.setCity("Londrina");
        addressDto.setDistrict("Bairro jardim XX");
        addressDto.setStreet("Rua elias abraão");
        addressDto.setNumber(91);
        addressDto.setNumberAp(63);
        addressDto.setMain(true);
        addressDto.setLatitude("4343443");
        addressDto.setLongitude("434343");

        dto.setAddress(addressDto);
        dto.setName("Roberto");
        dto.setDocument("10564574902");
        dto.setEmail("teste@hotmail.com");
        dto.setPassword("12222");
        dto.setPhone("12564588885");;
        dto.setActive(true);


        String result = given()
            .body(dto)
            .when().post("/dona-frost/v1/user")
            .then()
            .statusCode(201)
            .extract().asString();

        //Somente aplicação rodando, não é possível criar transação no test    
        Approvals.verifyJson(result);
    }


    @Test
    @DataSet("user-cenario1.json")
    public void testChangeUser() {
        CreateUserDto dto = new CreateUserDto();

        dto.setName("Roberto");
        dto.setDocument("10564574902");
        dto.setEmail("teste@hotmail.com");
        dto.setPhone("12564588885");

        Integer idUserToChange = 1;

        given()
            .with().pathParam("id", idUserToChange)
            .body(dto)
            .when().put("/dona-frost/v1/user/{id}")
            .then()
            .statusCode(Status.NO_CONTENT.getStatusCode())
            .extract().asString();

        User findById = User.findById(idUserToChange);
        
        Assert.assertEquals(dto.getName(), findById.getName());
        Assert.assertEquals(dto.getDocument(), findById.getDocument());
        Assert.assertEquals(dto.getEmail(), findById.getEmail());
        Assert.assertEquals(dto.getPhone(), findById.getPhone());

    }

    @Test
    @DataSet("user-cenario1.json")
    public void testDeleteUser() {

        Integer idUserToDelete = 1;

        given()
            .with().pathParam("id", idUserToDelete)
            .when().delete("/dona-frost/v1/user/{id}")
            .then()
            .statusCode(Status.NO_CONTENT.getStatusCode())
            .extract().asString();

        User findById = User.findById(idUserToDelete);
        
        Assert.assertEquals(findById, null);

    }

}