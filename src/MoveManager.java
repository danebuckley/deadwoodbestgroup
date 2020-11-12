import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;


// The ideal place for anything movement related 

public class MoveManager {

    void move(Player player, IArea area) { //needs to ask if they want to take the roles that are available there (Y/N)
        player.currentArea = area;
    }


    // Utilities

    IArea[] getMoveOptions(Player player) {
        return player.currentArea.connectedAreas.toArray(new IArea[0]);
    }

    String[] areasAsStrings(IArea[] areas) {
        String[] strings = new String[areas.length];
        for (int i = 0; i < areas.length; i++) {
            strings[i] = areas[i].name;
        }
        return strings;
    }
}
