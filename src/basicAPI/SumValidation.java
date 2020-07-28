package basicAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses()
	{
		int sum =0;
		JsonPath js3 = new JsonPath(Payload.CoursePrice());
		int count=js3.getInt("courses.size()");
		for(int i=0;i<count;i++)
		{
			int price=js3.get("courses["+i+"].price");
			int copies=js3.get(("courses["+i+"].copies"));
			int amount = price*copies;
			System.out.println(amount);
			sum=sum+amount;
		}
		System.out.println(sum);
		int purchaseAmount=js3.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmount);
	}
}
