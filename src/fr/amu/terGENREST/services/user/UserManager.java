package fr.amu.terGENREST.services.user;

import java.util.List;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.user.User;

/**
 * DAO interface to manipulate ORM User data
 * @author Mohamed
 *
 */

@Local
public interface UserManager {

	public Long saveUser(User user);
	
	public Long updateUser(User user);
	
	public Long removeUser(User user);
	
	public List<User> findAllUser();
	
	public User findUser(Long id);
	
	public User authentification(String email, String password);

}
