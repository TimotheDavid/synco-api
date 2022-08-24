package timothe.synco.dto.http;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import timothe.synco.error.HttpExceptions;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private String message;
    private String cause;
    private int code;
    public ErrorResponse(HttpExceptions http){
        this.message = http.getMessage();
        this.code = http.getCode();
        this.cause = http.getCause().getMessage();
    }


}
