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

import fr.amu.terGENREST.tests.utils.Utils;


public class UserRESTControllerTest {
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void testCRUDUserRest() throws IOException {

		// add user
		HttpPut request = new HttpPut("http://localhost:8080/terGENREST/api/users/");

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
		HttpPost requestUpdate = new HttpPost("http://localhost:8080/terGENREST/api/users/" + id);

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
		HttpGet requestGetData = new HttpGet("http://localhost:8080/terGENREST/api/users/" + id);
		
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

		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8080/terGENREST/api/users/" + id);
		response = HttpClientBuilder.create().build().execute( requestDeleteData );
		assertEquals(200, response.getStatusLine().getStatusCode());
		
	}
	@Ignore // to test findAllUser you have to empty the database
	@Test
	public void testFindAllUser() throws IOException{
		//	save user 1 
		HttpPut request = new HttpPut("http://localhost:8080/terGENREST/api/users/");

		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", "qddq@gmail.dez")
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
		assertFalse(responseObject.containsKey("firstName"));
		assertFalse(responseObject.containsKey("lastName"));

		long id1 = responseObject.getJsonNumber("id").longValue();
		
		// save user 2
		jsonPayloadRequest = Json.createObjectBuilder().add("email", "aede@gmail.dez")
				.add("firstName", "eifpj")
				.add("lastName", "fpezijf")
				.add("password", "pezifj")
				.build();
		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		request.setHeader("Content-Type", "application/json");

		 response = HttpClientBuilder.create().build().execute( request );
		assertEquals(200, response.getStatusLine().getStatusCode());

		 responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		
		long id2 = responseObject.getJsonNumber("id").longValue();
		
		// assert 
		
		HttpGet requestGetData = new HttpGet("http://localhost:8080/terGENREST/api/users/" );
		
		 response =  HttpClientBuilder.create().build().execute( requestGetData );

		assertEquals(200, response.getStatusLine().getStatusCode());

		JsonArray responseObjects =  Utils.stringToJsonArray(EntityUtils.toString(response.getEntity()));
		
		assertTrue( responseObjects.size() == 2 );
		
		//Delete users

		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8080/terGENREST/api/users/" + id1);
		response = HttpClientBuilder.create().build().execute( requestDeleteData );
		assertEquals(200, response.getStatusLine().getStatusCode());
		
		requestDeleteData = new HttpDelete("http://localhost:8080/terGENREST/api/users/" + id2);
		response = HttpClientBuilder.create().build().execute( requestDeleteData );
		assertEquals(200, response.getStatusLine().getStatusCode());
		
	}
	
	@Test
	public void testGetUserByEmailAndPassword() throws IOException {
		
		// add user
		
		HttpPut request = new HttpPut("http://localhost:8080/terGENREST/api/users/");

		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", "oaiehf@gmail.dez")
				.add("firstName", "Jean")
				.add("lastName", "Marc")
				.add("password", "efoke")
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

		String email = "oaiehf@gmail.dez";
		String password = "efoke";
		
		HttpGet requestGetData = new HttpGet("http://localhost:8080/terGENREST/api/users/" +email+"/"+ password);
		 response = HttpClientBuilder.create().build().execute( requestGetData );

		 assertEquals(200, response.getStatusLine().getStatusCode());
		
		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertTrue(responseObject.containsKey("email"));
		assertTrue(responseObject.containsKey("password"));
		assertTrue(responseObject.containsKey("lastName"));
		assertTrue(responseObject.containsKey("firstName"));

		assertEquals("oaiehf@gmail.dez", responseObject.getString("email"));
		
		//Delete user

		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8080/terGENREST/api/users/" + id);
		response = HttpClientBuilder.create().build().execute( requestDeleteData );
		assertEquals(200, response.getStatusLine().getStatusCode());
	}
}

