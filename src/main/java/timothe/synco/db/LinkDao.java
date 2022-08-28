package timothe.synco.db;

import timothe.synco.error.HttpExceptions;
import timothe.synco.model.Link;
import timothe.synco.model.User;

import java.util.List;

public interface LinkDao {

    public List<Link> getAllLinkFromUserId(User user);

    public Link getLinkByShortUrl(Link link, User user);

    public Link getLinkByShortUrlWithoutUser(Link link);
   public Link  getLinkFromUUID(Link link);
    public void create(Link link) throws HttpExceptions;
    public void delete(Link link, User user);
}
