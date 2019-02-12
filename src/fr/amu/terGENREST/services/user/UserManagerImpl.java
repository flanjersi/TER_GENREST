package fr.amu.terGENREST.services.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import fr.amu.terGENREST.entities.user.User;

@Stateless
public class UserManagerImpl implements UserManager{
	
	@PersistenceContext(unitName = "database")
    private EntityManager em;

	@Override
	public void saveUser(User user) {
		try {
			em.persist(user);
		} catch (Exception e) {
			throw new DBException(e); 
		}		
	}

	@Override
	public void updateUser(User user) {
		try {
	        em.merge(user);
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	@Override
	public void removeUser(User user) {
		try {
			User personne = em.find(User.class, user.getId());
			em.remove(personne);
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	@Override
	public List<User> findAllUser() {
		try {
			return em.createNamedQuery("User.findAll", User.class)
                .getResultList();	
			}catch (Exception e) {
				throw new DBException(e);
			}
		}

	@Override
	public User findUser(Long id) {
		try {
			return em.find(User.class, id);
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	@Override
	public User authentification(String email, String password) {
		try {
			return (User) em.createNamedQuery("User.Authentification").getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}
	
	
}
