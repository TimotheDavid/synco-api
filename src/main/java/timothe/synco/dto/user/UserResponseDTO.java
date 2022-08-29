package timothe.synco.dto.user;

import lombok.*;

import java.util.UUID;

@Setter
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class UserResponseDTO {

    private UUID id;
    private String username;
    private String token;
    private String email;
}
