package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.tests.utils.PayloadDataRequestREST;
import fr.amu.terGENREST.tests.utils.RequestsHelper;

public class CorridorRESTControllerTest {

	private static final String URL_ROOT_USER = "http://localhost:8090/terGENREST/api/users/";
	private static final String URL_ROOT_PROJECT = "http://localhost:8090/terGENREST/api/projects/";
	private long idUser;
	private long idProject;
	private long idBuilding;
	private long idFloor;
	private long idMotherRoom;
	
	@Before
	public void setUp() throws Exception {
		//ADD USER
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users", PayloadDataRequestREST.jsonPayloadRequestUser());
		idUser = response.getPayload().getJsonNumber("id").longValue();

		//ADD PROJECT
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users/" + idUser + "/projects", PayloadDataRequestREST.jsonPayloadRequestProject());
		 idProject = response.getPayload().getJsonNumber("id").longValue();

		//ADD BUILDING
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/projects/" + idProject + "/buildings", PayloadDataRequestREST.jsonPayloadRequestBuilding());
		 idBuilding = response.getPayload().getJsonNumber("id").longValue();

		//ADD FLOOR
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/buildings/" + idBuilding + "/floors", PayloadDataRequestREST.jsonPayloadRequestFloor());
		 idFloor = response.getPayload().getJsonNumber("id").longValue();

		//ADD MOTHER ROOM
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/motherRooms", PayloadDataRequestREST.jsonPayloadRequestMotherRoom());
		 idMotherRoom = response.getPayload().getJsonNumber("id").longValue();
	}
	
	@Test
	public void testCRUDUserRest() throws IOException {
		// ADD CORRIDOR
		JsonObject jsonPayloadRequestCorridor = Json.createObjectBuilder().add("numberCorridor", 6).build();
		RequestsHelper.ResponseJsonObject response = 
				RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/corridors", 
				jsonPayloadRequestCorridor);
		
		assertEquals(200, response.getResponseCode()); 
		
		assertTrue(response.getPayload().containsKey("id"));
		assertFalse(response.getPayload().containsKey("numberCorridor"));
		
		long idCorridor = response.getPayload().getJsonNumber("id").longValue();
		
		// update Corridor
		
//		jsonPayloadRequestCorridor = Json.createObjectBuilder().add("numberCorridor", 8).build();
//		response = RequestsHelper.httpPOST("http://localhost:8090/terGENREST/api/motherRooms/" + idMotherRoom + "/corridors"+idCorridor, 
//						jsonPayloadRequestCorridor);
//		assertEquals(200, response.getResponseCode()); 
//		
//		assertTrue(response.getPayload().containsKey("id"));
//		assertTrue(response.getPayload().containsKey("numberCorridor"));
//		
//		assertEquals(8, response.getPayload().getString("numberCorridor"));
				 
		// find Corridor by id
		
		RequestsHelper.ResponseJsonObject responseObject = 
				RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/corridors/"+idCorridor);

		assertEquals(200, responseObject.getResponseCode()); 
		assertTrue(responseObject.getPayload().containsKey("numberCorridor"));
		//assertEquals(6, response.getPayload().getJsonNumber("numberCorridor").intValue());
		
		// Delete Corridor
		
		response = RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/corridors/"+idCorridor);
		assertEquals(200, response.getResponseCode());
	}
