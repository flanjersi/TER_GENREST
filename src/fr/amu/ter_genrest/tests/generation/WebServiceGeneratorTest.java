package fr.amu.ter_genrest.tests.generation;

import java.io.IOException;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.amu.ter_genrest.tests.utils.PayloadDataRequestREST;
import fr.amu.ter_genrest.tests.utils.RequestsHelper;
import fr.amu.ter_genrest.tests.utils.RequestsHelper.ResponseJsonObject;

public class WebServiceGeneratorTest {

	private long idUser;

	private long idConfiguration;
	private long idRoom;
	long idProject;
	
	private static final String URL_ROOT_LANGUAGE = "http://localhost:8090/terGENREST/api/language/";
	private static final String URL_ROOT_CONFIGURATION = "http://localhost:8090/terGENREST/api/configurations/";
	private static final String URL_ROOT_OPERATING_SYSTEM = "http://localhost:8090/terGENREST/api/operatingSystem/";
	private long idLanguage;
	
	private long idOperatingSystem;


	@Before
	public void setUp() throws IOException {
		//ADD USER
		JsonObject payloadRequest = Json.createObjectBuilder().add("email", "medem4991@gmail.com")
		.add("firstName", "leo")
		.add("lastName", "michel")
		.add("password", "leoa121")
		.build();
		
		ResponseJsonObject resp = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users", payloadRequest);
		idUser = resp.getPayload().getJsonNumber("id").longValue();

		//ADD PROJECT
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users/" + idUser + "/projects", PayloadDataRequestREST.jsonPayloadRequestProject());
		idProject = response.getPayload().getJsonNumber("id").longValue();

		//ADD BUILDING
		JsonObject jsonPay= Json.createObjectBuilder()
				.add("type", "Batiment")
				.add("address", 
						Json.createObjectBuilder()
							.add("city", "Marseille")
							.add("street", "street")
							.add("country", "France")
							.build())
				.build();
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/projects/" + idProject + "/buildings", jsonPay);
		long idBuilding = response.getPayload().getJsonNumber("id").longValue();

		//ADD FLOOR
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/buildings/" + idBuilding + "/floors", PayloadDataRequestREST.jsonPayloadRequestFloor());
		long idFloor = response.getPayload().getJsonNumber("id").longValue();

		//ADD MOTHER ROOM
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/floors/" + idFloor + "/motherRooms", PayloadDataRequestREST.jsonPayloadRequestZone());
		long idMotherRoom = response.getPayload().getJsonNumber("id").longValue();

		//ADD ROOM
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/motherRooms/" + idMotherRoom + "/rooms", PayloadDataRequestREST.jsonPayloadRequestRoom());
		idRoom = response.getPayload().getJsonNumber("id").longValue();

		//ADD CORRIDOR
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/motherRooms/" + idMotherRoom + "/corridors", PayloadDataRequestREST.jsonPayloadRequestCorridor());
		Long idCorridor = response.getPayload().getJsonNumber("id").longValue();
		
		// ADD actuator to corridor
		JsonObject payload =  Json.createObjectBuilder().add("latitude", 13L)
				.add("longitude", 12L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.build();

		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/actuators", payload);

		// ADD sensor to corridor
		 payload =  Json.createObjectBuilder().add("latitude", 13L)
				.add("longitude", 12L)
				.add("model", "model")
				.add("brand", "brand")
				.add("reference", "ref")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();

		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/corridors/" + idCorridor + "/sensors", payload);
		
		// ADD LANGUAGE
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "JavaScript")
				.build();
		response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE, jsonPayloadRequest);
 
		idLanguage = response.getPayload().getJsonNumber("id").longValue();
		
		 jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "nodejs-express")
				.add("pathFolder", "nodejs-express")
				.add("description", "description")
				.build();

		response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		 jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "Windows 10")
				.add("nameFolder", "windows")
				.build();
		 idConfiguration = response.getPayload().getJsonNumber("id").longValue();
		 
		 response = RequestsHelper.httpPUT(URL_ROOT_OPERATING_SYSTEM, jsonPayloadRequest);		
		 
		  idOperatingSystem = response.getPayload().getJsonNumber("id").longValue();  
	}

	@After
	public void tearDown() throws IOException {
		RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/users/" + idUser);
//		RequestsHelper.httpDELETE(URL_ROOT_LANGUAGE + idLanguage);
//		RequestsHelper.httpDELETE(URL_ROOT_OPERATING_SYSTEM + idOperatingSystem);		
	}
	
	@Test
	public void testGenerateWS() throws IOException {
		System.out.println("http://localhost:8090/terGENREST/api/deploiement?project="+idProject+"&language="+idLanguage+"&configuration="+idConfiguration+"&operatingSystem="+idOperatingSystem);
		//RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/deploiement?project="+idProject+"&language="+idLanguage+"&configuration="+idConfiguration+"&operatingSystem="+idOperatingSystem);
		//idUser = response.getPayload().getJsonNumber("id").longValue();
		
	}

}
