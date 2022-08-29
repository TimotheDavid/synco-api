package timothe.synco.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timothe.synco.dto.http.ErrorResponse;
import timothe.synco.dto.auth.LoginDTO;
import timothe.synco.dto.auth.RegisterDTO;
import timothe.synco.dto.http.TokenResponse;
import timothe.synco.error.HttpExceptions;
import timothe.synco.model.User;
import timothe.synco.service.UserService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    @Autowired
    UserService user;

    /**
     * login routes for login the user
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) throws Exception {
        User userObject;
        try{
            userObject =  user.login(User.builder().email(login.getEmail()).password(login.getPassword()).build());
        } catch (HttpExceptions exception) {
            return ResponseEntity.status(exception.getCode()).body(new ErrorResponse(exception));
        }

        TokenResponse token = new TokenResponse();
        token.setId(userObject.getId());
        token.setToken(userObject.getToken());
        return ResponseEntity.ok(token);
    }

    /**
     * register an user
     * @param register
     * @return
     * @throws Exception
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO register ) throws Exception {
        try{
            user.register(User.builder().email(register.getEmail()).password(register.getPassword()).username(register.getUsername()).id(UUID.randomUUID()).build());

        }catch (HttpExceptions exception) {

            return ResponseEntity.status(exception.getCode()).body(new ErrorResponse(exception));
        }

        return ResponseEntity.ok().build();
    }


}
