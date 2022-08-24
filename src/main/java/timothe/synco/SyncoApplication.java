package timothe.synco;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import timothe.synco.env.EnvConfig;
import timothe.synco.model.Clicked;
import timothe.synco.model.Link;
import timothe.synco.model.User;
import timothe.synco.service.ClickedService;
import timothe.synco.service.LinkService;
import timothe.synco.service.UserService;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.trim;

@SpringBootApplication @Slf4j
public class SyncoApplication {

    @Autowired
    EnvConfig env;
    public static void main(String[] args) {
        SpringApplication.run(SyncoApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    Faker faker(){
        return new Faker();

    }
    @Bean
    CommandLineRunner init (UserService user, LinkService link, ClickedService clicked) {
        return args -> {

            for (int i = 0; i<=2; i++) {
                User userObject = new User(UUID.randomUUID(),
                        passwordEncoder().encode(faker().lorem().characters(8)),
                        faker().name().username(),
                        faker().internet().emailAddress(), "");
                user.register(userObject);

                for(int v = 0; v <= 1; v++) {
                    String url = faker().internet().url().replace("www.", "https://");
                    Link linkObject = Link.builder().id(UUID.randomUUID())
                            .longUrl(url)
                            .shortUrl(RandomStringUtils.random(8, true, false))
                            .name(trim(faker().lorem().fixedString(10)))
                            .nameUrl(faker().internet().url())
                            .build();
                    log.info("link " + linkObject.getId());
                    log.info(env.getBase() + linkObject.getShortUrl());
                    link.create(linkObject, userObject);

                    for(int l = 0; l < 3; l++){
                        Clicked click = Clicked.builder()
                                .host(faker().internet().domainName())
                                .date(Date.from(Instant.now()))
                                .id(UUID.randomUUID())
                                .platform(faker().internet().userAgentAny())
                                .linkId(linkObject.getId()).build();
                        log.info("click " + click.getId());
                        clicked.add(click.getHost(), click.getPlatform(), linkObject);

                    }

                }
            }

        };

    }

}
