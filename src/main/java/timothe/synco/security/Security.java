package timothe.synco.security;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class Security {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().antMatchers("/api/v1/auth", "/").permitAll()
                .and().csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .httpBasic().disable()
        ;

        return http.build();

    }







}
