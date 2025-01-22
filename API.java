package com;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class API {

    @Test
    public void Usuallyt() {
        
        Response response = RestAssured.get("https://api.coindesk.com/v1/bpi/currentprice.json");

       
        assertEquals(200, response.getStatusCode(), "Expected status code 200");

        String responseBody = response.getBody().asString();

   
        assertTrue(responseBody.contains("bpi"), "Response should contain 'bpi'");

     
        assertTrue(responseBody.contains("\"USD\""), "Response should contain 'USD' currency");
        assertTrue(responseBody.contains("\"GBP\""), "Response should contain 'GBP' currency");
        assertTrue(responseBody.contains("\"EUR\""), "Response should contain 'EUR' currency");

        
        String gbpDescription = responseBody.split("\"GBP\"")[1].split("\"description\":\"")[1].split("\"")[0];
        assertEquals("British Pound Sterling", gbpDescription, "GBP description should be 'British Pound Sterling'");
    }
}
