package fr.amu.terGENREST.tests.endpointsWS;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.tests.utils.RequestHelper;
import fr.amu.terGENREST.tests.utils.RequestsHelper;

public class SensorRESTControllerTest {


	@Before
	public void setUp() throws IOException {
		//ADD USER
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/api/users", RequestHelper.jsonPayloadRequestUser());
		long idUser = response.getPayload().getJsonNumber("id").longValue();

		//ADD PROJECT
		response = RequestsHelper.httpPUT("http://localhost:8090/api/users/" + idUser + "/projects", RequestHelper.jsonPayloadRequestProject());
		long idProject = response.getPayload().getJsonNumber("id").longValue();

		//ADD BUILDING
		response = RequestsHelper.httpPUT("http://localhost:8090/api/projects/" + idProject + "/buildings", RequestHelper.jsonPayloadRequestBuilding());
		long idBuilding = response.getPayload().getJsonNumber("id").longValue();

		//ADD FLOOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/buildings/" + idBuilding + "/floors", RequestHelper.jsonPayloadRequestFloor());
		long idFloor = response.getPayload().getJsonNumber("id").longValue();

		//ADD CORRIDOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/floors/" + idFloor + "/corridors", RequestHelper.jsonPayloadRequestCorridor());
		long idCorridor = response.getPayload().getJsonNumber("id").longValue();

		//ADD SENSOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/corridors/" + idCorridor + "/sensors", RequestHelper.jsonPayloadRequestSensor());
		long idSensor = response.getPayload().getJsonNumber("id").longValue();

		//ADD MOTHER ROOM
		response = RequestsHelper.httpPUT("http://localhost:8090/api/floors/" + idFloor + "/motherRooms", RequestHelper.jsonPayloadRequestMotherRoom());
		long idMotherRoom = response.getPayload().getJsonNumber("id").longValue();

		//ADD ROOM
		response = RequestsHelper.httpPUT("http://localhost:8090/api/motherRooms/" + idMotherRoom + "/rooms", RequestHelper.jsonPayloadRequestRoom());
		long idRoom = response.getPayload().getJsonNumber("id").longValue();

		//ADD SENSOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/rooms/" + idRoom + "/sensors", RequestHelper.jsonPayloadRequestSensor());
		long idSensorInRoom = response.getPayload().getJsonNumber("id").longValue();

		//ADD CORRIDOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/motherRooms/" + idMotherRoom + "/corridors", RequestHelper.jsonPayloadRequestCorridor());
		long idCorridorInMotherRoom = response.getPayload().getJsonNumber("id").longValue();
		
		//ADD SENSOR
		response = RequestsHelper.httpPUT("http://localhost:8090/api/corridors/" + idCorridorInMotherRoom + "/sensors", RequestHelper.jsonPayloadRequestSensor());
		long idSensorInCorridorInMotherRoom = response.getPayload().getJsonNumber("id").longValue();

	}

	@After
	public void tearDown() {

	}

	@Test
	public void testCRUD() {



	}

}
