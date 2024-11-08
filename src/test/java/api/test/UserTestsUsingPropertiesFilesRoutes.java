package api.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.javafaker.Faker;

import api.endpoints.UserEndPointsUsingThePropertiesFile;
import api.payload.User;
import io.restassured.response.Response;

public class UserTestsUsingPropertiesFilesRoutes {

	Faker faker;
	User userPayload ;
	public Logger logger;
	
	@BeforeClass
	public void setup() {
			faker = new Faker();
			userPayload = new User();
			
			userPayload.setId(faker.idNumber().hashCode());
			userPayload.setEmail(faker.internet().emailAddress());
			userPayload.setFirstName(faker.name().firstName());
			userPayload.setLastName(faker.name().lastName());
			userPayload.setPassword(faker.internet().password(5,10));
			userPayload.setPhone(faker.phoneNumber().cellPhone());
			userPayload.setUsername(faker.name().username());
			
			logger = LogManager.getLogger(this.getClass());
			
	}
	
	
	@Test(priority = 1)
	public void testPostUser() {
		
		logger.info("=============== Creating the User =====================");
		Response response = UserEndPointsUsingThePropertiesFile.createUser(this.userPayload);
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		logger.info("=============== User is Created =====================");
		
	}
	
	@Test(priority = 2)
	public void testGetUserByName() {
		logger.info("=============== Reading user Info =====================");
		Response response = UserEndPointsUsingThePropertiesFile.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		logger.info("=============== user info reading completed =====================");
		
	}
	
	@Test(priority = 3)
	public void testUpdateUser() {
		
		userPayload.setFirstName("Vimal");
		userPayload.setLastName("Verma");
		userPayload.setPhone("123456789");
		
		logger.info("=============== updating the User =====================");
		
		Response response = UserEndPointsUsingThePropertiesFile.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		 response = UserEndPointsUsingThePropertiesFile.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		logger.info("=============== User info updated =====================");
		
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName() {
		
		logger.info("=============== Deleting the user =====================");
		
		Response response = UserEndPointsUsingThePropertiesFile.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	
		logger.info("=============== User Deleted =====================");
		
	}
}
