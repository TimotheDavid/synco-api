package timothe.synco.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import timothe.synco.env.EnvConfig;
import timothe.synco.model.Link;
import timothe.synco.service.ClickedService;
import timothe.synco.service.LinkService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/")
@Slf4j
public class BaseController {


    @Autowired
    LinkService links;

    @Autowired
    ClickedService clicked;


    @Autowired
    EnvConfig env;

    @GetMapping(value = "{path}")
    public ResponseEntity<String>  redirect(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, @PathVariable String path) throws IOException {

        String host = httpServletRequest.getHeader("Host");
        String platform = httpServletRequest.getHeader("User-Agent");
        Link linkObject = Link.builder().shortUrl(path).build();
        Link link = links.getLinkByShortUrlWithoutUser(linkObject);
        clicked.add(host, platform, link);

       HttpHeaders responseHeaders = new HttpHeaders();
       responseHeaders.setLocation(URI.create(link.getLongUrl()));
        return new ResponseEntity<String>(link.getLongUrl(),responseHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}
