package fr.amu.terGENREST.services.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.DBException;

@Stateless
public class UserManagerImpl implements UserManager {
	/**
	 * DAO Implements to manipulate ORM User data
	 * 
	 * @author Mohamed
	 *
	 */

	@PersistenceContext(unitName = "database")
	private EntityManager em;

	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_PASSWORD = "password";

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
			em.remove(em.contains(user) ? user : em.merge(user));	
	} catch (Exception e) {
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
			Query request = em.createNamedQuery("User.Authentication");
			request.setParameter(PARAM_EMAIL, email);
			request.setParameter(PARAM_PASSWORD, password);
			return (User) request.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	@Override
	public User findUserByEmail(String email) {
		try {
			Query request = em.createNamedQuery("User.findUserByEmail");
			request.setParameter(PARAM_EMAIL, email);
			return (User) request.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	@Override
	public List<User> findAllUser() {
		try {
			return  em.createNamedQuery("User.findAll").getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}
	
	

}
