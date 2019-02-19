package fr.amu.terGENREST.services.project;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.user.User;

/**
 * @author youcef
 *
 */

@Stateless
public class ProjectManagerImpl  implements ProjectManager{

	
	@PersistenceContext(unitName = "database")
    private EntityManager em;
	
	@Override
	public Project findProject(Long id) {
		return em.find(Project.class, id);
	}

	@Override
	public Project updateProject(Project project) {
		return em.merge(project);
	}

	@Override
	public List<Project> findByProjectName(String projectName) {
		TypedQuery<Project> q  = em.createNamedQuery("Project.findByName", Project.class).setParameter("projectName", projectName);
		return  q.getResultList();
	}

	@Override
	public List<Project> findAllProject() {
		return em.createNamedQuery("Project.findAll", Project.class).getResultList();
	}
	


}
