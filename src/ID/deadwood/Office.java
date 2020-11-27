package ID.deadwood;

import java.awt.*;
import java.util.ArrayList;

class Office extends Room {

    // Aggregators
    ArrayList<Upgrade> upgrades;

    Office(ArrayList<String> defaultNeighbors, Rectangle area, ArrayList<Upgrade> upgrades) {
        this.name = "office";
        this.defaultNeighbors = defaultNeighbors;
        this.area = area;
        this.upgrades = upgrades;
    }
}
