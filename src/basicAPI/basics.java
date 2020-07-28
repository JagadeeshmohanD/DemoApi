package basicAPI;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReUsableMethods;
public class basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//validate if Add place API is working as expected
		//given-all input details
		//when-submit the API-resources http method
		//Then-validate the response
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.Addplace()).when().post("maps/api/place/add/json")
		        .then().assertThat().statusCode(200).body("scope",equalTo( "APP"))
		        .header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		//Add place ->update place with New Address->get place to validate if New address is present in response
		JsonPath js = new JsonPath(response);//for parsing json
		String placeID=js.get("place_id");
		System.out.println(placeID);
		
		//update Place
		String newAddress = "BangaloreA";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeID+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg", equalTo( "Address successfully updated"));
		
	//Get place
		
		String getPlaceResponse=  given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id",placeID)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		//JsonPath js1 = new JsonPath(getPlaceResponse);
		JsonPath js1=ReUsableMethods.rawToJson(getPlaceResponse);
        String actualAddress=js1.getString("address");
		System.out.println(actualAddress);
		//cucumber junit Testng for Assertions
		Assert.assertEquals(actualAddress, newAddress);
	}

}
