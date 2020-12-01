package ID.deadwood;// The ideal place for anything movement related

class MoveManager {

    // Singleton Functionality

    static MoveManager uniqueInstance = null;

    private MoveManager () { }

    static MoveManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MoveManager();
        }
        return uniqueInstance;
    }


    // Methods

    void move(Player player, Room area) {
        player.currentArea = area;
        player.hasMoved = true;
    }


    // Get Choices

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
