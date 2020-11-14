// The ideal place for anything movement related

public class MoveManager {

    void move(Player player, Room area) { //needs to ask if they want to take the roles that are available there (Y/N)
        player.currentArea = area;
    }


    // Utilities

    Room[] getMoveOptions(Player player) {
        return player.currentArea.connectedAreas.toArray(new Room[0]);
    }

    String[] areasAsStrings(Room[] areas) {
        String[] strings = new String[areas.length];
        for (int i = 0; i < areas.length; i++) {
            strings[i] = areas[i].name;
        }
        return strings;
    }
}
