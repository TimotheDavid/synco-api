package timothe.synco.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timothe.synco.dto.user.UserResponseDTO;
import timothe.synco.model.User;
import timothe.synco.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @Autowired
    UserService users;

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(users.getAllUser());
    }

    @GetMapping
    public ResponseEntity getOneUser(HttpServletRequest request) {

            String token =  request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
            log.info("get one user " + token);

            User  userObject = users.getUserByBearer(token);
            UserResponseDTO user = UserResponseDTO.builder()
                    .id(userObject.getId())
                    .username(userObject.getUsername())
                    .email(userObject.getEmail())
                    .token(userObject.getToken())
                    .build();

        return ResponseEntity.status(200).body(user);

    }
}
