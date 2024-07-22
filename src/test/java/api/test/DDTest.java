package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.payload.User;
import api.utilites.DataProviders;
import io.restassured.response.Response;
import api.endpoints.UserEndPoint;


public class DDTest {
	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class )
	public void testPostuser(String userID, String userName,String fname,String lname,String useremail,String pwd,String ph)
	{
		User userPayload=new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		Response response=UserEndPoint.create_user(userPayload);;
		Assert.assertEquals(response.getStatusCode(),200);
		Response response1 = UserEndPoint.read_user(userName);
		response1.then().log().body();
			
	}
	
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName)
	{
			Response response=UserEndPoint.delete_user(userName);
			Assert.assertEquals(response.getStatusCode(),200);	
	
	}
	
	
	

}
