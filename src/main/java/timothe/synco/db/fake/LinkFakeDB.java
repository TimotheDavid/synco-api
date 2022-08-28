package timothe.synco.db.fake;

import org.springframework.stereotype.Repository;
import timothe.synco.db.LinkDao;
import timothe.synco.model.Link;
import timothe.synco.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository("fake-link")
public class LinkFakeDB implements LinkDao {
    private List<Link> links = new ArrayList<>();

    @Override
    public List<Link> getAllLinkFromUserId(User user) {
        return links.stream().filter(link -> Objects.equals(link.getUserId(), user.getId())).toList();
    }

    @Override
    public Link getLinkByShortUrl(Link link, User user) {
        return links.stream().filter(linkObject -> Objects.equals(linkObject.getShortUrl(), link.getShortUrl()) && Objects.equals(linkObject.getUserId(), user.getId())).findFirst().orElse(null);
    }

    @Override
    public Link getLinkByShortUrlWithoutUser(Link link) {
        return links.stream().filter(linkObject -> Objects.equals(linkObject.getShortUrl(),link.getShortUrl())).findFirst().orElse(null);
    }

    @Override
    public Link getLinkFromUUID(Link link) {
        return links.stream().filter(linkObject -> Objects.equals(linkObject.getId(), link.getId())).findFirst().orElse(null);
    }

    @Override
    public void create(Link link) {
        links.add(link);
    }



    @Override
    public void delete(Link link, User user) {
        links = links.stream().dropWhile(linkObject -> linkObject.getId() == link.getId() && linkObject.getUserId() == user.getId()).toList();

    }
}
