import java.awt.*;
import java.util.ArrayList;

public class Trailer implements IArea {

    ArrayList<String> defaultNeighbors = null;
    ArrayList<Set> connectedSets = null;
    Rectangle area = null;

    public Trailer(ArrayList<String> defaultNeighbors, Rectangle area) {
        this.defaultNeighbors = defaultNeighbors;
        this.area = area;
    }
}