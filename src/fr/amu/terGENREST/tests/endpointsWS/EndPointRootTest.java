package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class EndPointRootTest {

	@Test 
	public void testRoot() throws IOException {
		URL url= new URL("http://localhost:8090/terGENREST/api");
		HttpURLConnection http= (HttpURLConnection)url.openConnection();

		int code= http.getResponseCode();

		
		assertTrue(code == 200);
	
		String content = readContentOfStream(http.getInputStream());
		
		assertTrue(content.equals("Root"));
	}

	private String readContentOfStream(InputStream inputStream) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder strBuilder = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
			strBuilder.append(output);
		}
		
		return strBuilder.toString();
	}
}
