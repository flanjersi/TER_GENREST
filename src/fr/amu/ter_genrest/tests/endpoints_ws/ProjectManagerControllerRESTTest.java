package fr.amu.ter_genrest.tests.endpoints_ws;

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

import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.tests.utils.PayloadDataRequestREST;
import fr.amu.ter_genrest.tests.utils.RequestsHelper;
import fr.amu.ter_genrest.tests.utils.Utils;

public class ProjectManagerControllerRESTTest {

	private static final String URL_ROOT_USER = "http://localhost:8090/terGENREST/api/users/";
	private static final String URL_ROOT_PROJECT = "http://localhost:8090/terGENREST/api/projects/";
	long idUser;
	long idProject;
	
	@Before
	public void setUp() throws Exception {
		//ADD USER
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_USER, PayloadDataRequestREST.jsonPayloadRequestUser());
		assertEquals(200, response.getResponseCode());
		idUser = response.getPayload().getJsonNumber("id").longValue();
		//ADD PROJECT
		response = RequestsHelper.httpPUT(URL_ROOT_USER + idUser + "/projects", PayloadDataRequestREST.jsonPayloadRequestProject());
		assertEquals(201, response.getResponseCode());
		idProject = response.getPayload().getJsonNumber("id").longValue();
	}

	@After
	public void tearDown() throws Exception {
		RequestsHelper.httpDELETE(URL_ROOT_USER + idUser);
	}
	
	@Test
	public void testFindProjectByUnknowID() throws IOException {	
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpGetJsonObject(URL_ROOT_PROJECT + 0);
		assertEquals(404, response.getResponseCode());		
	}
	
	@Test
	public void testUpdateProject() throws IOException {	
		JsonObject jsonPayloadRequest2 = Json.createObjectBuilder().add("projectName", "MySecondProject")
				.build();
		
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPOST(URL_ROOT_PROJECT+idProject,jsonPayloadRequest2);
		assertEquals(200, response.getResponseCode());	
		assertEquals("MySecondProject", response.getPayload().getString("projectName"));		
	}
	
	@Test
	public void testUpdateOfExistingProject() throws IOException {	
		JsonObject jsonPayloadRequest2 = Json.createObjectBuilder().add("projectName", "MySecondProject")
				.build();		
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPOST(URL_ROOT_PROJECT+0,jsonPayloadRequest2);
		assertEquals(404, response.getResponseCode());		
	}

	@Test
	public void testRemoveProject() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpDELETE(URL_ROOT_USER +idUser+"/projects/"+idProject);
		assertEquals(200, response.getResponseCode());	
	}
	
	@Test
	public void testRemoveProjectWithInexistentUser() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpDELETE(URL_ROOT_USER +1+"/projects/"+idProject);
		assertEquals(404, response.getResponseCode());
	}
	
	@Test
	public void testRemoveInexistentProject() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpDELETE(URL_ROOT_USER +idUser+"/projects/"+0);
		assertEquals(404, response.getResponseCode());
	}
	
	
	
	
	

}


