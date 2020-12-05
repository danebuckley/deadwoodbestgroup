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

    void move(Player player, Room room) {
        Room curRoom = player.currentRoom;
        curRoom.playerList.remove(player);

        player.currentRoom = room;
        room.playerList.add(player);
        System.out.println(room.playerList.get(0));
        System.out.println(room.name);
        player.hasMoved = true;
    }


    // Get Choices

    Room[] getMoveOptions(Player player) {
        return player.currentRoom.connectedAreas.toArray(new Room[0]);
    }

    String[] areasAsStrings(Room[] areas) {
        String[] strings = new String[areas.length+1];
        for (int i = 0; i < areas.length + 1; i++) {
            if (i < areas.length) {
                strings[i] = areas[i].name;
            }
            else {
                strings[i] = "Go Back";
            }
        }
        return strings;
    }
}
