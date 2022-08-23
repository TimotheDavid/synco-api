package timothe.synco.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Getter

public class TokenResponse {

    private UUID id;
    private String token;

    public TokenResponse(UUID id) {
        log.info("user " + id.toString());
        this.id = id;
    }

    public String toString(){
        return "user " + id.toString();
    }
}
