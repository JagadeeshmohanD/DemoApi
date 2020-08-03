package files;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      RestAssured.baseURI="http://localhost:8080/";
      //login scenario
      
    SessionFilter session=new SessionFilter();
    String response=given().header("Content-Type","application/json").body("{ \"username\": \"admin\", \"password\": \"admin\" }")
     .log().all().filter(session).when().post("rest/auth/1/session").then().log().all().extract().response().asString();
	
    
    String expectedMessage="Hi its automation Comment";
    //Add Comments
         String addCommentResponse=given().pathParam("bugid","10007").log().all().header("Content-Type","application/json").body("{\r\n" + 
      		"  \"body\": \""+expectedMessage+"\",\r\n" + 
      		"   \"visibility\": {\r\n" + 
      		"     \"type\": \"role\",\r\n" + 
      		"     \"value\": \"Administrators\"\r\n" + 
      		"  }\r\n" + 
      		"}").filter(session).when().post("rest/api/2/issue/{bugid}/comment").then().log().all().assertThat().statusCode(201)
         .extract().response().asString();
         JsonPath js = new JsonPath(addCommentResponse);
        String CommentID= js.getString("id");
         
      //Add Attachments
//         given().header("X-Atlassian-Token","no-check").filter(session).pathParam("bugid","10007")
//         .header("Content-Type","multiPart/form-data")
//         .multiPart("file",new File("C:\\Software\\Workspace\\JavaPractice\\eclipse-workspace\\DemoApi\\jira.txt")).when().post("rest/api/3/issue/{bugid}/attachments").then().log().all().assertThat().statusCode(200);
         
      //Get Issue
         String issueDetails=given().filter(session).pathParam("bugid","10007").queryParam("fields", "comment").log().all().when().get("rest/api/2/issue/{bugid}")
         .then().log().all().extract().asString();
         System.out.println(issueDetails);
         
         //for extract details 
         JsonPath js1 = new JsonPath(issueDetails);
         int commentsCount=js1.get("fields.comment.comments.size()");
         for(int i=0;i<commentsCount;i++)
         {
        	 String CommentIdIssue=js1.get("fields.comment.comments["+i+"].id").toString();
        	 if(CommentIdIssue.equalsIgnoreCase(CommentID))
        	 {
        		 String message=js1.get("fields.comment.comments["+i+"].body").toString();
        		 System.out.println(message);
        		 Assert.assertEquals(message, expectedMessage);
        	 }
         }
         
	}

}
