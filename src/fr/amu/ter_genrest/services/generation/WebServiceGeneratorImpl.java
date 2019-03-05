package fr.amu.ter_genrest.services.generation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.project.Project;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

@Stateless
public class WebServiceGeneratorImpl implements WebServiceGenerator {


	private final static String EXPRESS_PORT="3001";
	private Template templateRoute=null;
	private Template templateServer=null; 

	private final static String EXPRESS_ROUTE_TEMPLATE = "RoutesTemplate.ftl";
	private final static String EXPRESS_SERVER_TEMPLATE = "ServerTemplate.ftl";
	//private final static  String GENERATED_FOLDER_PATH = System.getProperty("user.dir")+"/Generated/";  // in root paath
														// "C:\\Users\\moham\\eclipse-workspace\\TER_GENREST\\TER_GENREST\\Generated\\"
	private final static  String GENERATED_FOLDER_PATH = "Generated/";  // in root paath
	private final static String URLPROJECTS = "/projects/";
	
	
	@Override
	public void engine(Project project, Language language,
			fr.amu.ter_genrest.entities.environment_technical.Configuration configuration,
			OperatingSystem operatingSystem) {
		
		if(language.getName().equals("Java")) {

		}
		if(language.getName().equals("Javascript")) {
			if(configuration.getName().equals("nodejs-express")) { 	
				initExpressTemplate();
				writeExpressFile(buildDataExpressRoutes(project), buildDataExpressServer(EXPRESS_PORT));
			}
		}
		if(language.getName().equals("Python")) {
			
		}
		
	}
	
	// init template files
	public void initExpressTemplate() {
		Configuration cfg = new Configuration(new Version(2, 3, 23)); 
		cfg.setClassForTemplateLoading(this.getClass(), "/templates/expressjs/");  // lookup /templates/expressjs/
		try { 
			 templateRoute = cfg.getTemplate(EXPRESS_ROUTE_TEMPLATE);
			 templateServer = cfg.getTemplate(EXPRESS_SERVER_TEMPLATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// build the data for route template
	public Map<String, Object> buildDataExpressRoutes(Project project) {
		
		Map<String, Object> dataMapRoutes =  new HashMap<String, Object>();
		dataMapRoutes.put("urlProject", URLPROJECTS);
		dataMapRoutes.put("project", project);
		return dataMapRoutes;
	}

	// build the data that will be used by the server template
	public Map<String, Object> buildDataExpressServer(String port) {
		Map<String, Object> dataMapServer =  new HashMap<String, Object>();

		dataMapServer.put("PORT", port);
		return dataMapServer;
	}
	
	public void writeExpressFile( Map<String, Object> routesData,  Map<String, Object> serverData) {
		
		Writer routeFile = null;
		Writer serverFile = null;
		 
		// Write routes File
		try {
// ---------------------------------------- delete file before generate	----------------------------		
			File file = new File(GENERATED_FOLDER_PATH+"Routes.js");
			file.delete();
// -----------------------------------------------------------------	

			routeFile  = new FileWriter(new File(GENERATED_FOLDER_PATH+"Routes.js"));
				
			// matches between data and templates
			templateRoute.process( routesData, routeFile);  
		
			routeFile.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				routeFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Write server File
		try {
// ---------------------------------------- delete file before generate	----------------------------					
			File server = new File(GENERATED_FOLDER_PATH+"Server.js");
			server.delete();
// -----------------------------------------------------------------	

			serverFile = new FileWriter(new File(GENERATED_FOLDER_PATH+"Server.js"));
			templateServer.process(serverData, serverFile);
			serverFile.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				serverFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}