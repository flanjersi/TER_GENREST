package fr.amu.ter_genrest.services.generation;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.environment_technical.Configuration;
import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.user.User;

@Local
public interface WebServiceGenerator {
	
	public String engine(Project project, Language language, 
			Configuration configuration, OperatingSystem operatingSystem);
}
