package ID.deadwood;

import java.awt.*;
import java.util.ArrayList;

class Trailer extends Room {

    public Trailer(ArrayList<String> defaultNeighbors, Rectangle area) {
        this.defaultNeighbors = defaultNeighbors;
        this.area = area;
        this.name = "trailer";
    }
}
