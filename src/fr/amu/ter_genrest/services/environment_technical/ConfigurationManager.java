package fr.amu.ter_genrest.services.environment_technical;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.environment_technical.Configuration;

@Local
public interface ConfigurationManager {

	public Configuration updateConfiguration(Configuration configuration);
	
	public Configuration findById(long id);
	
	public Configuration findByName(String name);
	
	public Configuration findByPathFolder(String pathFolder);
	
}
