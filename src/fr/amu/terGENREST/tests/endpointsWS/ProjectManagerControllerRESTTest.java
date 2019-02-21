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
import fr.amu.terGENREST.tests.utils.PayloadDataRequestREST;
import fr.amu.terGENREST.tests.utils.RequestsHelper;
import fr.amu.terGENREST.tests.utils.Utils;

public class ProjectManagerControllerRESTTest {


	long idUser;
	long idProject;
	
	@Before
	public void setUp() throws Exception {
		//ADD USER
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users/", PayloadDataRequestREST.jsonPayloadRequestUser());
		assertEquals(200, response.getResponseCode());
		idUser = response.getPayload().getJsonNumber("id").longValue();
		//ADD PROJECT
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users/" + idUser + "/projects", PayloadDataRequestREST.jsonPayloadRequestProject());
		assertEquals(201, response.getResponseCode());
		idProject = response.getPayload().getJsonNumber("id").longValue();
	}

	@After
	public void tearDown() throws Exception {
		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8090/terGENREST/api/users/" + idUser);
		HttpResponse response = HttpClientBuilder.create().build().execute( requestDeleteData );
		assertEquals(200, response.getStatusLine().getStatusCode());
	}

	@Test
	public void testUpdateProject() throws IOException {

		HttpPost requestProjectUpdate = new HttpPost("http://localhost:8090/terGENREST/api/projects/"+idProject);	
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
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/users/"+idUser+"/projects/"+idProject);
		assertEquals(200, response.getResponseCode());
	}

}


