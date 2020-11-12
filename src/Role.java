// Just for everything related to a single role.

import java.awt.*;

public class Role {
    String name; // name!
    private String line; // description!
    private int rank; // rank!
    Rectangle area;

    Role(String name, String line, int level, Rectangle area) {
        this.name = name;
        this.line = line;
        this.rank = level;
        this.area = area;
    }

    public int getRank() {
        return this.rank;
    }

}
