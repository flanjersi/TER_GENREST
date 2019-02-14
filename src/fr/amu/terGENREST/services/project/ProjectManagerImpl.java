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
		// TODO Auto-generated method stub
		return em.find(Project.class, id);
	}

	@Override
	public void addProject(Project project) {
		// TODO Auto-generated method stub
		em.persist(project);
	}

	@Override
	public void removeProject(Project project) {
		// TODO Auto-generated method stub
		em.remove(em.contains(project) ? project : em.merge(project));		
	}

	@Override
	public Project updateProject(Project project) {
		// TODO Auto-generated method stub
		return em.merge(project);
	}


	@Override
	public List<Project> findAllProject() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Project.findAll", Project.class).getResultList();
	}
	


}
