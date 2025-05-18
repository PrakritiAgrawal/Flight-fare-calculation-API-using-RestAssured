package Utils;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class FlightFareTest extends BaseTest {

    @Test(priority = 1)
    public void createFlightFare() {
        String payload = "{\n" +
                "  \"source\": \"Delhi\",\n" +
                "  \"destination\": \"Mumbai\",\n" +
                "  \"travelDate\": \"2025-05-10\",\n" +
                "  \"baseFare\": 5500,\n" +
                "  \"taxes\": 550,\n" +
                "  \"passengerCount\": 2,\n" +
                "  \"totalFare\": 12100\n" +
                "}";

        Response response = requestSpec
                .body(payload)
                .when()
                .post("/flights");

        Assert.assertEquals(response.statusCode(), 201, "Status Code Validation Failed!");
        Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
        Assert.assertEquals(response.jsonPath().getString("source"), "Delhi");
    }

    @Test(priority = 2)
    public void updateFlightFare() {
        String updatedPayload = "{\n" +
                "  \"source\": \"Delhi\",\n" +
                "  \"destination\": \"Mumbai\",\n" +
                "  \"travelDate\": \"2025-05-10\",\n" +
                "  \"baseFare\": 5500,\n" +
                "  \"taxes\": 550,\n" +
                "  \"passengerCount\": 2,\n" +
                "  \"totalFare\": 12100\n" +
                "}";

        Response response = requestSpec
                .body(updatedPayload)
                .when()
                .put("/flights/1");

        Assert.assertEquals(response.statusCode(), 200, "PUT Status Code Validation Failed!");
    }
    @Test(priority = 3)
    public void validateTotalFareCalculation() {
        Response response = requestSpec
                .when()
                .get("/flights/1"); // Fetching fare by ID

        int baseFare = response.jsonPath().getInt("baseFare");
        int taxes = response.jsonPath().getInt("taxes");
        int passengerCount = response.jsonPath().getInt("passengerCount");
        int totalFare = response.jsonPath().getInt("totalFare");

        int expectedTotalFare = (baseFare + taxes) * passengerCount;

        Assert.assertEquals(totalFare, expectedTotalFare, "Total Fare Calculation Validation Failed!");
    }

}
