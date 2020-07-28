package basicAPI;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js2 = new JsonPath(Payload.CoursePrice());
		//print no of courses returned by API
		int count=js2.getInt("courses.size()");
		System.out.println(count);
		//print purchase Amount
		int totalpurchase=js2.getInt("dashboard.purchaseAmount");
		System.out.println(totalpurchase);
		//print Title of the first course
		String titleFirstCourse=js2.get("courses[0].title");
		System.out.println(titleFirstCourse);
		//print all courses title and their respective prices
		for(int i =0;i<count;i++)
		{
			String CourseTitles=js2.get("courses["+i+"].title");
			System.out.println(js2.get("courses["+i+"].price").toString());
			System.out.println(CourseTitles);
		
		}
        
		//print No of copies sold by RPA
		System.out.println("Print Number of copies sold by RPA course");
		for(int j =0;j<count;j++)
		{
			String CourseTitles=js2.get("courses["+j+"].title");
			if(CourseTitles.equalsIgnoreCase("RPA"))
			{
				//copies sold
				int copies=js2.get("courses["+j+"].copies");
				System.out.println(copies);
				break; //optimization
			}
			
		
		}

	}

}
