package fr.amu.ter_genrest.services.generation;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.amu.ter_genrest.entities.environment_technical.Configuration;
import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.services.utils.DirectoryManager;

@Stateless
public class ProjectGeneratorImpl implements ProjectGenerator{

	@EJB
	private DirectoryManager directoryManager;
	
	@EJB
	private DatabaseFileGenerator databaseFileGenerator;
	
	@EJB
	private WebServiceGenerator webServiceGenerator;
	
	@EJB
	private OperatingSystemFileGenerator operatingSystemFileGenerator;
	
	@EJB
	private GenerationJsonLD generationJsonLD;
	
	@EJB
	private GenerationFileDescriptionAPI generationDescriptionAPI;
	
	
	@Override
	public String generateProject(Project project, Language language, Configuration configuration,
			OperatingSystem operatingSystem) {
		
		try {
			String generatedDirectoryPath = directoryManager.createDirectory(
					directoryManager.generateProjectDestFolderName(project, language, configuration, operatingSystem)
				).getAbsolutePath();   
			
			String fileJsonLDName = directoryManager.generateFileNameDataJsonLD(project, language, configuration, operatingSystem);
			String descriptionAPIFileName = directoryManager.generateFileNameDescriptionAPI(project, language, configuration, operatingSystem);
			
			generationJsonLD.generateJsonLDDataFile(new File(fileJsonLDName), project);
			
			generationDescriptionAPI.generate(new File(descriptionAPIFileName), project);
			
			webServiceGenerator.engine(project, language, configuration, operatingSystem);
			
			databaseFileGenerator.generateFusekiFiles(directoryManager.fusekiFolder(), generatedDirectoryPath);
	
			operatingSystemFileGenerator.engine(project, language, configuration, operatingSystem);
			
			
			return generatedDirectoryPath;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
