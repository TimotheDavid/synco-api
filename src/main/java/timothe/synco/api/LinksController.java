package timothe.synco.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import timothe.synco.dto.http.ErrorResponse;
import timothe.synco.dto.link.CreateLinkDTO;
import timothe.synco.dto.link.response.CreateLinkResponseDTO;
import timothe.synco.error.HttpExceptions;
import timothe.synco.model.Link;
import timothe.synco.model.User;
import timothe.synco.service.LinkService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/link")
@Slf4j
public class LinksController {


    @Autowired
    LinkService links;

    @PostMapping
    public ResponseEntity create(@RequestBody CreateLinkDTO create ) {
        Link link = Link.builder().id(UUID.randomUUID())
                .name(create.getName())
                .nameUrl(create.getNameUrl())
                .shortUrl(create.getShortUrl())
                .longUrl(create.getLongUrl())
                .build();

        User user = User.builder().id(create.getUserId()).build();

        try{
            links.create(link, user);
        }catch (HttpExceptions http) {
            return ResponseEntity.status(http.getCode()).body(new ErrorResponse(http));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateLinkResponseDTO(link.getId()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<Link>> getAllLinkOfAnUser(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        User user = User.builder().id(uuid).build();
        List<Link> listLink =  links.getAllLinkFromUserId(user);
        return ResponseEntity.status(HttpStatus.OK).body(listLink);
    }
}
