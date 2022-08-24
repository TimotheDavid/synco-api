package timothe.synco.env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class EnvConfig {


    @Autowired
    Environment env;

    public String getBase(){
        return env.getProperty("dev.base.url");

    }



}
