package fr.amu.ter_genrest.services.generation;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.environment_technical.Configuration;
import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.project.Project;

@Local
public interface ProjectGenerator {
	
	/**
	 * Method to generate the project which contains web service, Jena fuseki, scripts etc ..
	 * @param project
	 * @param language
	 * @param configuration
	 * @param operatingSystem
	 * @return
	 */
	public String generateProject(Project project, Language language, 
			Configuration configuration, OperatingSystem operatingSystem);

}
