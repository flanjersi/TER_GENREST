package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.tests.utils.PayloadDataRequestREST;
import fr.amu.terGENREST.tests.utils.RequestsHelper;
import fr.amu.terGENREST.tests.utils.Utils;


public class UserRESTControllerTest {
	
	private static final String URL_ROOT_USER = "http://localhost:8090/terGENREST/api/users/";
	

	@Before
	public void setUp() throws Exception {	
	}
	
	@Test
	public void testCRUDUserRest() throws IOException {

		// add user
		HttpPut request = new HttpPut("http://localhost:8090/terGENREST/api/users/");

		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.dez")
				.add("firstName", "Jean")
				.add("lastName", "Marc")
				.add("password", "zeoi")
				.build();
		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		request.setHeader("Content-Type", "application/json");

		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		assertEquals(200, response.getStatusLine().getStatusCode());

		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertFalse(responseObject.containsKey("email"));
		assertFalse(responseObject.containsKey("password"));

		long id = responseObject.getJsonNumber("id").longValue();
		
		// update user
		HttpPost requestUpdate = new HttpPost("http://localhost:8090/terGENREST/api/users/" + id);

		 jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.dez")
				.add("firstName", "JeanUpdate")
				.add("lastName", "Marc")
				.add("password", "zeoi")
				.build();

		requestUpdate.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		requestUpdate.setHeader("Content-Type", "application/json");

		response = HttpClientBuilder.create().build().execute( requestUpdate );

		assertEquals(200, response.getStatusLine().getStatusCode());

		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertTrue(responseObject.containsKey("email"));
		assertTrue(responseObject.containsKey("password"));
		assertTrue(responseObject.containsKey("lastName"));
		assertTrue(responseObject.containsKey("firstName"));

		assertEquals("JeanUpdate", responseObject.getString("firstName"));
		
		
		// find user by id
		HttpGet requestGetData = new HttpGet("http://localhost:8090/terGENREST/api/users/" + id);
		
		 response = HttpClientBuilder.create().build().execute( requestGetData );

		assertEquals(200, response.getStatusLine().getStatusCode());

		 responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertTrue(responseObject.containsKey("email"));
		assertTrue(responseObject.containsKey("password"));
		assertTrue(responseObject.containsKey("lastName"));
		assertTrue(responseObject.containsKey("firstName"));

		assertEquals("jmj@gmail.dez", responseObject.getString("email"));
		
		
		//Delete user

		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8090/terGENREST/api/users/" + id);
		response = HttpClientBuilder.create().build().execute( requestDeleteData );
		assertEquals(200, response.getStatusLine().getStatusCode());
		
	}
	
	@Test
	public void testGetUserByEmailAndPassword() throws IOException {
		
		// add user
		
		User u = new User ("Jean","Marc","jmc84@gmail.fr","aerty105");
		
		HttpPut request = new HttpPut("http://localhost:8090/terGENREST/api/users/");

		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", u.getEmail())
				.add("firstName", u.getFirstName())
				.add("lastName", u.getLastName())
				.add("password", u.getPassword())
				.build();
		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		request.setHeader("Content-Type", "application/json");

		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		assertEquals(200, response.getStatusLine().getStatusCode());

		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertFalse(responseObject.containsKey("email"));
		assertFalse(responseObject.containsKey("password"));
		long id = responseObject.getJsonNumber("id").longValue();
		
		// find by Email and Password

		String email = u.getEmail();
		String password = u.getPassword();
		
		HttpGet requestGetData = new HttpGet("http://localhost:8090/terGENREST/api/users?email="+email+"&password="+password);
	
		 response = HttpClientBuilder.create().build().execute( requestGetData );

		 assertEquals(200, response.getStatusLine().getStatusCode());
		
		 JsonArray responseArray = Utils.stringToJsonArray(EntityUtils.toString(response.getEntity()));

		 responseObject = responseArray.getJsonObject(0);
		 
		assertTrue(responseObject.containsKey("id"));
		assertTrue(responseObject.containsKey("email"));
		assertTrue(responseObject.containsKey("password"));
		assertTrue(responseObject.containsKey("lastName"));
		assertTrue(responseObject.containsKey("firstName"));

		assertEquals(email, responseObject.getString("email"));
		
		//Delete user

		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8090/terGENREST/api/users/" + id);
		response = HttpClientBuilder.create().build().execute( requestDeleteData );
		assertEquals(200, response.getStatusLine().getStatusCode());
	}
	
