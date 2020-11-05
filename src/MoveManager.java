import java.util.ArrayList;
import java.util.Hashtable;


public class MoveManager {

    void move(Player player, Set set) {

    }

    public void act() {

    }

    public void work() {

    }

    public void upgrade() {

    }

    public void rehearse() {

    }


    // Utilities

    Set[] getMoveOptions(Player player) {
        return player.currentSet.connectedSets.toArray(new Set[0]);
    }

    String[] setsAsStrings(Set[] sets) {
        String[] strings = new String[sets.length];
        for (int i = 0; i < sets.length; i++) {
            strings[i] = sets[i].name;
        }
        return strings;
    }
}
