package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.amu.terGENREST.tests.utils.PayloadDataRequestREST;
import fr.amu.terGENREST.tests.utils.RequestsHelper;
import fr.amu.terGENREST.tests.utils.Utils;
import fr.amu.terGENREST.tests.utils.RequestsHelper.ResponseJsonObject;


public class FloorManagerControllerRESTTest {
	
	private static final String URL_ROOT_USER = "http://localhost:8090/terGENREST/api/users/";
	private static final String URL_ROOT_BUILDING = "http://localhost:8090/terGENREST/api/buildings/";
	private static final String URL_ROOT_PROJECT = "http://localhost:8090/terGENREST/api/projects/";
	private static final String URL_ROOT_FLOOR = "http://localhost:8090/terGENREST/api/floors/";

	private long idBuilding;
	long idUser;
	long idProject;
	long idFloor;
	RequestsHelper.ResponseJsonObject response;

	@Before
	public void setup() throws IOException {		
		//ADD USER
		response = RequestsHelper.httpPUT(URL_ROOT_USER, PayloadDataRequestREST.jsonPayloadRequestUser());
		assertEquals(200, response.getResponseCode());
		idUser = response.getPayload().getJsonNumber("id").longValue();
		//ADD PROJECT
		response = RequestsHelper.httpPUT(URL_ROOT_USER + idUser + "/projects", PayloadDataRequestREST.jsonPayloadRequestProject());
		assertEquals(201, response.getResponseCode());
		idProject = response.getPayload().getJsonNumber("id").longValue();
		//ADD BUILDING 
		response = RequestsHelper.httpPUT(URL_ROOT_PROJECT+ idProject + "/buildings", PayloadDataRequestREST.jsonPayloadRequestBuilding());
		assertEquals(201, response.getResponseCode());
		idBuilding = response.getPayload().getJsonNumber("id").longValue();
	}
	
	@After
	public void tearDown() throws Exception {
		RequestsHelper.httpDELETE(URL_ROOT_USER + idUser);
	}

	@Test
	public void testCRUD() throws IOException {
		
		//Add Floor
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("floorNumber", 33)
				.build();
		response = RequestsHelper.httpPUT(URL_ROOT_BUILDING + idBuilding + "/floors", jsonPayloadRequest);
		
		assertEquals(201, response.getResponseCode());
		assertTrue(response.getPayload().containsKey("id"));
		assertFalse(response.getPayload().containsKey("floorNumber"));
		idFloor = response.getPayload().getJsonNumber("id").longValue();
		
		
		//Update Floor
		jsonPayloadRequest = Json.createObjectBuilder()
				.add("floorNumber", 2)		
				.build();

		response = RequestsHelper.httpPOST(URL_ROOT_FLOOR + idFloor, jsonPayloadRequest);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("floorNumber"));

		assertEquals(2, response.getPayload().getInt("floorNumber"));
		
		// Find FloorById
		response = RequestsHelper.httpGetJsonObject(URL_ROOT_FLOOR+idFloor);

		assertEquals(200, response.getResponseCode()); 
		assertTrue(response.getPayload().containsKey("floorNumber"));
		assertEquals(2, response.getPayload().getInt("floorNumber"));
		
		
		//Remove Floor
		response = RequestsHelper.httpDELETE(URL_ROOT_BUILDING + idBuilding + "/floors/"+idFloor);
		assertEquals(200, response.getResponseCode());	
		
		// Find
		response = RequestsHelper.httpGetJsonObject(URL_ROOT_FLOOR+idFloor);
		assertEquals(404, response.getResponseCode());

	}
	
	@Test
	public void getFloorWithUnknowID() throws IOException {
		ResponseJsonObject response = RequestsHelper.httpGetJsonObject(URL_ROOT_FLOOR+ 65);
		assertEquals(404, response.getResponseCode());
	}
	
	@Test
	public void createFloorWithUnknowBuildingID() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("floorNumber", 1)
				.build();

		response = RequestsHelper.httpPUT(URL_ROOT_BUILDING + 8796 + "/floors", jsonPayloadRequest);

		assertEquals(404, response.getResponseCode());
	}
	@Test
	public void createFloorWithFloorNumber() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.build();

		response = RequestsHelper.httpPUT(URL_ROOT_BUILDING + idBuilding + "/floors", jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
	}
	
	@Test
	public void createFloorUniqueFloorNumberConstraint() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("floorNumber", 2)
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_BUILDING + idBuilding + "/floors", jsonPayloadRequest);

		jsonPayloadRequest = Json.createObjectBuilder()
				.add("floorNumber", 2)
				.build();

		response = RequestsHelper.httpPUT(URL_ROOT_BUILDING + idBuilding + "/floors", jsonPayloadRequest);
	
		assertEquals(400, response.getResponseCode());
	}
	
	@Test
	public void updateFloorWithUnknowID() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpPOST(URL_ROOT_FLOOR+9999, PayloadDataRequestREST.jsonPayloadRequestFloor());
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void deleteFloorWithUnknowID() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpDELETE(URL_ROOT_BUILDING + idBuilding + "/floors/"+999);
		assertEquals(404, response.getResponseCode());
	}
	
	@Test
	public void deleteFloorWithUnknowIDBuilding() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpDELETE(URL_ROOT_BUILDING + 44 + "/floors/"+idFloor);
		assertEquals(404, response.getResponseCode());
	}
}