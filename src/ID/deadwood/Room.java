package ID.deadwood;

import java.awt.*;
import java.util.ArrayList;

class Room {

    // "Finals"
    String name;
    ArrayList<String> defaultNeighbors = new ArrayList<>();
    ArrayList<Room> connectedAreas = new ArrayList<>();
    Rectangle area;
    ArrayList<Player> playerList = new ArrayList<>();
}
