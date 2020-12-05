package ID.deadwood;

import java.awt.*;

class Upgrade {

    // Finals
    final int level;
    final String currency;
    final int amount;
    final Rectangle area;

    public Upgrade(int level, String currency, int amount, Rectangle area) {
        this.level = level;
        this.currency = currency;
        this.amount = amount;
        this.area = area;
    }
}
