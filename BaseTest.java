package Utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    public static RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:3000"; // Your mock server URL
        requestSpec = RestAssured.given()
                                 .header("Content-Type", "application/json")
                                 .log().all();
    }
}
