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

public class CorridorRESTControllerTest {

	private static final String URL_ROOT_USER = "http://localhost:8090/terGENREST/api/users/";
	private static final String URL_ROOT_PROJECT = "http://localhost:8090/terGENREST/api/projects/";
	long idUser;
	private long idFloor;
	private long idMotherRoom;

	@Before
	public void setUp() throws Exception {
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

		// ADD MOTHER ROOM
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/motherRooms",
				PayloadDataRequestREST.jsonPayloadRequestMotherRoom());
		idMotherRoom = response.getPayload().getJsonNumber("id").longValue();
	}

	@After
	public void tearDown() throws IOException {
		RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/users/" + idUser);
	}

	@Test
	public void testCRUDFloorRest() throws IOException {

		// ADD CORRIDOR WITHOUT numberCorridor

		JsonObject jsonPayloadRequestCorridorNumber = Json.createObjectBuilder().build();
		RequestsHelper.ResponseJsonObject responseWithoutNumber = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/floors/" + 7 + "/corridors", jsonPayloadRequestCorridorNumber);

		assertEquals(400, responseWithoutNumber.getResponseCode());

		// ADD CORRIDOR

		JsonObject jsonPayloadRequestCorridor = Json.createObjectBuilder().add("numberCorridor", 6).build();
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/floors/" + idFloor + "/corridors", jsonPayloadRequestCorridor);

		assertEquals(201, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertFalse(response.getPayload().containsKey("numberCorridor"));

		long idCorridor = response.getPayload().getJsonNumber("id").longValue();

		// UPDATE CORRIDOR

		jsonPayloadRequestCorridor = Json.createObjectBuilder().add("numberCorridor", 8).build();
		response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/corridors/" + idCorridor,
				jsonPayloadRequestCorridor);
		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("numberCorridor"));

		assertEquals(8, response.getPayload().getInt("numberCorridor"));

		// UPDATE CORRIDOR BY id
		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/corridors/" + idCorridor);

		assertEquals(200, response.getResponseCode());
		assertTrue(response.getPayload().containsKey("numberCorridor"));
		assertEquals(8, response.getPayload().getInt("numberCorridor"));

		// FIND CORRIDOR WITHOUT ID
		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/corridors/" + 241);

		assertEquals(404, response.getResponseCode());

		// DELETE CORRIDOR
		response = RequestsHelper
				.httpDELETE("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/corridors/" + idCorridor);
		assertEquals(200, response.getResponseCode());
		
		// DELETE CORRIDOR WITHOUT FLOOR ID
				response = RequestsHelper
						.httpDELETE("http://localhost:8090/terGENREST/api/floors/" + 45 + "/corridors/" + idCorridor);
				assertEquals(404, response.getResponseCode());

		// FIND
		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/corridors/" + idCorridor);
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void testCRUDMotheRoomRest() throws IOException {

		// ADD CORRIDOR WITHOUT MOTHER ROOM
		JsonObject jsonPayloadRequestCorridorWithoutMother = Json.createObjectBuilder().add("numberCorridor", 6)
				.build();
		RequestsHelper.ResponseJsonObject responseCorridor = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/motherRooms/" + 147 + "/corridors",
				jsonPayloadRequestCorridorWithoutMother);

		assertEquals(404, responseCorridor.getResponseCode());

		// ADD CORRIDOR WITHOUT numberCorridor
		JsonObject jsonPayloadRequestCorridorWithoutnumberCorridor = Json.createObjectBuilder().add("numberCorridor", 6)
				.build();
		RequestsHelper.ResponseJsonObject responseFalse = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/motherRooms/" + 147 + "/corridors",
				jsonPayloadRequestCorridorWithoutnumberCorridor);

		assertEquals(404, responseFalse.getResponseCode());

		// ADD CORRIDOR
		JsonObject jsonPayloadRequestCorridor = Json.createObjectBuilder().add("numberCorridor", 6).build();
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(
				"http://localhost:8090/terGENREST/api/motherRooms/" + idMotherRoom + "/corridors",
				jsonPayloadRequestCorridor);

		assertEquals(201, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertFalse(response.getPayload().containsKey("numberCorridor"));

		long idCorridor = response.getPayload().getJsonNumber("id").longValue();

		// UPDATE CORRIDOR

		jsonPayloadRequestCorridor = Json.createObjectBuilder().add("numberCorridor", 8).build();
		response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/corridors/" + idCorridor,
				jsonPayloadRequestCorridor);
		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("numberCorridor"));

		assertEquals(8, response.getPayload().getInt("numberCorridor"));

		// FIND CORRIDOR BY ID

		RequestsHelper.ResponseJsonObject responseObject = RequestsHelper
				.httpGetJsonObject("http://localhost:8090/terGENREST/api/corridors/" + idCorridor);

		assertEquals(200, responseObject.getResponseCode());
		assertTrue(responseObject.getPayload().containsKey("numberCorridor"));
		assertEquals(8, response.getPayload().getInt("numberCorridor"));

		// FIND CORRIDOR WITHOUT ID
		RequestsHelper.ResponseJsonObject responseCorridorWithoutId = RequestsHelper
				.httpGetJsonObject("http://localhost:8090/terGENREST/api/corridors/" + 7777);

		assertEquals(404, responseCorridorWithoutId.getResponseCode());

		// DELETE CORRIDOR WITHOUT ID

		response = RequestsHelper
				.httpDELETE("http://localhost:8090/terGENREST/api/motherRooms/" + idMotherRoom + "/corridors/" + 7777);
		assertEquals(404, response.getResponseCode());

		// DELETE CORRIDOR WITHOUT MOTHERROOM ID

		response = RequestsHelper
				.httpDELETE("http://localhost:8090/terGENREST/api/motherRooms/" + 88 + "/corridors/" + idCorridor);
		assertEquals(404, response.getResponseCode());

		// DELETE CORRIDOR

		response = RequestsHelper.httpDELETE(
				"http://localhost:8090/terGENREST/api/motherRooms/" + idMotherRoom + "/corridors/" + idCorridor);
		assertEquals(200, response.getResponseCode());

	}

}
