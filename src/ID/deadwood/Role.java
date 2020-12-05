package ID.deadwood;// Just for everything related to a single role.

import java.awt.*;

class Role {

    // Finals
    final String name; // name!
    final String line; // description!
    final int rank; // rank!
    final Rectangle area;
    final boolean isExtra;

    // State
    boolean chosen = false;

    Role(String name, String line, int level, Rectangle area, boolean isExtra) {
        this.name = name;
        this.line = line;
        this.rank = level;
        this.area = area;
        this.isExtra = isExtra;
    }
}
