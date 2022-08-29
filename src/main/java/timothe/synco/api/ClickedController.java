package timothe.synco.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timothe.synco.model.Link;
import timothe.synco.service.ClickedService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clicked")
public class ClickedController {

    @Autowired
    ClickedService clicker;

    @GetMapping(value = "/{id}")
    public ResponseEntity getAllClickerByLink(@PathVariable String id) {
        return  ResponseEntity.status(HttpStatus.OK).body(clicker.getAllClickedByLink(Link.builder().id(UUID.fromString(id)).build()));
    }

}
