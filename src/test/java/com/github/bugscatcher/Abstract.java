package com.github.bugscatcher;

import com.github.bugscatcher.dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public abstract class Abstract {
    protected static String usernameToSearch;
    protected static int userIdToSearch;

    @BeforeClass
    public static void setUp() throws IOException {
        Properties properties = loadProperties();
        usernameToSearch = properties.getProperty("username");
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
        UserDTO userToSearch = searchUser();
        Assert.assertNotNull("user shouldn't be null", userToSearch);
        userIdToSearch = userToSearch.getId();
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

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = Abstract.class.getResourceAsStream("/test.properties");
        properties.load(inputStream);
        return properties;
    }

    private static UserDTO searchUser() {
        UserDTO[] users = getResource(EndPoints.USERS, UserDTO[].class);
        return Arrays.stream(users)
                .filter(user -> usernameToSearch.equals(user.getUsername()))
                .findAny()
                .orElse(null);
    }
}
