package files;

import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;

public class oAuthAceesToken {

	public static void main(String[] args) throws InterruptedException {
		
	    String code = "4%2F3gEJIq8VmzrftgfSnzNLRuHJFIP0JxBiH_1FLde_lqt-jlBH07l21Y0SGBLlSDalnYV1j2KESKI_Y6ndXvF49_4";
	    
	// code keeps on changing and can be generated overtime using 
	 //https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
	 
			
			
		String accessTokenResponse = given()
				.urlEncodingEnabled(false)
				.queryParams("code", code)
			.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
			.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
			.queryParams("grant_type","authorization_code")
			.when().log().all()
			.post("https://www.googleapis.com/oauth2/v4/token")
			.asString();
		
		System.out.println(accessTokenResponse);
		JsonPath jp = new JsonPath(accessTokenResponse);
		String access_token = jp.getString("access_token");
		
		System.out.println(access_token);
			
			
			String response = given().queryParam("access_token", access_token).
			when().get("https://rahulshettyacademy.com/getCourse.php").asString();
			
			System.out.println(response);
			
			
			
		}
	
}