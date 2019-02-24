package fr.amu.ter_genrest.services.user;

import java.util.List;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.user.User;

/**
 * DAO interface to manipulate ORM User data
 * @author Mohamed
 *
 */

@Local
public interface UserManager {

	public void saveUser(User user);

	public void updateUser(User user);

	public void removeUser(User user);

	public User findUser(Long id);

	public User findUserByEmail(String email);

	public List<User> findAllUser();

	public User authentification(String email, String password);

}
