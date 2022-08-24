package timothe.synco.db;

import timothe.synco.model.Clicked;
import timothe.synco.model.Link;

import java.util.List;

public interface ClickedDao {

   void  add(Clicked clicked);

  List<Clicked> getAllClickedForALink(Link link);
}
