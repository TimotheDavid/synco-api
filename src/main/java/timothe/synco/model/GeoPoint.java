package timothe.synco.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoPoint {
    private String latitude;
    private String longitude;
}
