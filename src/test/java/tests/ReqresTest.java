package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.specification.ResponseSpecification;
import models.lombok.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static specs.ReqresSpec.*;


public class ReqresTest extends TestBase {

    @BeforeAll
    static void beforeAll() {
        com.codeborne.selenide.Configuration.browserSize = "1920x1080";
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

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
                            .patch("/api/users/2")


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


        given(userNotFoundRequestSpec)


                .when()
                .get("https://reqres.in/api/users/23")

                .then()
                .spec(userNotFoundResponseSpec);

    }

    @Test
    @DisplayName("Успешное получение данных из запроса")
    void successfulGetResourceTest() {

        GetResourcesReqresLombokModel response = step("Make request", () ->
        given(getResourcesRequestSpec)
                .when()
                .get("/api/unknown/2")

                .then()
                .spec(getResourcesResponseSpec)
                .extract().as(GetResourcesReqresLombokModel.class)
        );
        step("Check request", () -> {
            assertEquals("2", response.getData().getId());
            assertEquals("fuchsia rose", response.getData().getName());
            assertEquals("2001", response.getData().getYear());
            assertEquals("#C74375", response.getData().getColor());
            assertEquals("17-2031", response.getData().getPantone_value());
            assertEquals("https://reqres.in/#support-heading", response.getSupport().getUrl());


        });
    }

    @Test
    @DisplayName("Успешное удаление пользователя")
    void deleteUserTest() {

        step("Make request", () ->
            given(deleteUserRequestSpec)
                .when()
                .delete("/api/users?page=2")
                .then()
                    .spec(deleteUserResponseSpec)
        );
    }

    @Test
    @DisplayName("Неуспешная регистрация пользователя")
    void unsuccessfulRegisterTest() {
        unsuccessfulRegisterUserLombokModel registerData = new unsuccessfulRegisterUserLombokModel();
        registerData.setEmail("sydney@fife");

        unsuccessfulRegisterUserErrorResponseLombokModel response = step("Make request", () ->
        given(unsuccessfulRegisterUserRequestSpec)
                .when()
                    .post("/api/register")
                .then()
                    .spec(unsuccessfulRegisterUserResponseSpec)
                    .extract().as(unsuccessfulRegisterUserErrorResponseLombokModel.class)
        );
        step("Check request", () ->
            assertEquals("Missing email or username", response.getError())
        );

    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    void successfulRegisterTest() {
        successfulRegisterUserLombokModel registerData = new successfulRegisterUserLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");
        successfulRegisterUserResponseLombokModel response = step("Make request", () ->
        given(successfulRegisterUserRequestSpec)
                .body(registerData)

                .when()
                    .post("/api/register")
                .then()
                    .spec(successfulRegisterUserResponseSpec)
                    .extract().as(successfulRegisterUserResponseLombokModel.class)
        );
        step("Check request", () -> {
            assertEquals("4", response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });



    }
}
