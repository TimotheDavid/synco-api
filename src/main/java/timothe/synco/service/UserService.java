package timothe.synco.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import timothe.synco.db.UserDao;
import timothe.synco.error.HttpExceptions;
import timothe.synco.model.User;

import java.util.List;

@Service
@Slf4j
public class UserService {


    @Qualifier("fake")
    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder password;

    public void register(User user) throws Exception {
        User userEmail = userDao.findUserByEmail(user);
        if (userEmail  != null ) {
            throw new HttpExceptions("302", new Throwable("user found , cannot create"), 302);
        }

        String hash = password.encode(user.getPassword());
        user.password(hash);
        userDao.createUser(user);
    }

    public User login(User user) throws Exception {
        User userEmail = userDao.findUserByEmail(user);
        if(userEmail  == null) {
            log.error("user not found, cannot login");
            throw new HttpExceptions("404", new Throwable("user not found, cannot login"), 404);
        }

        if(!password.matches(user.getPassword(), userEmail.getPassword())) {
            log.error("password not matches");
            throw new HttpExceptions("401", new Throwable("password not matches"), 401);
        }

        return User.builder().id(userEmail.getId()).build();
    }

    public List<User> getAllUser() {
        return userDao.getAllUser();


    }




}
