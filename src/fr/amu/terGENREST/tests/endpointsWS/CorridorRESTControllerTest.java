package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
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
import org.junit.Test;

import fr.amu.terGENREST.tests.utils.Utils;

public class CorridorRESTControllerTest {

	@Before
	public void setUp() throws Exception {

	}
	
	@Test
	public void testCRUDUserRest() throws IOException {

//		
//		// add user
//		HttpPut request = new HttpPut("http://localhost:8090/terGENREST/api/users/");
//
//		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.fr")
//				.add("firstName", "Jean")
//				.add("lastName", "Marc")
//				.add("password", "zeoi")
//				.build();
//		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
//		request.setHeader("Content-Type", "application/json");
//
//		HttpResponse response = HttpClientBuilder.create().build().execute( request );
//		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));
//		long idUser = responseObject.getJsonNumber("id").longValue();  
//
//		// add project
//		
//		 request = new HttpPut("http://localhost:8090/terGENREST/api/users/"+idUser+"/projects");
//
//		 jsonPayloadRequest = Json.createObjectBuilder().add("projectName", "firstProject").build();
//
//		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
//		request.setHeader("Content-Type", "application/json");
//
//		 response = HttpClientBuilder.create().build().execute( request );
//	
//		  responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));
//		long idProject = responseObject.getJsonNumber("id").longValue();
//		
//		// add Building
//		
//		request = new HttpPut("http://localhost:8090/terGENREST/api/user/"+idUser+"/projects/"+idProject+"/buildings");
//
//		 jsonPayloadRequest = Json.createObjectBuilder().add("type", "local").build();
//
//		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
//		request.setHeader("Content-Type", "application/json");
//
//		response = HttpClientBuilder.create().build().execute( request );
//	
//		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));
//		long idBuilding = responseObject.getJsonNumber("id").longValue();
//		
//		// add floor
//		
//		request = new HttpPut("http://localhost:8090/terGENREST/api/user/"+idUser+"/projects/"+idProject+"/buildings/"+idBuilding+"/floors");
//
//		 jsonPayloadRequest = Json.createObjectBuilder().add("floorNumber", 5).build();
//
//		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
//		request.setHeader("Content-Type", "application/json");
//
//		response = HttpClientBuilder.create().build().execute( request );
//	
//		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));
//		long idFloor = responseObject.getJsonNumber("id").longValue();
//		
//		// update corridor
//		HttpPost requestUpdate = new HttpPost("http://localhost:8090/terGENREST/api/users/" + id);
//
//		 jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.dez")
//				.add("firstName", "JeanUpdate")
//				.add("lastName", "Marc")
//				.add("password", "zeoi")
//				.build();
//
//		requestUpdate.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
//		requestUpdate.setHeader("Content-Type", "application/json");
//
//		response = HttpClientBuilder.create().build().execute( requestUpdate );
//
//		assertEquals(200, response.getStatusLine().getStatusCode());
//
//		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));
//
//		assertTrue(responseObject.containsKey("id"));
//		assertTrue(responseObject.containsKey("email"));
//		assertTrue(responseObject.containsKey("password"));
//		assertTrue(responseObject.containsKey("lastName"));
//		assertTrue(responseObject.containsKey("firstName"));
//
//		assertEquals("JeanUpdate", responseObject.getString("firstName"));
//		
//		
//		// find user by id
//		HttpGet requestGetData = new HttpGet("http://localhost:8090/terGENREST/api/users/" + id);
//		
//		 response = HttpClientBuilder.create().build().execute( requestGetData );
//
//		assertEquals(200, response.getStatusLine().getStatusCode());
//
//		 responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));
//
//		assertTrue(responseObject.containsKey("id"));
//		assertTrue(responseObject.containsKey("email"));
//		assertTrue(responseObject.containsKey("password"));
//		assertTrue(responseObject.containsKey("lastName"));
//		assertTrue(responseObject.containsKey("firstName"));
//
//		assertEquals("jmj@gmail.dez", responseObject.getString("email"));
//		
//		
//		//Delete user
//
//		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8090/terGENREST/api/users/" + id);
//		response = HttpClientBuilder.create().build().execute( requestDeleteData );
//		assertEquals(200, response.getStatusLine().getStatusCode());
		
	}
}
