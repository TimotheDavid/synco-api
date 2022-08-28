package timothe.synco.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import timothe.synco.dto.auth.VerifyDTO;
import timothe.synco.dto.auth.VerifyResponseDTO;
import timothe.synco.dto.http.ErrorResponse;
import timothe.synco.env.EnvConfig;
import timothe.synco.error.HttpExceptions;
import timothe.synco.model.Link;
import timothe.synco.service.ClickedService;
import timothe.synco.service.LinkService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;

@RestController
@RequestMapping("/")
@Slf4j
@CrossOrigin
public class BaseController {


    @Autowired
    LinkService links;

    @Autowired
    ClickedService clicked;


    @Autowired
    EnvConfig env;

    /**
     * get the link and redirect to the ong url
     * @param httpServletResponse
     * @param httpServletRequest
     * @param path
     * @return
     * @throws IOException
     */
    @GetMapping(value = "{path}")
    public ResponseEntity<String>  redirect(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, @PathVariable String path) throws IOException {
        String host = httpServletRequest.getHeader("Host");
        String platform = httpServletRequest.getHeader("User-Agent");
        Link linkObject = Link.builder().shortUrl(path).build();
        Link link = links.getLinkByShortUrlWithoutUser(linkObject);
        HttpHeaders responseHeaders = new HttpHeaders();
        if(!findMaxClicked(link)){
            responseHeaders.setLocation(URI.create(link.getMaxClickedRedirectionLink()));
            return new ResponseEntity<String>(link.getMaxClickedRedirectionLink(),responseHeaders,  HttpStatus.MOVED_PERMANENTLY);
        }

        clicked.add(host, platform, link);
        responseHeaders.setLocation(URI.create(link.getLongUrl()));
        return new ResponseEntity<String>(link.getLongUrl(),responseHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    /**
     * verify the link with the password and the username given by the database and the user
     * @param request
     * @param response
     * @param verifydto
     * @return
     */
    @PostMapping("verify")
    public ResponseEntity verify(HttpServletRequest request, HttpServletResponse response, @RequestBody VerifyDTO verifydto) {
        if(verifydto.getPassword().isEmpty() || verifydto.getUsername().isEmpty()) {
            return ResponseEntity.status(401).body(new ErrorResponse(new HttpExceptions("401", new Throwable("bad request for username or password"), 401)));
        }

        Link link;
        try{
            link =  links.verifyPassword(verifydto);
        }catch (HttpExceptions http) {
            return ResponseEntity.status(http.getCode()).body(new ErrorResponse(http));
        }

        return ResponseEntity.ok(new VerifyResponseDTO(link.getLongUrl()));


    }

    /**
     * find if the max of click have been for a link
     * @param link
     * @return boolean
     */
    public boolean findMaxClicked(Link link) {
       return  links.findMaxClicked(link);
    }
}
