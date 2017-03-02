package RESTfulTests;

import Utilities.JSONUtilities;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by RXC8414 on 2/28/2017.
 */
public class Tests extends JSONUtilities{
    String api = "http://api.weather.com/v2/astro?geocode=33.99,-85&date=20150102&days=3&format=json&apiKey=3d498bd0777076fb2aa967aa67114c7e";


    @DataProvider(name = "geocodes")
    public static Object[][] geocode(){
        return new Object[][]{
                {"Test 1 (33,-85):","33,-85"},
                {"Test 2 (32,-80):","32,-80"},
                {"Test 3 (31,-75):","31,-75"},
                {"Test 4 (30,-70):","30,-70"},
                {"Test 5 (29,-65):","29,-65"}
        };
    }


    //Print the Response Body for the API GET request
    @Test
    public void printResponseAPI(){
        parseJSONIntoHashMap(get(api).then().extract().response().asString());
        //get(api).then().extract().response().prettyPrint();
        //System.out.println(strAPI);
    }

    //Use internal Rest Assured assertions to check status code = 200
    @Test
    public void verifyStatusCode(){
        get(api).then().statusCode(200);
    }

    //Use TestNG assertions to validate status code via extraction
    @Test
    public void verifyStatusCodeAssertion(){
        Assert.assertEquals(get(api).then().extract().statusCode(),200,"Error Message");
    }

    //Use TestNG assertion to validate the status code object has a 200 value
    @Test
    public void verifyStatusCodeFromBody(){
        Assert.assertEquals(get(api).then().extract().path("metadata.status_code"),200,"Error Message");
    }

    //Use TestNG to parameterize validation of status code for multiple geocodes
    @Test(dataProvider="geocodes")
    public void verifyDPStatusCode(String testName, String geocode){
        try{
            api = "http://api.weather.com/v2/astro?geocode="+geocode+"&date=20150102&days=3&format=json&apiKey=3d498bd0777076fb2aa967aa67114c7e";
            get(api).then().statusCode(201);
            System.out.println(testName+" Passed");
        }
        catch(AssertionError e){
            //Assert.fail(testName+" Failed");
            throw new AssertionError(testName+" Failed");
        }

    }

    //Get invisible data from API response using the Response class
    @Test
    public void getResponseValues(){
        Response response = get(api);

        System.out.println("\n****** HEADERS *******");
        System.out.println(response.getHeaders());
        System.out.println("\n****** CONTENT *******");
        System.out.println(response.getContentType());
        System.out.println("\n****** COOKIES *******");
        System.out.println(response.getCookies());
        System.out.println("\n****** SESSION *******");
        System.out.println(response.getSessionId());
        System.out.println("\n****** STATUS CODE *******");
        System.out.println(response.getStatusCode());
        System.out.println("\n****** EXECUTION TIME *******");
        System.out.println(response.getTimeIn(TimeUnit.MILLISECONDS)+" ms");
    }

    // Validate REST Assured hard assertions
    @Test
    public void verifyAssertionOldFormat(){
        given()
        .when()
            .get(api)
        .then()
            .statusCode(200)
            .body("metadata.status_code",equalTo(200))
            .header("Transaction-Id","7555634a-7329-4b18-9563-4a7329db1860");
    }

    //Validate REST Assured soft assertions
    @Test
    public void verifyAssertionNewFormat(){
        given()
        .expect()
            .statusCode(200)
            .body("metadata.status_code",equalTo(200))
            .header("Transaction-Id","7555634a-7329-4b18-9563-4a7329db1860")
        .when()
            .get(api);
    }
}