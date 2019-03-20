package fr.amu.ter_genrest.services.generation;

import javax.ejb.Local;

@Local
public interface DatabaseFileGenerator {

	/**
	 * Copy the files of fuseki folder to the folder dest project
	 * @param fusekiFolderStr
	 * @param pathfolderDest
	 */
	public void generateFusekiFiles(String fusekiFolderStr, String pathfolderDest);
	
}
