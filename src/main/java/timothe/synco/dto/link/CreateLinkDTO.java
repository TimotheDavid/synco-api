package timothe.synco.dto.link;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateLinkDTO {

    private UUID id;
    private String name;
    private String shortUrl;
    private String longUrl;
    private String nameUrl;
    private UUID userId;
}
