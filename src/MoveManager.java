import java.util.ArrayList;
import java.util.Hashtable;


// The ideal place for anything movement related 

public class MoveManager {

    void move(Player player, Set set) {

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
