package timothe.synco.db.fake;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import timothe.synco.db.UserDao;
import timothe.synco.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Qualifier("fake")
@Repository
public class UserFakeDB implements UserDao {

    List<User> users = new ArrayList<>();

    @Override
    public void createUser(UserDetails user) {
        //

    }

    @Override
    public void updateUser(UserDetails user) {
        //
    }

    @Override
    public void deleteUser(String username) {
        users = users.stream().dropWhile(user -> user.getUsername().equals(username)).collect(Collectors.toList());
    }

    //TODO : make it
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // TODO document why this method is empty
    }

    @Override
    public boolean userExists(String username) {
        return users.stream().anyMatch(user -> Objects.equals(user.getUsername(), username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream().filter(user -> Objects.equals(user.getUsername(), username)).findFirst().orElse(null);
    }

    @Override
    public void createUser(User user) {
        users.add(user);
    }

    @Override
    public User findUserById(UUID id) {
        return users.stream().filter(user -> user.id().equals(id)).findFirst().orElse(null);
    }

    @Override
    public User findUserByEmail(User user) {
        return users.stream().filter(userObject -> Objects.equals(userObject.getEmail(), user.getEmail())).findFirst().orElse(null);

    }

    @Override
    public List<User> getAllUser() {
        return users;
    }

    @Override
    public User findByToken(String token) {
        return users.stream().filter(user -> Objects.equals(user.getToken(), token)).findFirst().orElse(null);
    }

    @Override
    public User setToken(User user) {
        User userObject = users.stream().filter(userData -> Objects.equals(user.getId(), userData.getId())).findFirst().orElse(null);
        users.remove(userObject);
        users.add(user);
        return user;
    }


}
