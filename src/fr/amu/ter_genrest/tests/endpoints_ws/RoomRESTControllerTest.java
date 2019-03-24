package fr.amu.ter_genrest.tests.endpoints_ws;

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

import fr.amu.ter_genrest.tests.utils.PayloadDataRequestREST;
import fr.amu.ter_genrest.tests.utils.RequestsHelper;

public class RoomRESTControllerTest {

	private static final String URL_ROOT_ROOM = "http://localhost:8090/terGENREST/api/rooms/";
	long idUser;
	private long idFloor;
	private long idMotherroom;
	long idRoom;
	
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

		//ADD ZONE
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/zones", PayloadDataRequestREST.jsonPayloadRequestZone());
		idMotherroom = response.getPayload().getJsonNumber("id").longValue();
	}
	
	@After
	public void tearDown() throws IOException {
		RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/users/" + idUser);
	}
	
	@Test
	public void testCRUD() throws IOException {
		// ADD Room
		JsonObject jsonPayloadRequestRoom = Json.createObjectBuilder().add("name", "6").add("type", "Cuisine").build();
		RequestsHelper.ResponseJsonObject response = 
				RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/zones/" + idMotherroom + "/rooms/", 
				jsonPayloadRequestRoom);
		
		assertEquals(201, response.getResponseCode()); 
		
		assertTrue(response.getPayload().containsKey("id"));
		assertFalse(response.getPayload().containsKey("numberRoom"));
		assertFalse(response.getPayload().containsKey("type"));
		
		idRoom = response.getPayload().getJsonNumber("id").longValue();
		
//		// update Room
//		
		jsonPayloadRequestRoom = Json.createObjectBuilder().add("type", "BathRoom").build();
		response = RequestsHelper.httpPOST(URL_ROOT_ROOM+ idRoom , jsonPayloadRequestRoom);
		assertEquals(200, response.getResponseCode()); 
		
		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("type"));
		
		assertEquals("BathRoom", response.getPayload().getString("type"));  
		// find RoomById
		response = RequestsHelper.httpGetJsonObject(URL_ROOT_ROOM+idRoom);

		assertEquals(200, response.getResponseCode()); 
		assertTrue(response.getPayload().containsKey("name"));
		
		assertEquals("6", response.getPayload().getString("name"));
		

		//Remove Room

		response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/zones/" + idMotherroom + "/rooms/"+idRoom);
		assertEquals(200, response.getResponseCode());
	}
	
	@Test
	public void getRoomWithUnknowID() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpGetJsonObject(URL_ROOT_ROOM+ 99);
		assertEquals(404, response.getResponseCode());
	}
	
	@Test
	public void createRoomWithUnknowMotherRoomID() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("type", "BathRoom")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/zones/" + 8796 + "/rooms/", jsonPayloadRequest);

		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void createRoomWithNoType() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("numberRoom", 6)
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/zones/" + idMotherroom + "/rooms/", jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createRoomWithNoNumberRoom() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("type", "salon")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/zones/" + idMotherroom + "/rooms/", jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
	}
	
	@Test
	public void deleteRoomWithUnknowID() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/zones/" + idMotherroom + "/rooms/" +7645);
		assertEquals(404, response.getResponseCode());

	}
	
	@Test
	public void deleteRoomWithUnknowIDMotherRoom() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/zones/" + 7645 + "/rooms/" +idRoom);
		assertEquals(404, response.getResponseCode());
	}
}
