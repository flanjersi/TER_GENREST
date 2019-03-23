package fr.amu.ter_genrest.services.generation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.io.FileUtils;

import fr.amu.ter_genrest.entities.environment_technical.Configuration;
import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.services.utils.DirectoryManager;
import freemarker.template.Template;
import freemarker.template.Version;

@Stateless
public class OperatingSystemFileGeneratorImpl implements OperatingSystemFileGenerator{


	@EJB
	private DirectoryManager directoryManager;

	@Override
	public void engine(Project project, Language language, Configuration configuration,
			OperatingSystem operatingSystem) {

		try {
			String operatingSystemFolder = directoryManager.templateFolder(language, configuration) 
					+ File.separator + "operatingsSystem" + File.separator + operatingSystem.getNameFolder();
			String generatedDirectoryPath = directoryManager.generateProjectDestFolderName(project, language, configuration, operatingSystem);


			File scriptsFolder = new File(operatingSystemFolder + File.separator + "scripts");

			File scriptsFolderDest = new File(generatedDirectoryPath);

			File appFolder = new File(operatingSystemFolder + File.separator + "app");

			File appFolderDest = new File(generatedDirectoryPath);

			FileUtils.copyDirectory(scriptsFolder, scriptsFolderDest);
			FileUtils.copyDirectory(appFolder, appFolderDest);

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
