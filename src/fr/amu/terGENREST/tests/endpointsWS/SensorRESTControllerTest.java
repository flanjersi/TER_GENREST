package fr.amu.terGENREST.tests.endpointsWS;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.tests.utils.PayloadDataRequestREST;
import fr.amu.terGENREST.tests.utils.RequestsHelper;

public class SensorRESTControllerTest {

	private long idUser;
	private long idSensor;
	private long idSensorInCorridorInMotherRoom;
	private long idSensorInRoom;
	
	@Before
	public void setUp() throws IOException {
		//ADD USER
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/api/users", PayloadDataRequestREST.jsonPayloadRequestUser());
		idUser = response.getPayload().getJsonNumber("id").longValue();

		//ADD PROJECT
		response = RequestsHelper.httpPUT("http://localhost:8090/api/users/" + idUser + "/projects", PayloadDataRequestREST.jsonPayloadRequestProject());
		long idProject = response.getPayload().getJsonNumber("id").longValue();

		//ADD BUILDING
		response = RequestsHelper.httpPUT("http://localhost:8090/api/projects/" + idProject + "/buildings", PayloadDataRequestREST.jsonPayloadRequestBuilding());
		long idBuilding = response.getPayload().getJsonNumber("id").longValue();

		//ADD FLOOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/buildings/" + idBuilding + "/floors", PayloadDataRequestREST.jsonPayloadRequestFloor());
		long idFloor = response.getPayload().getJsonNumber("id").longValue();

		//ADD CORRIDOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/floors/" + idFloor + "/corridors", PayloadDataRequestREST.jsonPayloadRequestCorridor());
		long idCorridor = response.getPayload().getJsonNumber("id").longValue();

		//ADD SENSOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/corridors/" + idCorridor + "/sensors", PayloadDataRequestREST.jsonPayloadRequestSensor());
		idSensor = response.getPayload().getJsonNumber("id").longValue();

		//ADD MOTHER ROOM
		response = RequestsHelper.httpPUT("http://localhost:8090/api/floors/" + idFloor + "/motherRooms", PayloadDataRequestREST.jsonPayloadRequestMotherRoom());
		long idMotherRoom = response.getPayload().getJsonNumber("id").longValue();

		//ADD ROOM
		response = RequestsHelper.httpPUT("http://localhost:8090/api/motherRooms/" + idMotherRoom + "/rooms", PayloadDataRequestREST.jsonPayloadRequestRoom());
		long idRoom = response.getPayload().getJsonNumber("id").longValue();

		//ADD SENSOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/rooms/" + idRoom + "/sensors", PayloadDataRequestREST.jsonPayloadRequestSensor());
		idSensorInRoom = response.getPayload().getJsonNumber("id").longValue();

		//ADD CORRIDOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/motherRooms/" + idMotherRoom + "/corridors", PayloadDataRequestREST.jsonPayloadRequestCorridor());
		long idCorridorInMotherRoom = response.getPayload().getJsonNumber("id").longValue();
		
		//ADD SENSOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/corridors/" + idCorridorInMotherRoom + "/sensors", PayloadDataRequestREST.jsonPayloadRequestSensor());
		idSensorInCorridorInMotherRoom = response.getPayload().getJsonNumber("id").longValue();
	}

	@After
	public void tearDown() throws IOException {
		RequestsHelper.httpDELETE("http://localhost:8090/api/users/" + idUser);
	}

	@Test
	public void testCRUD() { 
		
		
	}

}
