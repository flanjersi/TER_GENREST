package fr.amu.ter_genrest.services.generation;

import java.io.File;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.project.Project;

@Local
public interface GenerationFileDescriptionAPI {

	public void generate(File fileDest, Project project);
	
}
