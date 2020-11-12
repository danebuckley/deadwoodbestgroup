import java.awt.*;
import java.util.ArrayList;

public class Office implements IArea {

    ArrayList<String> defaultNeighbors = null;
    ArrayList<Set> connectedSets = null;
    Rectangle area = null;

    public Office(ArrayList<String> defaultNeighbors, Rectangle area) {
        this.defaultNeighbors = defaultNeighbors;
        this.area = area;
    }
}
