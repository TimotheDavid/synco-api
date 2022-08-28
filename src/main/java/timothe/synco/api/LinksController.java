package timothe.synco.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import timothe.synco.dto.http.ErrorResponse;
import timothe.synco.dto.link.CreateLinkDTO;
import timothe.synco.dto.link.response.CreateLinkResponseDTO;
import timothe.synco.dto.link.response.QrCodeResponse;
import timothe.synco.error.HttpExceptions;
import timothe.synco.model.Link;
import timothe.synco.model.User;
import timothe.synco.service.LinkService;
import timothe.synco.service.QrCodeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.containsWhitespace;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

@RestController
@RequestMapping("/api/v1/link")
@Slf4j
public class LinksController {


    @Autowired
    LinkService links;

    @Autowired
    QrCodeService qrCodeService;

    /**
     * crete the link
     *
     * @param create
     * @return
     */
    @PostMapping
    public ResponseEntity create(@RequestBody CreateLinkDTO create) {
        Link link = Link.builder().id(UUID.randomUUID())
                .name(create.getName())
                .nameUrl(create.getNameUrl())
                .shortUrl(create.getShortUrl())
                .longUrl(create.getLongUrl())
                .maxClicked(create.getMaxClicked())
                .loginUrl(create.getLoginUrl())
                .maxClickedRedirectionLink(create.getMaxClickedRedirectionLink())
                .build();

        User user = User.builder().id(create.getUserId()).build();

        try {
            links.create(link, user);
        } catch (HttpExceptions http) {
            return ResponseEntity.status(http.getCode()).body(new ErrorResponse(http));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateLinkResponseDTO(link.getId()));
    }

    /**
     * get all the link by userId
     *
     * @param id
     * @return ResponseEntity
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<List<Link>> getAllLinkOfAnUser(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        User user = User.builder().id(uuid).build();
        List<Link> listLink = links.getAllLinkFromUserId(user);
        return ResponseEntity.status(HttpStatus.OK).body(listLink);
    }

    /**
     * generate a image of a qrcode of a link
     *
     * @param id
     * @param qrCodeGeneratorDTO
     * @param response
     * @param request
     * @return
     */
    @GetMapping(value = "/generate/{id}")
    public HttpEntity<?> generateQrCode(@PathVariable String id, @RequestParam Map<String, String> qrCodeGeneratorDTO, HttpServletResponse response, HttpServletRequest request) {

        if (containsWhitespace(id)) {
            return new HttpEntity<>(new ErrorResponse(new HttpExceptions("400", new Throwable("the uri contains whitespace"), 400)));

        }
        if (qrCodeGeneratorDTO.get("width").isEmpty() || qrCodeGeneratorDTO.get("height").isEmpty()) {
            return new HttpEntity<>(new ErrorResponse(new HttpExceptions("400", new Throwable("need width and height to generate the qrcode"), 400)));
        }

        int width = Integer.parseInt(qrCodeGeneratorDTO.get("width"));
        int height = Integer.parseInt(qrCodeGeneratorDTO.get("height"));


        Link link = Link.builder().id(UUID.fromString(id)).build();
        QrCodeResponse stream = null;
        try {
            stream = qrCodeService.getQrCode(link, width, height);
        } catch (HttpExceptions http) {
            return new HttpEntity<>(new ErrorResponse(http));
        }

        HttpHeaders header = new HttpHeaders();
        String fileName = MessageFormat.format("attachment; filename=\"{0}.png\"", deleteWhitespace(stream.getName()));
        header.setContentType(MediaType.IMAGE_PNG);
        header.add(HttpHeaders.CONTENT_DISPOSITION, fileName);
        header.setContentLength(stream.getData().length);
        return new HttpEntity<>(stream.getData(), header);
    }
}
