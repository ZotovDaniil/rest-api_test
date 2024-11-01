import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresTest {
    @Test
    @DisplayName("Успешное обновление данных")
    void successfulUpdateTest() {
        String updateData = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";

        given()
                .body(updateData)
                .contentType(JSON)
                .log().uri()
                .log().method()

                .when()
                .patch("https://reqres.in/api/users/2")


                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", is(notNullValue()));
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
}
