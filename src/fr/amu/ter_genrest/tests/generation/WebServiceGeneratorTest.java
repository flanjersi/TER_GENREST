package fr.amu.ter_genrest.tests.generation;

import java.io.IOException;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.ter_genrest.services.zip.ZipFolder;
import fr.amu.ter_genrest.tests.utils.PayloadDataRequestREST;
import fr.amu.ter_genrest.tests.utils.RequestsHelper;

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
	
	@EJB
	ZipFolder zipFolder;
	
	@Before
	public void setUp() throws IOException {
		//ADD USER
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users", PayloadDataRequestREST.jsonPayloadRequestUser());
		idUser = response.getPayload().getJsonNumber("id").longValue();

		//ADD PROJECT
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users/" + idUser + "/projects", PayloadDataRequestREST.jsonPayloadRequestProject());
		idProject = response.getPayload().getJsonNumber("id").longValue();

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
		Long idCorridor = response.getPayload().getJsonNumber("id").longValue();
		
		
		// ADD LANGUAGE
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "Javascript")
				.build();
		response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE, jsonPayloadRequest);

		idLanguage = response.getPayload().getJsonNumber("id").longValue();
		
		 jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "nodejs-express")
				.add("pathFolder", "path")
				.add("description", "description")
				.build();

		response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		 jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "Ubuntu")
				.add("nameFolder", "ubuntu")
				.build();
		 idConfiguration = response.getPayload().getJsonNumber("id").longValue();
		 
		 response = RequestsHelper.httpPUT(URL_ROOT_OPERATING_SYSTEM, jsonPayloadRequest);		
		 
		  idOperatingSystem = response.getPayload().getJsonNumber("id").longValue();  
	}

	@After
	public void tearDown() throws IOException {
		RequestsHelper.httpDELETE("http://localhost:8090/terGENREST/api/users/" + idUser);
		RequestsHelper.httpDELETE(URL_ROOT_LANGUAGE + idLanguage);
		RequestsHelper.httpDELETE(URL_ROOT_OPERATING_SYSTEM + idOperatingSystem);		
	}
	
	@Test
	public void testGenerateWS() throws IOException {
		RequestsHelper.httpGetJsonObject("http://localhost:8090/terGENREST/api/deploiement?project="+idProject+"&language="+idLanguage+"&configuration="+idConfiguration+"&operatingSystem="+idOperatingSystem);
		//idUser = response.getPayload().getJsonNumber("id").longValue();
		
		// ZIP
		String path = System.getProperty("user.dir");
	     System.out.println("Zip in progress ...");
	     // Use the following paths for windows
	     String folderToZip = "C:\\Users\\moham\\eclipse\\jee-2018-09\\eclipse\\Generated";
	     String zipName = "C:\\Users\\moham\\eclipse\\jee-2018-09\\eclipse\\Generated.zip";
	     
	     // Linux/mac paths
//	     String folderToZip = "/Users/jj/test";
//	     String zipName = "/Users/jj/test.zip";
	     //zf.zipFolder(Paths.get(folderToZip), Paths.get(zipName));
	     
	     try {
			zipFolder.zipFolder(Paths.get(folderToZip), Paths.get(zipName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	     System.out.println("Zip done !");
	}

}
