package timothe.synco.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import timothe.synco.db.ClickedDao;
import timothe.synco.db.LinkDao;
import timothe.synco.dto.auth.VerifyDTO;
import timothe.synco.error.HttpExceptions;
import timothe.synco.model.Clicked;
import timothe.synco.model.Link;
import timothe.synco.model.User;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class LinkService {


    @Autowired
    @Qualifier("fake-link")
    LinkDao links;

    @Autowired
    @Qualifier("fake-clicked")
    ClickedDao clicked;

    @Autowired
    PasswordEncoder password;


    public List<Link> getAllLinkFromUserId(User user) {
        return links.getAllLinkFromUserId(user);
    }

    public Link getLinkByShortUrl(Link link, User user) {
        return links.getLinkByShortUrl(link, user);
    }

   public Link  getLinkByShortUrlWithoutUser(Link link) {
        return links.getLinkByShortUrlWithoutUser(link);

    }

    public void create(Link link, User user) throws HttpExceptions {
        Link linkObject = links.getLinkByShortUrl(link, user);

        if (linkObject != null) {
            throw new HttpExceptions("302", new Throwable("link found it cannot be created two times"), 302);
        }

        link.setUserId(user.getId());
        links.create(link);
    }

    public Link verifyPassword(VerifyDTO verifyDTO) throws HttpExceptions {

        Link linkObject = Link.builder().shortUrl(verifyDTO.getName()).build();
        Link link = links.getLinkByShortUrlWithoutUser(linkObject);

        if(!password.matches(verifyDTO.getPassword(), link.getPassword()) || !Objects.equals(verifyDTO.getUsername(), link.getUsername())) {
            throw new HttpExceptions("401", new Throwable("user and password doesnt matches"), 401);
        }

        return link;
    }

    public boolean findMaxClicked(Link link) {
        List<Clicked> clickers = clicked.getAllClickedForALink(link);

        return link.getMaxClicked() >= clickers.size();

    }

    public void delete(Link link, User user) throws HttpExceptions {
        Link linkObject = links.getLinkByShortUrl(link, user);

        if(linkObject != null) {
            throw  new HttpExceptions("404", new Throwable("links not found"), 404);
        }

        links.delete(link, user);
    }
}
