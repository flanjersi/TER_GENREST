package fr.amu.ter_genrest.tests.endpoints_ws;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import fr.amu.ter_genrest.tests.utils.Utils;


public class EndPointRootTest {

	@Test 
	public void testRoot() throws IOException {
		URL url= new URL("http://localhost:8090/terGENREST/api");
		HttpURLConnection http= (HttpURLConnection)url.openConnection();

		int code= http.getResponseCode();

		assertTrue(code == 200);
	
		String content = Utils.readContentOfStream(http.getInputStream());
		
		assertTrue(content.equals("Root"));
	}
}
