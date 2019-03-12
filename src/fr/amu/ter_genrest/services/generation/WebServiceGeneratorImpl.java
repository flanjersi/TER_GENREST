package fr.amu.ter_genrest.services.generation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.services.DirectoryManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

@Stateless
public class WebServiceGeneratorImpl implements WebServiceGenerator {

	private Template templateRoute=null;
	private Template templateServer=null; 

	private final static String ROUTE_TEMPLATE = "RoutesTemplate.ftl";
	private final static String SERVER_TEMPLATE = "ServerTemplate.ftl";
	
	//private final static String GENERATED_FOLDER_PATH = "Generated/";  // in root paath
	private final static String URLPROJECTS = "/projects/";
	private final static String EXPRESS_PORT="3001";
	private final static String SERVER = "server.js";
	private final static String ROUTES = "routes.js";
	
	@Context 
	ServletContext servletContext;
	
	@EJB
	DirectoryManager directoryManager;
	
	@Override
	public String engine(Project project, Language language,
			fr.amu.ter_genrest.entities.environment_technical.Configuration configuration,
			OperatingSystem operatingSystem) {
		
		String templateFolder = configuration.getPathFolder();
		
		initTemplate(templateFolder);					
				
		// create directory in the context
		String generatedDirectoryPath = directoryManager.createDirectory(servletContext.getRealPath("/")).getAbsolutePath();   
		
		// TODO configuration.getPort() instead EXPRESS_PORT
		writeFile(generatedDirectoryPath, buildDataRoutes(project), buildDataServer(EXPRESS_PORT));	
		
		return generatedDirectoryPath;
	}
	
	// init template files
	public void initTemplate(String pathTemplateFolder) {
		Configuration cfg = new Configuration(new Version(2, 3, 23)); 

		cfg.setClassForTemplateLoading(this.getClass(), pathTemplateFolder);   // lookup for templates
		try { 
			 templateRoute = cfg.getTemplate(ROUTE_TEMPLATE);
			 templateServer = cfg.getTemplate(SERVER_TEMPLATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// build the data for route template
	public Map<String, Object> buildDataRoutes(Project project) {
		
		Map<String, Object> dataMapRoutes =  new HashMap<String, Object>();
		dataMapRoutes.put("urlProject", URLPROJECTS);
		dataMapRoutes.put("project", project);
		return dataMapRoutes;
	}

	// build the data that will be used by the server template
	public Map<String, Object> buildDataServer(String port) {
		Map<String, Object> dataMapServer =  new HashMap<String, Object>();

		dataMapServer.put("PORT", port);
		return dataMapServer;
	}
	
	public void writeFile( String generatedDirectoryPath,  Map<String, Object> routesData,  Map<String, Object> serverData) {
		
		Writer routeFile = null;
		Writer serverFile = null;
		String seperator = File.separator; 
		// Write routes File
		try {
			routeFile  = new FileWriter(new File(generatedDirectoryPath+seperator+ROUTES));
				
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
			serverFile = new FileWriter(new File(generatedDirectoryPath+seperator+SERVER));
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