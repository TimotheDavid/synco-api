package timothe.synco.dto.link.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeResponse {

    private byte[] data;
    private String name;

}
