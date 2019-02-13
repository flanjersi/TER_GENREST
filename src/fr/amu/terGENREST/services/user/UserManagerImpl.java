package fr.amu.terGENREST.services.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import fr.amu.terGENREST.entities.user.User;

@Stateless
public class UserManagerImpl implements UserManager{
	/**
	 * DAO Implements to manipulate ORM User data
	 * @author Mohamed
	 *
	 */
	
	@PersistenceContext(unitName = "database")
    private EntityManager em;

	@Override
	public Long saveUser(User user) {
		try {
			em.persist(user);
			return user.getId();
		} catch (Exception e) {
			throw new DBException(e); 
		}		
	}

	@Override
	public Long updateUser(User user) {
		try {
	        em.merge(user);
	        return user.getId();
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	@Override
	public Long removeUser(User user) {
		try {
			 em.remove(user);
			 return user.getId();
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
