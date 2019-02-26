package fr.amu.ter_genrest.services.generation;

import java.util.Map;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.user.User;

@Local
public interface WebServiceGenerator {
	
	public void init();
	public Map<String, Object> buildDataRoutes(User user, Language language, 
			fr.amu.ter_genrest.entities.environment_technical.Configuration configuration, OperatingSystem operatingSystem );
	
	public Map<String, Object> buildDataServer(User user, Language language, 
			fr.amu.ter_genrest.entities.environment_technical.Configuration configuration, OperatingSystem operatingSystem );
	
	public void writeServerFile();
	public void writeRoutesFile();
}
