import java.awt.*;
import java.util.ArrayList;

public class Office extends Room {
    ArrayList<Upgrade> upgrades;

    public Office(ArrayList<String> defaultNeighbors, Rectangle area, ArrayList<Upgrade> upgrades) {
        this.name = "office";
        this.defaultNeighbors = defaultNeighbors;
        this.area = area;
        this.upgrades = upgrades;
    }
}
