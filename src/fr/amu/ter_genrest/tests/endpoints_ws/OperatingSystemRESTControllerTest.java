package fr.amu.ter_genrest.tests.endpoints_ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.amu.ter_genrest.tests.utils.PayloadDataRequestREST;
import fr.amu.ter_genrest.tests.utils.RequestsHelper;
import fr.amu.ter_genrest.tests.utils.Utils;
import fr.amu.ter_genrest.tests.utils.RequestsHelper.ResponseJsonArray;
import fr.amu.ter_genrest.tests.utils.RequestsHelper.ResponseJsonObject;
import junit.framework.Assert;

public class OperatingSystemRESTControllerTest {

	private static final String URL_ROOT_OPERATING_SYSTEM = "http://localhost:8090/terGENREST/api/operatingSystem/";
	private static final String URL_ROOT_LANGUAGE = "http://localhost:8090/terGENREST/api/language/";
	private static final String URL_ROOT_CONFIGURATION = "http://localhost:8090/terGENREST/api/configurations/";

	private long idLanguage;
	private long idConfiguration;
	
	@Before
	public void setup() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE, PayloadDataRequestREST.jsonPayloadRequestLanguage());

		idLanguage = response.getPayload().getJsonNumber("id").longValue();

		response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", PayloadDataRequestREST.jsonPayloadRequestConfiguration());

		idConfiguration = response.getPayload().getJsonNumber("id").longValue();
	}

	@After
	public void tearDown() throws IOException {
		RequestsHelper.httpDELETE(URL_ROOT_LANGUAGE + idLanguage);
	}
	
	@Test
	public void testCRUD() throws IOException {

		//Add data
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "Ubuntu")
				.add("nameFolder", "ubuntu")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_CONFIGURATION + idConfiguration + "/operatingsSystem", jsonPayloadRequest);		

		assertEquals(201, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertFalse(response.getPayload().containsKey("name"));

		long id = response.getPayload().getJsonNumber("id").longValue();

		//Update data
		jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "UbuntuUpdate")
				.add("nameFolder", "ubuntuUpdate")
				.build();

		response = RequestsHelper.httpPOST(URL_ROOT_OPERATING_SYSTEM + id, jsonPayloadRequest);		

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("name"));
		assertTrue(response.getPayload().containsKey("nameFolder"));

		assertEquals("UbuntuUpdate", response.getPayload().getString("name"));
		assertEquals("ubuntuUpdate", response.getPayload().getString("nameFolder"));

		//Find data
		response = RequestsHelper.httpGetJsonObject(URL_ROOT_OPERATING_SYSTEM + id);		

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("name"));
		assertTrue(response.getPayload().containsKey("nameFolder"));

		assertEquals("UbuntuUpdate", response.getPayload().getString("name"));
		assertEquals("ubuntuUpdate", response.getPayload().getString("nameFolder"));

		//Delete data

		response = RequestsHelper.httpDELETE(URL_ROOT_CONFIGURATION + idConfiguration + "/operatingsSystem/" + id);		

		assertEquals(200, response.getResponseCode());

		//Find data deleted

		response = RequestsHelper.httpGetJsonObject(URL_ROOT_OPERATING_SYSTEM + id);		

		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void findOperatingSystemWithUnknowID() throws IOException {
		ResponseJsonObject response = RequestsHelper.httpGetJsonObject(URL_ROOT_OPERATING_SYSTEM + "99999");		

		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void deleteOperatingSystemWithUnknowID() throws IOException {
		ResponseJsonObject response = RequestsHelper.httpDELETE(URL_ROOT_CONFIGURATION + idConfiguration + "/operatingsSystem/999999");		

		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void createOperatingSystemWithNullName() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "Ubuntu")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_CONFIGURATION + idConfiguration + "/operatingsSystem", jsonPayloadRequest);		

		assertEquals(400, response.getResponseCode());		
	}

	@Test
	public void createOperatingSystemUniqueNameConstraint() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "Ubuntu")
				.add("nameFolder", "ubuntu")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_CONFIGURATION + idConfiguration + "/operatingsSystem", jsonPayloadRequest);		

		long id = response.getPayload().getJsonNumber("id").longValue();
		
		jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "Ubuntu")
				.add("nameFolder", "ubuntu2")
				.build();

		response = RequestsHelper.httpPUT(URL_ROOT_CONFIGURATION + idConfiguration + "/operatingsSystem", jsonPayloadRequest);		

		assertEquals(400, response.getResponseCode());
		
		response = RequestsHelper.httpDELETE(URL_ROOT_OPERATING_SYSTEM + id);
	}


	@Test
	public void createOperatingSystemWithNullNameFolder() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "Ubuntu")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_CONFIGURATION + idConfiguration + "/operatingsSystem", jsonPayloadRequest);		

		assertEquals(400, response.getResponseCode());		
	}

	@Test
	public void createOperatingSystemUniqueNameFolderConstraint() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "Ubuntu")
				.add("nameFolder", "ubuntu")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_CONFIGURATION + idConfiguration + "/operatingsSystem", jsonPayloadRequest);		

		long id = response.getPayload().getJsonNumber("id").longValue();
		
		jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "Ubuntu2")
				.add("nameFolder", "ubuntu")
				.build();

		response = RequestsHelper.httpPUT(URL_ROOT_CONFIGURATION + idConfiguration + "/operatingsSystem", jsonPayloadRequest);		

		assertEquals(400, response.getResponseCode());	
		
		response = RequestsHelper.httpDELETE(URL_ROOT_OPERATING_SYSTEM + id);
	}

	@Test
	public void updateNothingOnOperatingsystem() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "Ubuntu")
				.add("nameFolder", "ubuntu")
				.build();

		ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_CONFIGURATION + idConfiguration + "/operatingsSystem", jsonPayloadRequest);		

		long id = response.getPayload().getJsonNumber("id").longValue();

		//Update data
		jsonPayloadRequest = Json.createObjectBuilder().build();

		response = RequestsHelper.httpPOST(URL_ROOT_OPERATING_SYSTEM + id, jsonPayloadRequest);		

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("name"));
		assertTrue(response.getPayload().containsKey("nameFolder"));

		assertEquals("Ubuntu", response.getPayload().getString("name"));
		assertEquals("ubuntu", response.getPayload().getString("nameFolder"));
		
		response = RequestsHelper.httpDELETE(URL_ROOT_OPERATING_SYSTEM + id);
	}

	@Test
	public void updateOperatingsystemWithUnkonwId() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().build();

		ResponseJsonObject response = RequestsHelper.httpPOST(URL_ROOT_OPERATING_SYSTEM + 99999, jsonPayloadRequest);		

		assertEquals(404, response.getResponseCode());
	}

}
