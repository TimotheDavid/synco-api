package timothe.synco.model;


import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clicked {

    private UUID id;
    private String app;
    private Date date;

}
