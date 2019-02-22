package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.amu.terGENREST.tests.utils.PayloadDataRequestREST;
import fr.amu.terGENREST.tests.utils.RequestsHelper;

public class RoomRESTControllerTest {

	private static final String URL_ROOT_ROOM = "http://localhost:8090/terGENREST/api/rooms/";
	private static final String URL_ROOT_PROJECT = "http://localhost:8090/terGENREST/api/projects/";
	long idUser;
	private long idFloor;
	private long idMotherroom;
	
	@Before
	public void setUp() throws Exception {
		//ADD USER
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users", PayloadDataRequestREST.jsonPayloadRequestUser());
		 idUser = response.getPayload().getJsonNumber("id").longValue();

		//ADD PROJECT
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users/" + idUser + "/projects", PayloadDataRequestREST.jsonPayloadRequestProject());
		long idProject = response.getPayload().getJsonNumber("id").longValue();

		//ADD BUILDING
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/projects/" + idProject + "/buildings", PayloadDataRequestREST.jsonPayloadRequestBuilding());
		long idBuilding = response.getPayload().getJsonNumber("id").longValue();

		//ADD FLOOR
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/buildings/" + idBuilding + "/floors", PayloadDataRequestREST.jsonPayloadRequestFloor());
		 idFloor = response.getPayload().getJsonNumber("id").longValue();

		//ADD MOTHER ROOM
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/motherRooms", PayloadDataRequestREST.jsonPayloadRequestMotherRoom());
		idMotherroom = response.getPayload().getJsonNumber("id").longValue();
	}
	
	@After
	public void tearDown() throws IOException {
		RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/users/" + idUser);
	}
	
	@Test
	public void testCRUD() throws IOException {
		// ADD Room
		JsonObject jsonPayloadRequestRoom = Json.createObjectBuilder().add("numberRoom", 6).add("type", "Cuisine").build();
		RequestsHelper.ResponseJsonObject response = 
				RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/motherRooms/" + idMotherroom + "/rooms/", 
				jsonPayloadRequestRoom);
		
		assertEquals(201, response.getResponseCode()); 
		
		assertTrue(response.getPayload().containsKey("id"));
		assertFalse(response.getPayload().containsKey("numberRoom"));
		assertFalse(response.getPayload().containsKey("type"));
		
		long idRoom = response.getPayload().getJsonNumber("id").longValue();
		
//		// update Room
//		
		jsonPayloadRequestRoom = Json.createObjectBuilder().add("type", "BathRoom").build();
		response = RequestsHelper.httpPOST(URL_ROOT_ROOM+ idRoom , jsonPayloadRequestRoom);
		assertEquals(200, response.getResponseCode()); 
		
		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("type"));
		
		assertEquals("BathRoom", response.getPayload().getString("type"));  
		// find RoomById
		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/rooms/"+idRoom);

		assertEquals(200, response.getResponseCode()); 
		assertTrue(response.getPayload().containsKey("numberRoom"));
		assertEquals(6, response.getPayload().getInt("numberRoom"));
		
		//Remove Room
		response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/motherRooms/" + idMotherroom + "/rooms/"+idRoom);
		assertEquals(200, response.getResponseCode());
}
}