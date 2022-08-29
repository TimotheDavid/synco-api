package timothe.synco.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;



@Builder
@Accessors(fluent = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {


    private UUID id;
    private String password;
    private String username;

    private String token;

    private Instant tokenExpiration;



    private String email;


    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public Instant getTokenExpiration() {
        return tokenExpiration;

    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenExpiration() {
        this.tokenExpiration = Instant.now().plus(30, ChronoUnit.MINUTES);

    }

    public UUID getId() {
        return id;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
