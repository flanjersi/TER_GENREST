package fr.amu.terGENREST.services.environmentTechnical;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.environmentTechnical.Configuration;

@Local
public interface ConfigurationManager {

	public Configuration updateConfiguration(Configuration configuration);
	
	public Configuration findById(long id);
}
