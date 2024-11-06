package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class ReqresSpec {
    public static RequestSpecification reqresRequestSpec = with()
            .contentType(JSON)
            .log().uri()
            .log().method()
            .filter(withCustomTemplates());

    public static ResponseSpecification reqresResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification userNotFoundResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(STATUS)
            .log(BODY)
            .build();

    public static RequestSpecification userNotFoundRequestSpec = with()
            .contentType(JSON)
            .log().uri()
            .log().method()
            .filter(withCustomTemplates());

    public static ResponseSpecification getResourcesResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static RequestSpecification getResourcesRequestSpec = with()
            .contentType(JSON)
            .log().uri()
            .log().method()
            .filter(withCustomTemplates());

    public static RequestSpecification deleteUserRequestSpec = with()
            .contentType(JSON)
            .log().uri()
            .log().method()
            .filter(withCustomTemplates());

    public static ResponseSpecification deleteUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(STATUS)
            .log(BODY)
            .build();

    public static RequestSpecification unsuccessfulRegisterUserRequestSpec = with()
            .contentType(JSON)
            .log().uri()
            .log().method()
            .filter(withCustomTemplates());

    public static ResponseSpecification unsuccessfulRegisterUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(STATUS)
            .log(BODY)
            .build();

    public static RequestSpecification successfulRegisterUserRequestSpec = with()
            .contentType(JSON)
            .log().uri()
            .log().method()
            .filter(withCustomTemplates());

    public static ResponseSpecification successfulRegisterUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

}