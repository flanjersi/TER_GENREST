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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.tests.utils.Utils;

public class BuildingRESTControllerTest {

	
	
	
	
	long idUser;
	long idProject;
	
	@Before
	public void setUp() throws Exception {
		
		// ADD USER
				HttpPut requestUser = new HttpPut("http://localhost:8090/terGENREST/api/users/");
				JsonObject jsonPayloadRequestUser = Json.createObjectBuilder().add("email", "veersionTest@versionTest.versionTest")
						.add("firstName", "firstNameVersionTest")
						.add("lastName", "lastNameVersionTest")
						.add("password", "passwordVersionTest")
						.build();
				requestUser.setEntity(new StringEntity(jsonPayloadRequestUser.toString(), "UTF-8"));
				requestUser.setHeader("Content-Type", "application/json");
				HttpResponse responseUser = HttpClientBuilder.create().build().execute( requestUser );
				assertEquals(200, responseUser.getStatusLine().getStatusCode());
				JsonObject responseObjectUser = Utils.stringToJsonObject(EntityUtils.toString(responseUser.getEntity()));
				assertTrue(responseObjectUser.containsKey("id"));
				assertFalse(responseObjectUser.containsKey("email"));
				assertFalse(responseObjectUser.containsKey("password"));
				idUser = responseObjectUser.getJsonNumber("id").longValue();
				
		// ADD PROJECT	
				HttpPut requestProject = new HttpPut("http://localhost:8090/terGENREST/api/users/"+ idUser + "/projects");
				JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("projectName", "MyFirstProject").build();
				requestProject.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
				requestProject.setHeader("Content-Type", "application/json");
				HttpResponse responseProject = HttpClientBuilder.create().build().execute( requestProject );
				assertEquals(201, responseProject.getStatusLine().getStatusCode());
				JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(responseProject.getEntity()));
				assertTrue(responseObject.containsKey("id"));
				assertFalse(responseObject.containsKey("projectName"));
				idProject = responseObject.getJsonNumber("id").longValue();
	}
	

	
	@Test
	public void testCRUD() throws IOException {

		//Add data
		HttpPut request = new HttpPut("http://localhost:8090/terGENREST/api/users/"+idUser+"/projects/"+idProject+"/buildings/");

		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("type", "studio").build();

		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		request.setHeader("Content-Type", "application/json");

		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		
		assertEquals(200, response.getStatusLine().getStatusCode());
		

		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertFalse(responseObject.containsKey("type"));	

		long id = responseObject.getJsonNumber("id").longValue();
		
		//		//Update data

		HttpPost requestUpdate = new HttpPost("http://localhost:8090/terGENREST/api/users/"+idUser+"/projects/"+idProject+"/buildings/"+id);

		jsonPayloadRequest = Json.createObjectBuilder().add("type", "studiostudio")
				.add("id", id)
				.build();


		requestUpdate.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		requestUpdate.setHeader("Content-Type", "application/json");

		response = HttpClientBuilder.create().build().execute( requestUpdate );

		assertEquals(200, response.getStatusLine().getStatusCode());

		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertTrue(responseObject.containsKey("type"));

		assertEquals("studiostudio", responseObject.getString("type"));		
	
//
//		//Find data
//
//		HttpGet requestGetData = new HttpGet("http://localhost:8090/terGENREST/api/users/1/projects/1/buildings/" + id);
//		
//		response = HttpClientBuilder.create().build().execute( requestGetData );
//		
//		assertEquals(200, response.getStatusLine().getStatusCode());
//		
//		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));
//		
//		assertTrue(responseObject.containsKey("id"));
//		assertTrue(responseObject.containsKey("type"));
//		
//		assertEquals("studio", responseObject.getString("type"));
//		
//		
//		//Delete building
//		
//		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8090/terGENREST/api/users/1/projects/1/buildings/" + id);
//		response = HttpClientBuilder.create().build().execute( requestDeleteData );
//		assertEquals(200, response.getStatusLine().getStatusCode());
	}
}