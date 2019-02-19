package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.ejb.embeddable.EJBContainer;
import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.tests.utils.Utils;

public class ProjectManagerControllerRESTTest {

	long id;

	@Before
	public void setUp() throws Exception {
		
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
				id = responseObjectUser.getJsonNumber("id").longValue();
	}
	

	@After
	public void tearDown() throws Exception {
		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8090/terGENREST/api/users/" + id);
		HttpResponse response = HttpClientBuilder.create().build().execute( requestDeleteData );
		assertEquals(200, response.getStatusLine().getStatusCode());
	}
	
	@Test
	public void testCreateProject() throws IOException {		
		HttpPut requestProject = new HttpPut("http://localhost:8090/terGENREST/api/users/"+ id + "/projects");
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("projectName", "MyFirstProject").build();
		requestProject.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		requestProject.setHeader("Content-Type", "application/json");
		HttpResponse responseProject = HttpClientBuilder.create().build().execute( requestProject );
		assertEquals(201, responseProject.getStatusLine().getStatusCode());
		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(responseProject.getEntity()));
		assertTrue(responseObject.containsKey("id"));
		assertFalse(responseObject.containsKey("projectName"));

	}
	
	@Test
	public void testUpdateProject() throws IOException {

		HttpPut requestProject = new HttpPut("http://localhost:8090/terGENREST/api/users/"+ id + "/projects");
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("projectName", "MyFirstProject").build();
		requestProject.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		requestProject.setHeader("Content-Type", "application/json");
		HttpResponse responseProject = HttpClientBuilder.create().build().execute( requestProject );
		assertEquals(201, responseProject.getStatusLine().getStatusCode());
		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(responseProject.getEntity()));
		assertTrue(responseObject.containsKey("id"));
		assertFalse(responseObject.containsKey("projectName"));
		long idProject = responseObject.getJsonNumber("id").longValue();
		HttpPost requestProjectUpdate = new HttpPost("http://localhost:8090/terGENREST/api/users/"+ id + "/projects");
		JsonObject jsonPayloadRequest2 = Json.createObjectBuilder().add("projectName", "MySecondProject")
				.add("id", idProject)
				.build();

		requestProjectUpdate.setEntity(new StringEntity(jsonPayloadRequest2.toString(), "UTF-8"));
		requestProjectUpdate.setHeader("Content-Type", "application/json");
		HttpResponse responseProject2 = HttpClientBuilder.create().build().execute( requestProjectUpdate );
		assertEquals(200, responseProject2.getStatusLine().getStatusCode());
		JsonObject responseObject2 = Utils.stringToJsonObject(EntityUtils.toString(responseProject2.getEntity()));
		assertTrue(responseObject2.containsKey("id"));
		assertTrue(responseObject2.containsKey("projectName"));		
		assertEquals("MySecondProject", responseObject2.getString("projectName"));		
	}
	
	
	
	@Test
	public void testRemoveProject() throws IOException {

		HttpPut requestProject = new HttpPut("http://localhost:8090/terGENREST/api/users/"+ id + "/projects");
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("projectName", "MyFirstProject").build();
		requestProject.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		requestProject.setHeader("Content-Type", "application/json");
		HttpResponse responseProject = HttpClientBuilder.create().build().execute( requestProject );
		assertEquals(201, responseProject.getStatusLine().getStatusCode());
		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(responseProject.getEntity()));
		assertTrue(responseObject.containsKey("id"));
		assertFalse(responseObject.containsKey("projectName"));
		long idProject = responseObject.getJsonNumber("id").longValue();
		HttpDelete requestDeleteProject = new HttpDelete("http://localhost:8090/terGENREST/api/users/"+ id + "/projects/"+ idProject);
		HttpResponse response2 = HttpClientBuilder.create().build().execute( requestDeleteProject );
		assertEquals(200, response2.getStatusLine().getStatusCode());
	
	}

}


