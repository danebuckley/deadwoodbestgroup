import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Office extends IArea {
    ArrayList<Upgrade> upgrades;
    //String name;
    //ArrayList<String> defaultNeighbors;
    //ArrayList<IArea> connectedAreas = new ArrayList<>();
    //Rectangle area;

    public Office(ArrayList<String> defaultNeighbors, Rectangle area, ArrayList<Upgrade> upgrades) {
        this.name = "Office";
        this.defaultNeighbors = defaultNeighbors;
        this.area = area;
    }
}