//	
//	@Test
//	public void getCorridorWithUnknowID() throws IOException {
//		RequestsHelper.ResponseJsonObject response = RequestsHelper
//				.httpGetJsonObject(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"+"99999999");
//		assertEquals(404, response.getResponseCode());
//	}
//	
//	@Test
//	public void deleteCorridorWithUnknowID() throws IOException {
//		RequestsHelper.ResponseJsonObject response = 
//				RequestsHelper.httpDELETE(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/" + "99999999");
//		assertEquals(404, response.getResponseCode());
//	}
//	
//	@Test
//	public void UpdateUserWithUnknowID() throws IOException {
//		JsonObject jsonPayloadRequestCorridor = Json.createObjectBuilder().add("numberCorridor", 1).build();
//		RequestsHelper.ResponseJsonObject response = 
//				RequestsHelper.httpPOST(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"+9999999, 
//						jsonPayloadRequestCorridor);
//		assertEquals(404, response.getResponseCode());
//	}
//
//	@Test
//	public void testCreateCorridorWithNullEmail() throws IOException {
//		JsonObject jsonPayloadRequest = Json.createObjectBuilder().build();
//		RequestsHelper.ResponseJsonObject response = 
//				RequestsHelper.httpPUT(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"
//						, jsonPayloadRequest);
//		assertEquals(400, response.getResponseCode());
//	}
//
//	@Test
//	public void findAllCorridors() throws IOException {
//		RequestsHelper.ResponseJsonArray response = 
//				RequestsHelper.httpGetJsonArray(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/");
//		assertEquals(200, response.getResponseCode());
//	}
//	
//	@Test
//	public void findActuators() throws IOException {
//		//ADD ACTUATOR
//		double longitude = 18.3;
//		double lattitude = 20.0;
//		JsonObject jsonPayloadRequestActuator = Json.createObjectBuilder().add("latitude", lattitude)
//													.add("longitude", longitude)
//													.add("model", "AS12")
//													.add("brand", "Brand20")
//													.add("reference", "Ref00060")
//													.add("state", "ON")
//													.build();
//		RequestsHelper.ResponseJsonObject response = 
//				RequestsHelper.httpPUT(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"
//						, jsonPayloadRequestActuator);
//		long idCorridor = response.getPayload().getJsonNumber("id").longValue();
//		assertEquals(200, response.getResponseCode());
//		
//		RequestsHelper.ResponseJsonArray responseArray = RequestsHelper
//				.httpGetJsonArray(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"+idCorridor+"/actuators/");
//		long idActuator = response.getPayload().getJsonNumber("id").longValue();
//		assertEquals(200, responseArray.getResponseCode());
//		
//		//Delete Corridor and Actuator
//
//		response = RequestsHelper.httpDELETE(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"+idCorridor);
//		response = RequestsHelper.httpDELETE(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"+idCorridor+"/actuators/"+idActuator);
//	}
//	
//	@Test
//	public void testCreateActuators() throws IOException {
//		
//		// ADD CORRIDOR
//		JsonObject jsonPayloadRequestCorridor = Json.createObjectBuilder().add("numberCorridor", 6).build();
//		RequestsHelper.ResponseJsonObject response = 
//				RequestsHelper.httpPUT(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/", 
//				jsonPayloadRequestCorridor);
//		
//		assertEquals(200, response.getResponseCode()); 
//		Long idCorridor = response.getPayload().getJsonNumber("id").longValue();
//		
//		// ADD ACTUATOR
//		double longitude = 18.3;
//		double lattitude = 20.0;
//		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("latitude", lattitude)
//													.add("longitude", longitude)
//													.add("model", "AS12")
//													.add("brand", "Brand20")
//													.add("reference", "Ref00060")
//													.add("state", "ON")
//													.build();
//		response = RequestsHelper
//			.httpPUT(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"+idCorridor+"/actuators/"
//						, jsonPayloadRequest);
//
//		long idActutator = response.getPayload().getJsonNumber("id").longValue();
//		assertEquals(200, response.getResponseCode());
//		
//		//Delete CORRIDOR and ACTUATOR
//
//		response = RequestsHelper.httpDELETE(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"+idCorridor);
//		response = RequestsHelper.httpDELETE(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"+idCorridor+"/actuators/"+idActutator);
//	}
//	
//	@Test
//	public void updateNothing() throws IOException {
//		// ADD CORRIDOR
//		JsonObject jsonPayloadRequestCorridor = Json.createObjectBuilder().add("numberCorridor", 6).build();
//		RequestsHelper.ResponseJsonObject response = 
//					RequestsHelper.httpPUT(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/", 
//					jsonPayloadRequestCorridor);
//				
//		assertEquals(200, response.getResponseCode()); 
//		Long idCorridor = response.getPayload().getJsonNumber("id").longValue();
//
//		//Update CORRIDOR
//		
//		jsonPayloadRequestCorridor = Json.createObjectBuilder().build();
//		response = RequestsHelper.httpPOST(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"+idCorridor, 
//						jsonPayloadRequestCorridor);
//		assertEquals(200, response.getResponseCode()); 
//		
//		assertTrue(response.getPayload().containsKey("id"));
//		assertTrue(response.getPayload().containsKey("numberCorridor"));
//		
//		assertEquals(6, response.getPayload().getString("numberCorridor"));
//
//		idCorridor = response.getPayload().getJsonNumber("id").longValue();
//	
//		//Delete CORRIDOR
//
//		response = RequestsHelper.httpDELETE(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/"+idFloor+"/corridors/"+idCorridor);
//	}
//	
	
}
