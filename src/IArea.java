import java.awt.*;
import java.util.ArrayList;

public interface IArea {

    String name = "";
    ArrayList<String> defaultNeighbors = new ArrayList<>();
    ArrayList<IArea> connectedAreas = new ArrayList<>();
    Rectangle area = null;
    ArrayList<Player> playerList = new ArrayList<>();
}
