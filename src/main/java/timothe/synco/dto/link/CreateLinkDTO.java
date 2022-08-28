package timothe.synco.dto.link;


import lombok.*;
import timothe.synco.model.GeoPoint;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateLinkDTO {

    private UUID id;
    private String name;
    private String shortUrl;
    private String longUrl;
    private String nameUrl;
    private UUID userId;
    private int maxClicked;
    private String loginUrl;
    private String maxClickedRedirectionLink;
    private GeoPointDTO points;
}
