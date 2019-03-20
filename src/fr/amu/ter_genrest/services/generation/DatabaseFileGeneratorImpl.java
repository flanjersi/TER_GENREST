package fr.amu.ter_genrest.services.generation;

import java.io.File;
import java.io.IOException;

import javax.ejb.Stateless;

import org.apache.commons.io.FileUtils;

@Stateless
public class DatabaseFileGeneratorImpl implements DatabaseFileGenerator{

	@Override
	public void generateFusekiFiles(String fusekiFolderStr, String pathfolderDest) {
		File fusekiFolder = new File(fusekiFolderStr);
		
		File fusekiFolderDest = new File(pathfolderDest, "fuseki");
		
		try {
			FileUtils.copyDirectory(fusekiFolder, fusekiFolderDest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
