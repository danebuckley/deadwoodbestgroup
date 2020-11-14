// Just for everything related to a single role.

import java.awt.*;

public class Role {
    String name; // name!
    private String line; // description!
    private int rank; // rank!
    boolean isExtra;
    boolean chosen = false;
    Rectangle area;

    Role(String name, String line, int level, Rectangle area, boolean isExtra) {
        this.name = name;
        this.line = line;
        this.rank = level;
        this.area = area;
        this.isExtra = isExtra;
    }

    public int getRank() {
        return this.rank;
    }

}
