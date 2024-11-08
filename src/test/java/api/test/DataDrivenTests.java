package api.test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTests {
	
	
	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userID, String username, String fName, String lName, String userEmail, String pwd, String ph) {
		
		User userPayload = new User();
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setFirstName(fName);
		userPayload.setLastName(lName);
		userPayload.setEmail(userEmail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		userPayload.setUsername(username);
		

			Response response = UserEndPoints.createUser(userPayload);
			response.then().log().all();
			
			AssertJUnit.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteUser(String username) {
		
		
		Response getUserResponse = UserEndPoints.readUser(username);
		if (getUserResponse.getStatusCode() == 200) {
		    Response response = UserEndPoints.deleteUser(username);
		    response.then().log().all();
		    AssertJUnit.assertEquals(response.getStatusCode(), 200);
		} else {
		    AssertJUnit.fail("User does not exist for deletion: " + username);
		}
		
		
//			Response response = UserEndPoints.deleteUser(username);
//			response.then().log().all();
//			
//			Assert.assertEquals(response.getStatusCode(), 200);
			
		
	}
}
