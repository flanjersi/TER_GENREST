package fr.amu.ter_genrest.tests.endpoints_ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.ter_genrest.tests.utils.PayloadDataRequestREST;
import fr.amu.ter_genrest.tests.utils.RequestsHelper;
import fr.amu.ter_genrest.tests.utils.RequestsHelper.ResponseJsonObject;
import junit.framework.Assert;

public class ActuatorRESTControllerTEST {


	private long idUser;

	private long idCorridor;
	private long idRoom;

	@Before
	public void setUp() throws IOException {
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
		long idFloor = response.getPayload().getJsonNumber("id").longValue();

		//ADD MOTHER ROOM
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/motherRooms", PayloadDataRequestREST.jsonPayloadRequestMotherRoom());
		long idMotherRoom = response.getPayload().getJsonNumber("id").longValue();

		//ADD ROOM
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/motherRooms/" + idMotherRoom + "/rooms", PayloadDataRequestREST.jsonPayloadRequestRoom());
		idRoom = response.getPayload().getJsonNumber("id").longValue();

		//ADD CORRIDOR
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/motherRooms/" + idMotherRoom + "/corridors", PayloadDataRequestREST.jsonPayloadRequestCorridor());
		idCorridor = response.getPayload().getJsonNumber("id").longValue();
	}

	@After
	public void tearDown() throws IOException {
		RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/users/" + idUser);
	}

	
	@Test
	public void testCRUDCorridors() throws IOException { 
		//ADD

		JsonObject payload =  Json.createObjectBuilder().add("latitude", 13L)
				.add("longitude", 12L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/actuators", payload);

		assertEquals(201, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));

		assertFalse(response.getPayload().containsKey("latitude"));
		assertFalse(response.getPayload().containsKey("longitude"));
		assertFalse(response.getPayload().containsKey("model"));
		assertFalse(response.getPayload().containsKey("brand"));
		assertFalse(response.getPayload().containsKey("reference"));
		assertFalse(response.getPayload().containsKey("state"));

		long idActuator = response.getPayload().getJsonNumber("id").longValue();	

		//UPDATE

		payload =  Json.createObjectBuilder().add("latitude", 14L)
				.add("longitude", 15L)
				.add("model", "modelU")
				.add("brand", "brandU")
				.add("reference", "refU")
				.add("state", "OFFU")
				.build();

		response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/actuators/" + idActuator, payload);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("latitude"));
		assertTrue(response.getPayload().containsKey("longitude"));
		assertTrue(response.getPayload().containsKey("model"));
		assertTrue(response.getPayload().containsKey("brand"));
		assertTrue(response.getPayload().containsKey("reference"));
		assertTrue(response.getPayload().containsKey("state"));

		assertEquals(idActuator, response.getPayload().getJsonNumber("id").longValue());
		assertEquals(14L, response.getPayload().getJsonNumber("latitude").longValue());
		assertEquals(15L, response.getPayload().getJsonNumber("longitude").longValue());
		assertEquals("modelU", response.getPayload().getString("model"));
		assertEquals("brandU", response.getPayload().getString("brand"));
		assertEquals("refU", response.getPayload().getString("reference"));
		assertEquals("OFFU", response.getPayload().getString("state"));

		//FIND
		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/actuators/" + idActuator);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("latitude"));
		assertTrue(response.getPayload().containsKey("longitude"));
		assertTrue(response.getPayload().containsKey("model"));
		assertTrue(response.getPayload().containsKey("brand"));
		assertTrue(response.getPayload().containsKey("reference"));
		assertTrue(response.getPayload().containsKey("state"));

		assertEquals(idActuator, response.getPayload().getJsonNumber("id").longValue());
		assertEquals(14L, response.getPayload().getJsonNumber("latitude").longValue());
		assertEquals(15L, response.getPayload().getJsonNumber("longitude").longValue());
		assertEquals("modelU", response.getPayload().getString("model"));
		assertEquals("brandU", response.getPayload().getString("brand"));
		assertEquals("refU", response.getPayload().getString("reference"));
		assertEquals("OFFU", response.getPayload().getString("state"));

		//DELETE
		response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/actuators/" + idActuator);
		assertEquals(200, response.getResponseCode());

		//FIND
		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/actuators/" + idActuator);
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void testCRUDRoom() throws IOException {
		//ADD

		JsonObject payload =  Json.createObjectBuilder().add("latitude", 13L)
				.add("longitude", 12L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/actuators", payload);

		assertEquals(201, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));

		assertFalse(response.getPayload().containsKey("latitude"));
		assertFalse(response.getPayload().containsKey("longitude"));
		assertFalse(response.getPayload().containsKey("model"));
		assertFalse(response.getPayload().containsKey("brand"));
		assertFalse(response.getPayload().containsKey("reference"));
		assertFalse(response.getPayload().containsKey("state"));

		long idActuator = response.getPayload().getJsonNumber("id").longValue();	

		//UPDATE

		payload =  Json.createObjectBuilder().add("latitude", 14L)
				.add("longitude", 15L)
				.add("model", "modelU")
				.add("brand", "brandU")
				.add("reference", "refU")
				.add("state", "OFFU")
				.build();

		response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/actuators/" + idActuator, payload);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("latitude"));
		assertTrue(response.getPayload().containsKey("longitude"));
		assertTrue(response.getPayload().containsKey("model"));
		assertTrue(response.getPayload().containsKey("brand"));
		assertTrue(response.getPayload().containsKey("reference"));
		assertTrue(response.getPayload().containsKey("state"));

		assertEquals(idActuator, response.getPayload().getJsonNumber("id").longValue());
		assertEquals(14L, response.getPayload().getJsonNumber("latitude").longValue());
		assertEquals(15L, response.getPayload().getJsonNumber("longitude").longValue());
		assertEquals("modelU", response.getPayload().getString("model"));
		assertEquals("brandU", response.getPayload().getString("brand"));
		assertEquals("refU", response.getPayload().getString("reference"));
		assertEquals("OFFU", response.getPayload().getString("state"));

		//FIND

		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/actuators/" + idActuator);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("latitude"));
		assertTrue(response.getPayload().containsKey("longitude"));
		assertTrue(response.getPayload().containsKey("model"));
		assertTrue(response.getPayload().containsKey("brand"));
		assertTrue(response.getPayload().containsKey("reference"));
		assertTrue(response.getPayload().containsKey("state"));

		assertEquals(idActuator, response.getPayload().getJsonNumber("id").longValue());
		assertEquals(14L, response.getPayload().getJsonNumber("latitude").longValue());
		assertEquals(15L, response.getPayload().getJsonNumber("longitude").longValue());
		assertEquals("modelU", response.getPayload().getString("model"));
		assertEquals("brandU", response.getPayload().getString("brand"));
		assertEquals("refU", response.getPayload().getString("reference"));
		assertEquals("OFFU", response.getPayload().getString("state"));

		//DELETE

		response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/actuators/" + idActuator);
		assertEquals(200, response.getResponseCode());

		//FIND
		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/actuators/" + idActuator);
		assertEquals(404, response.getResponseCode());
	}

	
	@Test
	public void getActuatorWithUnknowID() throws IOException {
		ResponseJsonObject response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/actuators/9999");
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void updateActuatorWithUnknowID() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/actuators/9999", PayloadDataRequestREST.jsonPayloadRequestActuator());
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void deleteActuatorWithUnknowActuatorIDInRoom() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/actuators/9999");
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void deleteActuatorWithUnknowActuatorIDInCorridor() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/actuators/9999");
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void deleteActuatorWithUnknowRoomID() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/rooms/99999/actuators/9999");
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void deleteActuatorWithUnknowCorridorID() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/corridors/99999/actuators/9999");
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void createActuatorWithUnknowRoomID() throws IOException {
		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/9999/actuators/",
				PayloadDataRequestREST.jsonPayloadRequestActuator());
		assertEquals(404, response.getResponseCode());
	}
	
	@Test
	public void createActuatorWithUnknowCorridorID() throws IOException {
		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/9999/actuators/",
				PayloadDataRequestREST.jsonPayloadRequestActuator());
		assertEquals(404, response.getResponseCode());
	}
	
