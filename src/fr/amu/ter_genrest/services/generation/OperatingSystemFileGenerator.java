package fr.amu.ter_genrest.services.generation;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.environment_technical.Configuration;
import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.project.Project;

@Local
public interface OperatingSystemFileGenerator {

	/**
	 * Copy all files needed by the operating system to run the application
	 * @param project
	 * @param language
	 * @param configuration
	 * @param operatingSystem
	 */
	public void engine(Project project, Language language, 
			Configuration configuration, OperatingSystem operatingSystem);
	
}
