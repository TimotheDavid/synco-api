package timothe.synco.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timothe.synco.dto.http.ErrorResponse;
import timothe.synco.dto.user.UserResponseDTO;
import timothe.synco.error.HttpExceptions;
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


    @GetMapping
    public ResponseEntity getOneUser(HttpServletRequest request) throws HttpExceptions {

            String token =  request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
            User userObject;
            try{
                userObject = users.getUserByBearer(token);
            }catch (HttpExceptions http) {
                return ResponseEntity.status(http.getCode()).body(new ErrorResponse(http));
            }
            UserResponseDTO user = UserResponseDTO.builder()
                    .id(userObject.getId())
                    .username(userObject.getUsername())
                    .email(userObject.getEmail())
                    .token(userObject.getToken())
                    .build();

        return ResponseEntity.status(200).body(user);

    }
}
