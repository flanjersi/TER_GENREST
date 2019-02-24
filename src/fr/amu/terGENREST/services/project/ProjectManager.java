package fr.amu.terGENREST.services.project;

import java.util.List;
import javax.ejb.Local;

import fr.amu.terGENREST.entities.project.Project;

/**
 * DAO interface to manipulate ORM Project
 * @author Youcef
 *
 */

@Local
public interface ProjectManager {

	public Project findProject(Long id);

	public Project updateProject(Project project);

	public List<Project> findAllProject();

	public List<Project> findByProjectName(String projectName);

}
