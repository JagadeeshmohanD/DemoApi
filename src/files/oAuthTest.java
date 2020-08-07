package files;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {
		
		String[] courseTitle= {"Selenium Webdriver java","cypress","protractor"};
		// TODO Auto-generated method stub
		//Selenium code to get code
		//System.setProperty("webdriver.gecko.driver","C:\\Users\\kusuma\\Downloads\\geckodriver-v0.26.0-win64\\geckodriver.exe");
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\kusuma\\Downloads\\chromedriver_win32\\chromedriver.exe");
//		 //FirefoxDriver driver = new FirefoxDriver();
//		 WebDriver driver = new ChromeDriver();
//		 driver.manage().window().maximize();
//		 driver.manage().deleteAllCookies();
//		 
//		 driver.manage().timeouts().pageLoadTimeout(40,TimeUnit.SECONDS);
//		 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		 driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
//		 driver.findElement(By.cssSelector("input[type='email']")).sendKeys("airwatch.sdkqa");
//		 driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//		 driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Airwatch@123");
//		 driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//		 Thread.sleep(4000);
		//String url= driver.getCurrentUrl();
		String url="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F2wEtC-QP5Zu-2jutt9szwpQ2cyqfiEmx88vO0rF5UselRBkljR_lFnoADrNADGv12_GnboqfqK8sEkrvIzQyWiY&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#";
		String partialcode=url.split("code=")[1];
		String code=partialcode.split("&scope")[0];
		System.out.println(code);
		
		String accessTokenResponse=given().
		urlEncodingEnabled(false).
		queryParams("code",code)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.queryParams("state", "verifyfjdss")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js=new JsonPath(accessTokenResponse);
		String accessToken=js.getString("access_token");
		System.out.println(accessToken);
		
//		String response=given().contentType("application/json").queryParam("access_token",accessToken)
//		.when().log().all()
//		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		//using pojo clases
		GetCourse gc=given().queryParam("access_token",accessToken).expect().defaultParser(Parser.JSON)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		System.out.println(gc.getLinkedin());
		System.out.println(gc.getInstructors());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apicourses=gc.getCourses().getApi();
		for(int i=0;i<apicourses.size();i++)
		{
		      if(apicourses.get(1).getCourseTitle().equalsIgnoreCase("soupUI webservices testing"))
		      {
		    	  System.out.println(apicourses.get(i).getPrice());
		      }
		}
		
		//get course names of web automation
		ArrayList<String> a = new ArrayList<String>();
		List<WebAutomation> w=gc.getCourses().getWeAutomation();
		for(int j=0;j<w.size();j++)
		{
			a.add(w.get(j).getCourseTitle());
		}
		
		List<String> expectedList=Arrays.asList(courseTitle);
		Assert.assertTrue(a.equals(expectedList));
        //System.out.println(response);
	}

}
