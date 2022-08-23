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
}
