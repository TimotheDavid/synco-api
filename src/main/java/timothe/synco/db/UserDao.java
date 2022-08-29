package timothe.synco.db;

import org.springframework.security.provisioning.UserDetailsManager;
import timothe.synco.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao extends UserDetailsManager {

    void createUser(User user);
    User findUserById(UUID id);
    User findUserByEmail(User user);

    List<User> getAllUser();

    User findByToken(String token);

    User setToken(User user);
}
