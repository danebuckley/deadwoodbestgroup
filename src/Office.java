import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Office extends IArea {
    ArrayList<Upgrade> upgrades;

    public Office(ArrayList<String> defaultNeighbors, Rectangle area, ArrayList<Upgrade> upgrades) {
        this.name = "office";
        this.defaultNeighbors = defaultNeighbors;
        this.area = area;
    }
}
