package tests;

import io.qameta.allure.Allure;
import models.lombok.LoginResponseLombokModel;
import models.lombok.ReqresBodyLombokModel;
import models.lombok.ReqresResponseLombokModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static jdk.dynalink.linker.support.Guards.isNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static specs.LoginSpec.loginResponseSpec;
import static specs.ReqresSpec.reqresRequestSpec;
import static specs.ReqresSpec.reqresResponseSpec;


public class ReqresTest {
    @Test
    @DisplayName("Успешное обновление данных")
    void successfulUpdateTest() {
        ReqresBodyLombokModel updateData = new ReqresBodyLombokModel();
        updateData.setName("morpheus");
        updateData.setJob("zion resident");

        ReqresResponseLombokModel response = step("Make request", () ->
                given(reqresRequestSpec)
                        .body(updateData)


                        .when()
                        .patch("https://reqres.in/api/users/2")


                        .then()
                        .spec(reqresResponseSpec)
                        .extract().as(ReqresResponseLombokModel.class)
        );
        step("Check request", () -> {
            assertEquals("morpheus", response.getName());
            assertEquals("zion resident", response.getJob());
            assertNotEquals(0, response.getUpdatedAt());
        });
    }

    @Test
    @DisplayName("Ошибка 404")
    void userNotFoundTest() {


        given()

                .contentType(JSON)
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/23")

                .then()
                .log().status()
                .log().body()
                .statusCode(404);

    }

    @Test
    void successfulGetResourceTest() {


        given()
                .contentType(JSON)
                .log().method()
                .log().uri()

                .when()
                .get("https://reqres.in/api/unknown/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.name", is("fuchsia rose"))
                .body("data.year", is(2001))
                .body("data.color", is("#C74375"))
                .body("data.pantone_value", is("17-2031"))
                .body("support.url", is("https://reqres.in/#support-heading"));
    }

    @Test
    @DisplayName("Успешное удаление пользователя")
    void deleteUserTest() {


        given()

                .contentType(JSON)
                .log().uri()
                .when()
                .delete("https://reqres.in/api/users?page=2")

                .then()
                .log().status()
                .log().body()
                .statusCode(204);

    }

    @Test
    @DisplayName("Неуспешная регистрация пользователя")
    void unsuccessfulRegisterTest() {
        String registerData = "{\"email\": \"sydney@fife\"}";

        given()
                .body(registerData)
                .contentType(JSON)
                .log().uri()
                .log().method()

                .when()
                .post("https://reqres.in/api/register")


                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));

    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    void successfulRegisterTest() {
        String registerData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .body(registerData)
                .contentType(JSON)
                .log().uri()
                .log().method()

                .when()
                .post("https://reqres.in/api/register")


                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));

    }
}
