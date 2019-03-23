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
import fr.amu.ter_genrest.tests.utils.RequestsHelper.ResponseJsonObject;

public class ZoneControllerRESTtest {

	private Long idUser;
	private Long idFloor;

	@Before
	public void setUp() throws IOException {
		// ADD USER
		RequestsHelper.ResponseJsonObject response = RequestsHelper
				.httpPUT("http://localhost:8090/terGENREST/api/users", PayloadDataRequestREST.jsonPayloadRequestUser());
		idUser = response.getPayload().getJsonNumber("id").longValue();

		// ADD PROJECT
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users/" + idUser + "/projects",
				PayloadDataRequestREST.jsonPayloadRequestProject());
		long idProject = response.getPayload().getJsonNumber("id").longValue();

		// ADD BUILDING
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/projects/" + idProject + "/buildings",
				PayloadDataRequestREST.jsonPayloadRequestBuilding());
		long idBuilding = response.getPayload().getJsonNumber("id").longValue();

		// ADD FLOOR
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/buildings/" + idBuilding + "/floors",
				PayloadDataRequestREST.jsonPayloadRequestFloor());
		idFloor = response.getPayload().getJsonNumber("id").longValue();

	}

	@After
	public void tearDown() throws IOException {
		RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/users/" + idUser);
	}


	@Test
	public void testCRUDMotherRoom() throws IOException {
		// ADD ZONE
		JsonObject payloadZone = Json.createObjectBuilder().add("type", "Chambre").add("name", "12")
				.build();
		ResponseJsonObject response = RequestsHelper
				.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/zones", payloadZone);

		assertEquals(200, response.getResponseCode());

		assertFalse(response.getPayload().containsKey("type"));
		assertFalse(response.getPayload().containsKey("name"));
		long idMotherRoomToFind = response.getPayload().getJsonNumber("id").longValue();

		// ADD ZONE WITHOUT FLOOR

		payloadZone = Json.createObjectBuilder().add("type", "Chambre").build();
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/floors/" + 888 + "/zones",
				payloadZone);

		assertEquals(404, response.getResponseCode());

		// ADD ZONE WITHOUT numberMotherRoom

		payloadZone = Json.createObjectBuilder().add("type", "Chambre").build();
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/zones",
				payloadZone);

		assertEquals(400, response.getResponseCode());

		// ADD ZONE WITHOUT type

		payloadZone = Json.createObjectBuilder().add("name", "12").build();
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/zones",
				payloadZone);

		assertEquals(400, response.getResponseCode());

		// ADD ZONE WITHOUT IdFloor

		payloadZone = Json.createObjectBuilder().add("type", "Chambre").add("name", "12").build();
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/floors/" + 20000 + "/zones",
				payloadZone);

		assertEquals(404, response.getResponseCode());

		// UPDATE ZONE WITHOUT numberMotherRoom

		payloadZone = Json.createObjectBuilder().add("type", "Salon").build();
		response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/zones/" + idMotherRoomToFind,
				payloadZone);

		assertEquals(200, response.getResponseCode());
		assertEquals("Salon", response.getPayload().getString("type"));

		// UPDATE ZONE

		payloadZone = Json.createObjectBuilder().add("type", "Salon").add("name", "18").build();
		response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/zones/" + idMotherRoomToFind,
				payloadZone);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("type"));
		assertTrue(response.getPayload().containsKey("name"));
		assertEquals("Salon", response.getPayload().getString("type"));

		// FIND ZONE
		response = RequestsHelper
				.httpGetJsonObject("http://localhost:8090/terGENREST/api/zones/" + idMotherRoomToFind);
		assertEquals(200, response.getResponseCode());

		// FIND ZONE WITHOUT FALSE ID
		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/zones/" + 7777);
		assertEquals(404, response.getResponseCode());

		// REMOVE ZONE

		JsonObject payloadZoneToRemove = Json.createObjectBuilder().add("type", "Box").add("name", "8")
				.build();
		ResponseJsonObject responseMotherRoom = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/floors/" + idFloor + "/zones", payloadZoneToRemove);

		long idMotherRoomToRemove = responseMotherRoom.getPayload().getJsonNumber("id").longValue();

		assertEquals(200, responseMotherRoom.getResponseCode());
		response = RequestsHelper.httpDELETE(
				"http://localhost:8090/terGENREST/api/floors/" + idFloor + "/zones/" + idMotherRoomToRemove);

		assertEquals(200, response.getResponseCode());

		// REMOVE ZONE WITHOUT Floor id
		response = RequestsHelper.httpDELETE(
				"http://localhost:8090/terGENREST/api/floors/" + 888 + "/zones/" + idMotherRoomToRemove);

		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void testROOM() throws IOException {

		JsonObject payloadZone = Json.createObjectBuilder().add("type", "Chambre").add("name", "12")
				.build();
		ResponseJsonObject response = RequestsHelper
				.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/zones", payloadZone);

		assertEquals(200, response.getResponseCode());
		long idMotherRoomToFind = response.getPayload().getJsonNumber("id").longValue();

		// ADD ROOM without numberRom

		JsonObject payloadRoom = Json.createObjectBuilder().add("type", "Chambre").build();
		ResponseJsonObject responseRoom = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/zones/" + idMotherRoomToFind + "/rooms", payloadRoom);

		assertEquals(400, responseRoom.getResponseCode());

		// ADD ROOM without type

		payloadRoom = Json.createObjectBuilder().add("name", "14").build();
		responseRoom = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/zones/" + idMotherRoomToFind + "/rooms", payloadRoom);

		assertEquals(400, responseRoom.getResponseCode());

		// ADD ROOM
		payloadRoom = Json.createObjectBuilder().add("name", "14").add("type", "Chambre").build();
		responseRoom = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/zones/" + idMotherRoomToFind + "/rooms", payloadRoom);

		assertEquals(201, responseRoom.getResponseCode());

		// DELETE ROOM

		long idRoomToDelete = response.getPayload().getJsonNumber("id").longValue();
		response = RequestsHelper.httpDELETE(
				"http://localhost:8090/terGENREST/api/zones/" + idMotherRoomToFind + "/rooms/" + idRoomToDelete);
		assertEquals(201, responseRoom.getResponseCode());
	}
	
	@Test
	public void testCORRIDOR() throws IOException {

		JsonObject payloadZone = Json.createObjectBuilder().add("type", "Chambre").add("name", "12")
				.build();
		ResponseJsonObject response = RequestsHelper
				.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/zones", payloadZone);

		assertEquals(200, response.getResponseCode());
		long idMotherRoomToFind = response.getPayload().getJsonNumber("id").longValue();

		// ADD CORRIDOR with numberCorridor = 0

		JsonObject payloadRoom = Json.createObjectBuilder().add("numberCorridor", 0).build();
		ResponseJsonObject responseRoom = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/zones/" + idMotherRoomToFind + "/corridors", payloadRoom);

		assertEquals(400, responseRoom.getResponseCode());

		// ADD CORRIDOR without numberCorridor
		payloadRoom = Json.createObjectBuilder().build();
		responseRoom = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/zones/" + idMotherRoomToFind + "/corridors", payloadRoom);

		assertEquals(400, responseRoom.getResponseCode());

		// ADD CORRIDOR

		payloadRoom = Json.createObjectBuilder().add("numberCorridor", 4).build();
		responseRoom = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/zones/" + idMotherRoomToFind + "/corridors", payloadRoom);

		assertEquals(201, responseRoom.getResponseCode());

		// DELETE CORRIDOR

		long idCorridorToDelete = response.getPayload().getJsonNumber("id").longValue();
		response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/zones/" + idMotherRoomToFind
				+ "/corridors/" + idCorridorToDelete);
		assertEquals(201, responseRoom.getResponseCode());

	}

}
