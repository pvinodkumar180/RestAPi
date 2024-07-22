package api.test;

import org.testng.Assert;
import org.testng.annotations.*;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoint;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userpayload;
	
	@BeforeClass()
	public void setup() {
		faker =  new Faker();
		userpayload =  new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
	}
	
	@Test(priority=1)
	public void Testpostuser() {
		Response response = UserEndPoint.create_user(userpayload);
		Assert.assertEquals(response.getStatusCode(),200);
		response.then().log().all();
	}
	
	@Test(priority=2)
	public void Testgetuser() {
		Response response = UserEndPoint.read_user(this.userpayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);
		response.then().log().all();
	}
	
	@Test(priority=3,dependsOnMethods= {"Testgetuser"})
	public void Testupdateuser() {
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		Response response = UserEndPoint.update_user(userpayload, this.userpayload.getUsername());
		response.then().log().body();
		//Assert.assertEquals(response.getStatusCode(),200);
		
		Response response1 = UserEndPoint.read_user(this.userpayload.getUsername());
		response1.then().log().all();
	}
	
	@Test(priority=4,dependsOnMethods= {"Testupdateuser"})
	public void Testdeleteuser() {
		Response response = UserEndPoint.delete_user(this.userpayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);
	}
	
	
	
	

}
