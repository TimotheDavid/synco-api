package timothe.synco.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import timothe.synco.db.LinkDao;
import timothe.synco.error.HttpExceptions;
import timothe.synco.model.Link;
import timothe.synco.model.User;

import java.util.List;

@Service
@Slf4j
public class LinkService {


    @Autowired
    @Qualifier("fake-link")
    LinkDao links;


    public List<Link> getAllLinkFromUserId(User user) {
        return links.getAllLinkFromUserId(user);
    }

    public Link getLinkByShortUrl(Link link, User user) {
        return links.getLinkByShortUrl(link, user);
    }

   public Link  getLinkByShortUrlWithoutUser(Link link) {
        return links.getLinkByShortUrlWthoutUser(link);

    }

    public void create(Link link, User user) throws HttpExceptions {
        Link linkObject = links.getLinkByShortUrl(link, user);

        if (linkObject != null) {
            throw new HttpExceptions("302", new Throwable("link found it cannot be created two times"), 302);
        }

        log.info(String.valueOf(user.getId()));
        link.setUserId(user.getId());
        links.create(link);
    }

    public void delete(Link link, User user) throws HttpExceptions {
        Link linkObject = links.getLinkByShortUrl(link, user);

        if(linkObject != null) {
            throw  new HttpExceptions("404", new Throwable("links not found"), 404);
        }

        links.delete(link, user);
    }
}
