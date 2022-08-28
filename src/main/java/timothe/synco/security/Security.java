package timothe.synco.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import timothe.synco.service.ClickedService;
import timothe.synco.service.LinkService;

import java.util.List;

@Configuration
@Slf4j
public class Security {


    @Autowired
    LinkService linkService;

    @Autowired
    ClickedService clickedService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")));
        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChainBasicAuth(HttpSecurity http) throws Exception {
        http.requestMatcher(request -> {
                    log.info("url " + request.getRequestURL().toString());
                    return false ;

                })
                .authorizeRequests()
                .anyRequest()
                .authenticated().and()
                .addFilterBefore(new BasicAuthFilter(linkService, clickedService), BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public SecurityFilterChain securityPublicFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().antMatchers("/api/v1/auth", "/").permitAll();
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("HEAD", "POST", "PUT", "GET", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Origin", "Origin"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CorsRegistry addCorsMappings(CorsRegistry registry) {

        final CorsRegistry corsRegistry = new CorsRegistry();
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");

        return corsRegistry;
    }







}
