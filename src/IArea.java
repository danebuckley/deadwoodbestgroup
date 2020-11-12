import java.awt.*;
import java.util.ArrayList;

public interface IArea {

    String name = "";
    ArrayList<String> defaultNeighbors = null;
    ArrayList<IArea> connectedAreas = null;
    Rectangle area = null;
    ArrayList<Player> playerList = new ArrayList<>();

}
