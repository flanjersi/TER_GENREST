package fr.amu.ter_genrest.tests.endpoints_ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;

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
import org.junit.Test;

import fr.amu.ter_genrest.tests.utils.PayloadDataRequestREST;
import fr.amu.ter_genrest.tests.utils.RequestsHelper;
import fr.amu.ter_genrest.tests.utils.Utils;

public class ConfigurationManagerControllerRESTTest {

	private static final String URL_ROOT_LANGUAGE = "http://localhost:8090/terGENREST/api/language/";
	private static final String URL_ROOT_CONFIGURATION = "http://localhost:8090/terGENREST/api/configurations/";

	private long idLanguage;

	@Before
	public void setup() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE, PayloadDataRequestREST.jsonPayloadRequestLanguage());

		idLanguage = response.getPayload().getJsonNumber("id").longValue();
	}

	@After
	public void tearDown() throws IOException {
		RequestsHelper.httpDELETE(URL_ROOT_LANGUAGE + idLanguage);
	}


	@Test
	public void testCRUD() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("pathFolder", "path")
				.add("description", "description")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		assertEquals(201, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertFalse(response.getPayload().containsKey("name"));
		assertFalse(response.getPayload().containsKey("pathFolder"));
		assertFalse(response.getPayload().containsKey("description"));

		long idConfiguration = response.getPayload().getJsonNumber("id").longValue();

		//Update data

		jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-expressUpdate")
				.add("pathFolder", "pathUpdate")
				.add("description", "descriptionUpdate")		
				.build();


		response = RequestsHelper.httpPOST(URL_ROOT_CONFIGURATION + idConfiguration, jsonPayloadRequest);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("name"));
		assertTrue(response.getPayload().containsKey("pathFolder"));
		assertTrue(response.getPayload().containsKey("description"));

		assertEquals("node-js-expressUpdate", response.getPayload().getString("name"));
		assertEquals("pathUpdate", response.getPayload().getString("pathFolder"));
		assertEquals("descriptionUpdate", response.getPayload().getString("description"));	

		//Find data

		response = RequestsHelper.httpGetJsonObject(URL_ROOT_CONFIGURATION + idConfiguration);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("name"));
		assertTrue(response.getPayload().containsKey("pathFolder"));
		assertTrue(response.getPayload().containsKey("description"));

		assertEquals("node-js-expressUpdate", response.getPayload().getString("name"));
		assertEquals("pathUpdate", response.getPayload().getString("pathFolder"));
		assertEquals("descriptionUpdate", response.getPayload().getString("description"));

		//Delete data

		response = RequestsHelper.httpDELETE(URL_ROOT_LANGUAGE + idLanguage + "/configurations/" + idConfiguration);

		assertEquals(200, response.getResponseCode());

		//Find data deleted

		response = RequestsHelper.httpGetJsonObject(URL_ROOT_CONFIGURATION + idConfiguration);

		assertEquals(404, response.getResponseCode());	
	}

	@Test
	public void getConfigurationWithUnknowID() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpGetJsonObject(URL_ROOT_CONFIGURATION + "99999999");

		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void updateConfigurationWithNothing() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("pathFolder", "path")
				.add("description", "description")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		long idConfiguration = response.getPayload().getJsonNumber("id").longValue();

		//Update data

		jsonPayloadRequest = Json.createObjectBuilder().build();

		response = RequestsHelper.httpPOST(URL_ROOT_CONFIGURATION + idConfiguration, jsonPayloadRequest);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("name"));
		assertTrue(response.getPayload().containsKey("pathFolder"));
		assertTrue(response.getPayload().containsKey("description"));

		assertEquals("node-js-express", response.getPayload().getString("name"));
		assertEquals("path", response.getPayload().getString("pathFolder"));
		assertEquals("description", response.getPayload().getString("description"));	
	}

	@Test
	public void updateConfigurationWithUniqueNameConstraint() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("pathFolder", "path")
				.add("description", "description")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		long idConfiguration = response.getPayload().getJsonNumber("id").longValue();

		jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express2")
				.add("pathFolder", "path2")
				.add("description", "description2")
				.build();

		response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		
		//Update data

		jsonPayloadRequest = Json.createObjectBuilder().add("name", "node-js-express2").build();

		response = RequestsHelper.httpPOST(URL_ROOT_CONFIGURATION + idConfiguration, jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void updateConfigurationWithUniquePathFolderConstraint() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("pathFolder", "path")
				.add("description", "description")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		long idConfiguration = response.getPayload().getJsonNumber("id").longValue();

		jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express2")
				.add("pathFolder", "path2")
				.add("description", "description2")
				.build();

		response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		
		//Update data

		jsonPayloadRequest = Json.createObjectBuilder().add("pathFolder", "path2").build();

		response = RequestsHelper.httpPOST(URL_ROOT_CONFIGURATION + idConfiguration, jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createConfigurationWithUnknowLanguageID() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("pathFolder", "path")
				.add("description", "description")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + 9999 + "/configurations/", jsonPayloadRequest);

		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void createConfigurationNullName() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("pathFolder", "path")
				.add("description", "description")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());

	}

	@Test
	public void createConfigurationUniqueNameConstraint() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("pathFolder", "path")
				.add("description", "description")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("pathFolder", "path2")
				.add("description", "description2")
				.build();

		response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);
	
		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createConfigurationNullPathFolder() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("description", "description")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void createConfigurationUniquePathFolderConstraint() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("pathFolder", "path")
				.add("description", "description")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express2")
				.add("pathFolder", "path")
				.add("description", "description2")
				.build();

		response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);
	
		assertEquals(400, response.getResponseCode());

	}

	@Test
	public void createConfigurationNullDescription() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("pathFolder", "path")
				.add("name", "node-js-express")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
	}

	@Test
	public void deleteConfigurtionWithUnknwowLanguageID() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("pathFolder", "path")
				.add("description", "description")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE + idLanguage + "/configurations/", jsonPayloadRequest);

		long idConfiguration = response.getPayload().getJsonNumber("id").longValue();

		response = RequestsHelper.httpDELETE(URL_ROOT_LANGUAGE + "999999/configurations/" + idConfiguration);

		assertEquals(404, response.getResponseCode());
	}

	@Test
	public void deleteConfigurationWithUnknowConfigurationID() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpDELETE(URL_ROOT_LANGUAGE + idLanguage + "/configurations/99999");

		assertEquals(404, response.getResponseCode());
	}

}