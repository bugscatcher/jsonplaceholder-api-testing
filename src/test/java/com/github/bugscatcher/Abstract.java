package com.github.bugscatcher;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

import static com.github.bugscatcher.TestUtil.loadProperties;
import static io.restassured.RestAssured.given;

public abstract class Abstract {
    static final Logger LOG = LoggerFactory.getLogger(Abstract.class);
    static Properties properties;

    @BeforeClass
    public static void setUp() throws IOException {
        properties = loadProperties();
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.ANY)
                .setBaseUri(EndPoints.BASE_URI)
                .build();
        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;
    }

    static <T> T[] getResource(String path, Class<T[]> responseClass) {
        Response response = given()
                .when()
                .get(path)
                .then()
                .extract()
                .response();
        return response.as(responseClass);
    }
}
