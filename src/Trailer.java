import java.awt.*;
import java.util.ArrayList;

public class Trailer implements IArea {

    String name = "Trailer";
    ArrayList<String> defaultNeighbors;
    ArrayList<IArea> connectedAreas = new ArrayList<>();
    Rectangle area;
    ArrayList<Player> playerList = new ArrayList<>();


    public Trailer(ArrayList<String> defaultNeighbors, Rectangle area) {
        this.defaultNeighbors = defaultNeighbors;
        this.area = area;

    }
}