	@Test
	public void updateActuatorWithNothing() throws IOException {	
		JsonObject payload =  Json.createObjectBuilder().add("latitude", 13L)
				.add("longitude", 12L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/actuators", payload);

		long idActuator = response.getPayload().getJsonNumber("id").longValue();	

		//UPDATE

		payload =  Json.createObjectBuilder().build();

		response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/actuators/" + idActuator, payload);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("latitude"));
		assertTrue(response.getPayload().containsKey("longitude"));
		assertTrue(response.getPayload().containsKey("model"));
		assertTrue(response.getPayload().containsKey("brand"));
		assertTrue(response.getPayload().containsKey("reference"));
		assertTrue(response.getPayload().containsKey("state"));

		assertEquals(idActuator, response.getPayload().getJsonNumber("id").longValue());
		assertEquals(13L, response.getPayload().getJsonNumber("latitude").longValue());
		assertEquals(12L, response.getPayload().getJsonNumber("longitude").longValue());
		assertEquals("model", response.getPayload().getString("model"));
		assertEquals("brand", response.getPayload().getString("brand"));
		assertEquals("ref", response.getPayload().getString("reference"));
		assertEquals("OFF", response.getPayload().getString("state"));
	}
	
	

	@Test
	public void createActuatorNullLatitudeInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/actuators", payload);
		
		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createActuatorNullLongitudeInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/actuators", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createActuatorNullModeInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/actuators", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createActuatorNullBrandInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/actuators", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createActuatorNullReferenceInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/actuators", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createActuatorNullStateInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/actuators", payload);

		assertEquals(400, response.getResponseCode());
	}


	@Test
	public void createActuatorNullLatitudeInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/actuators", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createActuatorNullLongitudeInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/actuators", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createActuatorNullModeInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/actuators", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createActuatorNullBrandInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/actuators", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createActuatorNullReferenceInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/actuators", payload);

		assertEquals(400, response.getResponseCode());

	}

	@Test
	public void createActuatorNullStateInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/actuators", payload);

		assertEquals(400, response.getResponseCode());
	}

}
