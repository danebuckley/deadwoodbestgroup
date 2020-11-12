import java.awt.*;
import java.util.ArrayList;

public class Office implements IArea {

    String name = "Office";
    ArrayList<String> defaultNeighbors;
    ArrayList<IArea> connectedAreas = new ArrayList<>();
    Rectangle area;

    public Office(ArrayList<String> defaultNeighbors, Rectangle area) {
        this.defaultNeighbors = defaultNeighbors;
        this.area = area;
    }
}
