package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPointsUsingThePropertiesFile {

	// to the urls form properties file
	public static ResourceBundle getUrl(){
		return ResourceBundle.getBundle("routes");
	}
	
	
public static Response createUser(User payload) {
			
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(getUrl().getString("user.create.url"));
		
		return response;
	}
	
	public static Response readUser(String username) {
		
		Response response = given()
					.pathParam("username", username)
		.when()
			.get(getUrl().getString("user.get.url"));
		
		return response;
	}
	
	
	public static Response updateUser(String username, User payload) {
		
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
			.pathParam("username", username)
		.when()
			.put(getUrl().getString("user.update.url"));
		
		return response;
	}
	
	
	public static Response deleteUser(String username) {
		
		Response response = given()
					.pathParam("username", username)
		.when()
			.delete(getUrl().getString("user.delete.url"));
		
		return response;
	}
	
	
}
