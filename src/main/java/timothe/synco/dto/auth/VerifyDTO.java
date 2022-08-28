package timothe.synco.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerifyDTO {

    private String username;
    private String password;
    private String name;

}
