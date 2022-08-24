package timothe.synco.db.fake;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import timothe.synco.db.ClickedDao;
import timothe.synco.model.Clicked;
import timothe.synco.model.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@Qualifier("fake-clicked")
public class CLickedFakeDB implements ClickedDao {

    private List<Clicked> clickers = new ArrayList<>();

    @Override
    public void add(Clicked clicked) {
        clickers.add(clicked);
    }

    @Override
    public List<Clicked> getAllClickedForALink(Link link) {
        return clickers.stream().filter(click -> Objects.equals(click.getLinkId(), link.getId())).toList();
    }


}
