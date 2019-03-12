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
import fr.amu.ter_genrest.tests.utils.RequestsHelper.ResponseJsonObject;



public class BuildingRESTControllerTest {

	private static final String URL_ROOT_USER = "http://localhost:8090/terGENREST/api/users/";
	private static final String URL_ROOT_PROJECT = "http://localhost:8090/terGENREST/api/projects/";
	private static final String URL_ROOT_BUILDING = "http://localhost:8090/terGENREST/api/buildings/";

	//Add data
	private long idUser;
	private long idProject;
	private long idBuilding;
	RequestsHelper.ResponseJsonObject response;


	@Before
	public void setup() throws IOException {
		//ADD User
 		response = RequestsHelper.httpPUT(URL_ROOT_USER, PayloadDataRequestREST.jsonPayloadRequestUser());
		idUser = response.getPayload().getJsonNumber("id").longValue();
		assertEquals(200, response.getResponseCode());


		//ADD Project
		response = RequestsHelper.httpPUT(URL_ROOT_USER+ idUser +"/projects", PayloadDataRequestREST.jsonPayloadRequestProject());
		idProject = response.getPayload().getJsonNumber("id").longValue();

		assertEquals(201, response.getResponseCode());
	}
	
	@After
	public void tearDown() throws Exception {
		RequestsHelper.httpDELETE(URL_ROOT_USER + idUser);
	}

	@Test
	public void testCRUD() throws IOException {
		
		//Add Building
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("type", "Batiment")
				.add("address", 
						Json.createObjectBuilder()
							.add("city", "Marseille")
							.add("street", "street")
							.add("country", "France")
							.build())
				.build();
		response = RequestsHelper.httpPUT(URL_ROOT_PROJECT + idProject + "/buildings", jsonPayloadRequest);
		assertEquals(201, response.getResponseCode());
		assertTrue(response.getPayload().containsKey("id"));
		assertFalse(response.getPayload().containsKey("type"));
		idBuilding = response.getPayload().getJsonNumber("id").longValue();

		//Update Building 
		jsonPayloadRequest = Json.createObjectBuilder()
				.add("type", "studetteUpdated")		
				.build();

		response = RequestsHelper.httpPOST(URL_ROOT_BUILDING + idBuilding, jsonPayloadRequest);
		assertEquals(200, response.getResponseCode());
		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("type"));
		assertEquals("studetteUpdated", response.getPayload().getString("type"));
		//Remove Building
		response = RequestsHelper.httpDELETE(URL_ROOT_PROJECT + idProject + "/buildings/"+idBuilding);
		assertEquals(200, response.getResponseCode());	
	}
	
	@Test
	public void getBuildingWithUnknowID() throws IOException {
		response = RequestsHelper.httpGetJsonObject(URL_ROOT_BUILDING + "99999999");
		assertEquals(404, response.getResponseCode());
	}
	
	@Test
	public void createBuildingWithUnknowProjectID() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("type", "CommercialLocal")
				.build();
		response = RequestsHelper.httpPUT(URL_ROOT_PROJECT + 8796 + "/buildings", jsonPayloadRequest);
		assertEquals(404, response.getResponseCode());
	
	}
	
	@Test
	public void createBuildingWithNoType() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.build();
		response = RequestsHelper.httpPUT(URL_ROOT_PROJECT + idProject + "/buildings", jsonPayloadRequest);
		assertEquals(400, response.getResponseCode());
	}
	
	@Test
	public void deleteBuildingWithUnknowID() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpDELETE(URL_ROOT_PROJECT + idProject + "/buildings/" + 99);
		assertEquals(404, response.getResponseCode());
	}
	
	@Test
	public void deleteBuildingWithUnknowIDProject() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpDELETE(URL_ROOT_PROJECT + 99 + "/buildings/" + idBuilding);
		assertEquals(404, response.getResponseCode());
	}
}