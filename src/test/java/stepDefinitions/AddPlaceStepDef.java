package stepDefinitions;

import org.testng.AssertJUnit;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPlaceStepDef extends Utils {

	RequestSpecification res;
	ResponseSpecBuilder resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	JsonPath js;
	static String placeID;

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		res = given().spec(requestSpecification()).body(data.addPlaceDataBuild(name, language, address));

	}

	@When("user call {string} with {string} request")
	public void user_call_with_request(String apiName, String requestType) {
		// if(requestType.equalsIgnoreCase("POST"))
		String resourceAPI = APIResources.valueOf(apiName).getResource();
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON);

		if (requestType.equalsIgnoreCase("POST")) {
			response = res.when().post(resourceAPI).then().extract().response();

		}
		if (requestType.equalsIgnoreCase("GET")) {
			response = res.when().get(resourceAPI).then().extract().response();
		}
	}

	@Then("The API call gets success with {int} status code")
	public void the_api_call_gets_success_with_status_code(int statusCode) {

		assertEquals(statusCode, response.getStatusCode());
	}

	@Then("The {string} in response code is {string}")
	public void the_in_response_code_is(String responseKey, String responseValue) {

		System.out.println(getJsonPath(response, responseKey));
		assertEquals(getJsonPath(response, responseKey), responseValue);
	}

	@Then("Verify the placeId created maps to {string} using {string}")
	public void verify_the_placeId_created_maps_to(String name, String apiName) throws IOException {

		placeID = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", placeID);
		user_call_with_request(apiName, "GET");
		String actualValue = getJsonPath(response, "name");
		assertEquals(actualValue, name);
	}

	@Given("Delete Place API")
	public void delete_place_api() throws IOException {
		res = given().spec(requestSpecification().body(data.deletePlaceDataBuild(placeID)));
	}
}