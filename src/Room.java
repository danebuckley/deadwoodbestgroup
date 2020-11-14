
import java.awt.*;
import java.util.ArrayList;

public class Room {
    String name;
    ArrayList<String> defaultNeighbors = new ArrayList<>();
    ArrayList<Room> connectedAreas = new ArrayList<>();
    Rectangle area;
    ArrayList<Player> playerList = new ArrayList<>();
}
