package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.tests.utils.RequestsHelper;
import fr.amu.terGENREST.tests.utils.Utils;
import junit.framework.Assert;

public class LanguageRESTControllerTest {

	private static final String URL_ROOT_LANGUAGE = "http://localhost:8090/terGENREST/api/language";

	@Test
	public void testCRUD() throws IOException {

		//ADD data

		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "JavaTest")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE, jsonPayloadRequest);

		assertEquals(201, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertFalse(response.getPayload().containsKey("name"));
		assertFalse(response.getPayload().containsKey("configurationsAvailable"));

		long id = response.getPayload().getJsonNumber("id").longValue();

		//Update data
		jsonPayloadRequest = Json.createObjectBuilder().add("name", "JavaTestUpdate").build();

		response = RequestsHelper.httpPOST(URL_ROOT_LANGUAGE + "/" + id, jsonPayloadRequest);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("name"));
		assertTrue(response.getPayload().containsKey("configurationsAvailable"));

		assertEquals("JavaTestUpdate", response.getPayload().getString("name"));

		id = response.getPayload().getJsonNumber("id").longValue();

		//Find data

		response = RequestsHelper.httpGetJsonObject(URL_ROOT_LANGUAGE + "/" + id);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("name"));
		assertTrue(response.getPayload().containsKey("configurationsAvailable"));

		assertEquals("JavaTestUpdate", response.getPayload().getString("name"));

		//Delete data

		response = RequestsHelper.httpDELETE(URL_ROOT_LANGUAGE + "/" + id);

		assertEquals(200, response.getResponseCode());

		//Find data deleted

		response = RequestsHelper.httpGetJsonObject(URL_ROOT_LANGUAGE + "/" + id);

		assertEquals(404, response.getResponseCode());	
	}

	@Test
	public void updateNothing() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "JavaTest")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE, jsonPayloadRequest);

		long id = response.getPayload().getJsonNumber("id").longValue();

		//Update data
		jsonPayloadRequest = Json.createObjectBuilder().build();

		response = RequestsHelper.httpPOST(URL_ROOT_LANGUAGE + "/" + id, jsonPayloadRequest);

		assertEquals(200, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertTrue(response.getPayload().containsKey("name"));
		assertTrue(response.getPayload().containsKey("configurationsAvailable"));

		assertEquals("JavaTest", response.getPayload().getString("name"));

		id = response.getPayload().getJsonNumber("id").longValue();

		//Delete data

		response = RequestsHelper.httpDELETE(URL_ROOT_LANGUAGE + "/" + id);
	}
	
	@Test
	public void testCreateLanguageWithNullName() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE, jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
	}
	
	@Test
	public void testCreateConstraintUniqueName() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "JavaTest")
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE, jsonPayloadRequest);

		long id = response.getPayload().getJsonNumber("id").longValue();

		response = RequestsHelper.httpPUT(URL_ROOT_LANGUAGE, jsonPayloadRequest);

		assertEquals(400, response.getResponseCode());
		
		//Delete data

		response = RequestsHelper.httpDELETE(URL_ROOT_LANGUAGE + "/" + id);
	}
	
	@Test
	public void testUpdateWithUnknowID() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("name", "JavaTestUpdate").build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPOST(URL_ROOT_LANGUAGE + "/999999999", jsonPayloadRequest);

		assertEquals(404, response.getResponseCode());
	}


	@Test
	public void testGetWithUnknowId() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpGetJsonObject(URL_ROOT_LANGUAGE + "/999999999");

		assertEquals(404, response.getResponseCode());

	}
	
	@Test
	public void testDELETEWithUnknowId() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpGetJsonObject(URL_ROOT_LANGUAGE + "/999999999");

		assertEquals(404, response.getResponseCode());
	}
	
	@Test
	public void findAllLanguage() throws IOException {
		RequestsHelper.ResponseJsonArray response = RequestsHelper.httpGetJsonArray(URL_ROOT_LANGUAGE);

		assertEquals(200, response.getResponseCode());
	}
	
}

