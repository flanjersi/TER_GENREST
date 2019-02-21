package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.tests.utils.PayloadDataRequestREST;
import fr.amu.terGENREST.tests.utils.RequestsHelper;
import fr.amu.terGENREST.tests.utils.RequestsHelper.ResponseJsonObject;
import junit.framework.Assert;

public class SensorRESTControllerTest {

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
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/sensors", payload);

		assertEquals(201, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));

		assertFalse(response.getPayload().containsKey("latitude"));
		assertFalse(response.getPayload().containsKey("longitude"));
		assertFalse(response.getPayload().containsKey("model"));
		assertFalse(response.getPayload().containsKey("brand"));
		assertFalse(response.getPayload().containsKey("reference"));
		assertFalse(response.getPayload().containsKey("state"));
		assertFalse(response.getPayload().containsKey("unitData"));

		long idSensor = response.getPayload().getJsonNumber("id").longValue();	

		//UPDATE

		payload =  Json.createObjectBuilder().add("latitude", 14L)
				.add("longitude", 15L)
				.add("model", "modelU")
				.add("brand", "brandU")
				.add("reference", "refU")
				.add("state", "OFFU")
				.add("unitData", "CelsiusU")
				.build();

		response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/sensors/" + idSensor, payload);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("latitude"));
		assertTrue(response.getPayload().containsKey("longitude"));
		assertTrue(response.getPayload().containsKey("model"));
		assertTrue(response.getPayload().containsKey("brand"));
		assertTrue(response.getPayload().containsKey("reference"));
		assertTrue(response.getPayload().containsKey("state"));
		assertTrue(response.getPayload().containsKey("unitData"));

		assertEquals(idSensor, response.getPayload().getJsonNumber("id").longValue());
		assertEquals(14L, response.getPayload().getJsonNumber("latitude").longValue());
		assertEquals(15L, response.getPayload().getJsonNumber("longitude").longValue());
		assertEquals("modelU", response.getPayload().getString("model"));
		assertEquals("brandU", response.getPayload().getString("brand"));
		assertEquals("refU", response.getPayload().getString("reference"));
		assertEquals("OFFU", response.getPayload().getString("state"));
		assertEquals("CelsiusU", response.getPayload().getString("unitData"));

		//FIND
		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/sensors/" + idSensor);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("latitude"));
		assertTrue(response.getPayload().containsKey("longitude"));
		assertTrue(response.getPayload().containsKey("model"));
		assertTrue(response.getPayload().containsKey("brand"));
		assertTrue(response.getPayload().containsKey("reference"));
		assertTrue(response.getPayload().containsKey("state"));
		assertTrue(response.getPayload().containsKey("unitData"));

		assertEquals(idSensor, response.getPayload().getJsonNumber("id").longValue());
		assertEquals(14L, response.getPayload().getJsonNumber("latitude").longValue());
		assertEquals(15L, response.getPayload().getJsonNumber("longitude").longValue());
		assertEquals("modelU", response.getPayload().getString("model"));
		assertEquals("brandU", response.getPayload().getString("brand"));
		assertEquals("refU", response.getPayload().getString("reference"));
		assertEquals("OFFU", response.getPayload().getString("state"));
		assertEquals("CelsiusU", response.getPayload().getString("unitData"));

		//DELETE
		response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/sensors/" + idSensor);
		assertEquals(200, response.getResponseCode());

		//FIND
		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/sensors/" + idSensor);
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
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/sensors", payload);

		assertEquals(201, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));

		assertFalse(response.getPayload().containsKey("latitude"));
		assertFalse(response.getPayload().containsKey("longitude"));
		assertFalse(response.getPayload().containsKey("model"));
		assertFalse(response.getPayload().containsKey("brand"));
		assertFalse(response.getPayload().containsKey("reference"));
		assertFalse(response.getPayload().containsKey("state"));
		assertFalse(response.getPayload().containsKey("unitData"));

		long idSensor = response.getPayload().getJsonNumber("id").longValue();	

		//UPDATE

		payload =  Json.createObjectBuilder().add("latitude", 14L)
				.add("longitude", 15L)
				.add("model", "modelU")
				.add("brand", "brandU")
				.add("reference", "refU")
				.add("state", "OFFU")
				.add("unitData", "CelsiusU")
				.build();

		response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/sensors/" + idSensor, payload);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("latitude"));
		assertTrue(response.getPayload().containsKey("longitude"));
		assertTrue(response.getPayload().containsKey("model"));
		assertTrue(response.getPayload().containsKey("brand"));
		assertTrue(response.getPayload().containsKey("reference"));
		assertTrue(response.getPayload().containsKey("state"));
		assertTrue(response.getPayload().containsKey("unitData"));

		assertEquals(idSensor, response.getPayload().getJsonNumber("id").longValue());
		assertEquals(14L, response.getPayload().getJsonNumber("latitude").longValue());
		assertEquals(15L, response.getPayload().getJsonNumber("longitude").longValue());
		assertEquals("modelU", response.getPayload().getString("model"));
		assertEquals("brandU", response.getPayload().getString("brand"));
		assertEquals("refU", response.getPayload().getString("reference"));
		assertEquals("OFFU", response.getPayload().getString("state"));
		assertEquals("CelsiusU", response.getPayload().getString("unitData"));

		//FIND

		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/sensors/" + idSensor);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("latitude"));
		assertTrue(response.getPayload().containsKey("longitude"));
		assertTrue(response.getPayload().containsKey("model"));
		assertTrue(response.getPayload().containsKey("brand"));
		assertTrue(response.getPayload().containsKey("reference"));
		assertTrue(response.getPayload().containsKey("state"));
		assertTrue(response.getPayload().containsKey("unitData"));

		assertEquals(idSensor, response.getPayload().getJsonNumber("id").longValue());
		assertEquals(14L, response.getPayload().getJsonNumber("latitude").longValue());
		assertEquals(15L, response.getPayload().getJsonNumber("longitude").longValue());
		assertEquals("modelU", response.getPayload().getString("model"));
		assertEquals("brandU", response.getPayload().getString("brand"));
		assertEquals("refU", response.getPayload().getString("reference"));
		assertEquals("OFFU", response.getPayload().getString("state"));
		assertEquals("CelsiusU", response.getPayload().getString("unitData"));

		//DELETE

		response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/sensors/" + idSensor);
		assertEquals(200, response.getResponseCode());

		//FIND
		response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/sensors/" + idSensor);
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void getSensorWithUnknowID() throws IOException {
		ResponseJsonObject response = RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/sensors/9999");
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void updateSensorWithUnknowID() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/sensors/9999", PayloadDataRequestREST.jsonPayloadRequestSensor());
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void deleteSensorWithUnknowSensorIDInRoom() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/sensors/9999");
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void deleteSensorWithUnknowSensorIDInCorridor() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/sensors/9999");
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void deleteSensorWithUnknowRoomID() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/rooms/99999/sensors/9999");
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void deleteSensorWithUnknowCorridorID() throws IOException {	
		ResponseJsonObject response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/corridors/99999/sensors/9999");
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void createSensorWithUnknowRoomID() throws IOException {
		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/9999/sensors/",
				PayloadDataRequestREST.jsonPayloadRequestSensor());
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void createSensorWithUnknowCorridorID() throws IOException {
		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/9999/sensors/",
				PayloadDataRequestREST.jsonPayloadRequestSensor());
		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void updateSensorWithNothing() throws IOException {	
		JsonObject payload =  Json.createObjectBuilder().add("latitude", 13L)
				.add("longitude", 12L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/sensors", payload);

		long idSensor = response.getPayload().getJsonNumber("id").longValue();	

		//UPDATE

		payload =  Json.createObjectBuilder().build();

		response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/sensors/" + idSensor, payload);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("latitude"));
		assertTrue(response.getPayload().containsKey("longitude"));
		assertTrue(response.getPayload().containsKey("model"));
		assertTrue(response.getPayload().containsKey("brand"));
		assertTrue(response.getPayload().containsKey("reference"));
		assertTrue(response.getPayload().containsKey("state"));
		assertTrue(response.getPayload().containsKey("unitData"));

		assertEquals(idSensor, response.getPayload().getJsonNumber("id").longValue());
		assertEquals(13L, response.getPayload().getJsonNumber("latitude").longValue());
		assertEquals(12L, response.getPayload().getJsonNumber("longitude").longValue());
		assertEquals("model", response.getPayload().getString("model"));
		assertEquals("brand", response.getPayload().getString("brand"));
		assertEquals("ref", response.getPayload().getString("reference"));
		assertEquals("OFF", response.getPayload().getString("state"));
		assertEquals("Celsius", response.getPayload().getString("unitData"));
	}



	@Test
	public void createSensorNullLatitudeInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/sensors", payload);
		
		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullLongitudeInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullModelInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullBrandInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("reference", "ref")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullReferenceInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullStateInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullUnitDataInRoom() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/rooms/" + idRoom + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}



	@Test
	public void createSensorNullLatitudeInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullLongitudeInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullModelInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idRoom + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullBrandInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("reference", "ref")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullReferenceInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullStateInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("unitData", "Celsius")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createSensorNullUnitDataInCorridor() throws IOException {
		JsonObject payload =  Json.createObjectBuilder()
				.add("longitude", 12L)
				.add("latitude", 13L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/sensors", payload);

		assertEquals(400, response.getResponseCode());
	}

}