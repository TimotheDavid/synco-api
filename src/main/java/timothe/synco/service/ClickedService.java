package timothe.synco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import timothe.synco.db.ClickedDao;
import timothe.synco.model.Clicked;
import timothe.synco.model.Link;

import java.util.List;

@Service
public class ClickedService {

    @Autowired
    @Qualifier("fake-clicked")
    ClickedDao clicker;


    public void add(String host, String platform, Link link) {
        Clicked clicked = Clicked.builder().host(host).platform(platform).linkId(link.getId()).build();
        clicker.add(clicked);
    }

    public List<Clicked> getAllClickedByLink(Link link) {
        return clicker.getAllClickedForALink(link);
    }

}
