package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class DynamicJson {
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response=given().header("Content-Type","application/json").
		body(Payload.Addbook(isbn,aisle)).
		when()
		.post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js=ReUsableMethods.rawToJson(response);
		String id=js.get("ID");
		System.out.println(id);
		
		//deleteBook
//		String deletestatus=given().header("Content-Type","application/json").body(Payload.DeleteBook(id)).
//		when()
//		.delete("/Library/DeleteBook.php")
//		.then().log().all().assertThat().statusCode(200).extract().response().asString();
//		JsonPath js1=ReUsableMethods.rawToJson(deletestatus);
//		String msg=js1.get("msg");
//		System.out.println(msg);
		}
@DataProvider(name="BooksData")
public Object[][] getData()
{
	//Array-collection of elements
	//multidimensional array collection of arrays
	return new Object[][]{{"asdfca","123edca"},{"qwerta","456zxca"},{"zxcvba","7896tya"}};
}

}
