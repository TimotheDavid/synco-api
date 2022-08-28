package timothe.synco.model;


import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Link {

    private UUID id;
    private String name;
    private String shortUrl;
    private String longUrl;
    private String nameUrl;
    private UUID userId;
    private String username;
    private String password;
    private String loginUrl;

    private int maxClicked;
    private String maxClickedRedirectionLink;
    private GeoPoint points;
}