	@Test
	public void getUserWithUnknowID() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpGetJsonObject(URL_ROOT_USER + "99999999");
		assertEquals(404, response.getResponseCode());
	}
	
	@Test
	public void deleteUserWithUnknowID() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpDELETE(URL_ROOT_USER + "99999999");
		assertEquals(404, response.getResponseCode());
	}
	
	@Test
	public void UpdateUserWithUnknowID() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.dez")
				.add("firstName", "JeanUpdate")
				.add("lastName", "Marc")
				.add("password", "zeoi")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPOST(URL_ROOT_USER + "999999999", jsonPayloadRequest);
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void testCreateConstraintUniqueEmail() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.dez")
				.add("firstName", "Jean")
				.add("lastName", "Marc")
				.add("password", "zeoi")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_USER, jsonPayloadRequest);

		long id = response.getPayload().getJsonNumber("id").longValue();

		response = RequestsHelper.httpPUT(URL_ROOT_USER, jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
		
		//Delete user

		response = RequestsHelper.httpDELETE(URL_ROOT_USER + "/" + id);
	}
	
	
	@Test
	public void testCreateUserWithNullEmail() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("firstName", "Jean")
				.add("lastName", "Marc")
				.add("password", "zeoi")
				.build();
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_USER, jsonPayloadRequest);
		assertEquals(400, response.getResponseCode());
	}
	
	
	@Test
	public void testCreateUserWithNullFirstName() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.dez")
				.add("lastName", "Marc")
				.add("password", "zeoi")
				.build();
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_USER, jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
	}
	
	@Test
	public void testCreateUserWithNullLastName() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.dez")
				.add("firstName", "Jean")
				.add("password", "zeoi")
				.build();
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_USER, jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
	}
	
	@Test
	public void testCreateUserWithNullPassword() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.dez")
				.add("firstName", "Jean")
				.add("lastName", "Marc")
				.build();
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_USER, jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
	}
	
	@Test
	public void findAllUsers() throws IOException {
		RequestsHelper.ResponseJsonArray response = RequestsHelper.httpGetJsonArray(URL_ROOT_USER);
		assertEquals(200, response.getResponseCode());
	}
	
	@Test
	public void findAllProjects() throws IOException {
		//ADD USER
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_USER, PayloadDataRequestREST.jsonPayloadRequestUser());
		long idUser = response.getPayload().getJsonNumber("id").longValue();
		assertEquals(200, response.getResponseCode());
		
		RequestsHelper.ResponseJsonArray responseArray = RequestsHelper.httpGetJsonArray(URL_ROOT_USER+idUser+"/projects/");
		long idProject = response.getPayload().getJsonNumber("id").longValue();
		assertEquals(200, responseArray.getResponseCode());
		
		//Delete user and Project

		response = RequestsHelper.httpDELETE(URL_ROOT_USER + "/" + idUser);
	//	response = RequestsHelper.httpDELETE(URL_ROOT_USER + "/" + idUser+"/projects/"+idProject);
	}
	
	@Test
	public void testCreateProject() throws IOException {
		// add user 
		
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.dez")
				.add("firstName", "Jean")
				.add("lastName", "Marc")
				.add("password", "zeoi")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_USER, jsonPayloadRequest);

		long id = response.getPayload().getJsonNumber("id").longValue();
		assertEquals(200, response.getResponseCode());
		
		// add Project
		
		JsonObject jsonPayloadRequest2 = Json.createObjectBuilder().add("projectName", "Project")
				.add("id", 4)
				.build();
				 
		response = RequestsHelper.httpPUT(URL_ROOT_USER + id + "/projects/", jsonPayloadRequest2);
		long idProject = response.getPayload().getJsonNumber("id").longValue();
		
		assertEquals(201, response.getResponseCode());

		//Delete user and Project

		response = RequestsHelper.httpDELETE(URL_ROOT_USER + "/" + id);
		response = RequestsHelper.httpDELETE(URL_ROOT_USER + "/" + id+"/projects/"+idProject);
	}
	
	
	@Test
	public void updateNothing() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.dez")
				.add("firstName", "Jean")
				.add("lastName", "Marc")
				.add("password", "zeoi")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_USER, jsonPayloadRequest);

		long id = response.getPayload().getJsonNumber("id").longValue();

		//Update User
		jsonPayloadRequest = Json.createObjectBuilder().build();

		response = RequestsHelper.httpPOST(URL_ROOT_USER + id, jsonPayloadRequest);
		assertEquals(200, response.getResponseCode());
		
		assertTrue(response.getPayload().containsKey("email"));
		assertTrue(response.getPayload().containsKey("password"));
		assertTrue(response.getPayload().containsKey("lastName"));
		assertTrue(response.getPayload().containsKey("firstName"));

		assertEquals("Jean", response.getPayload().getString("firstName"));
		
		id = response.getPayload().getJsonNumber("id").longValue();

		//Delete User

		response = RequestsHelper.httpDELETE(URL_ROOT_USER + "/" + id);
	}

}