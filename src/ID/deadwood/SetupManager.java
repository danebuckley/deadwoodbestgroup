package ID.deadwood;

import java.util.*;

import org.w3c.dom.Document;

// For part creation, and perhaps xml parsing.

class SetupManager {

    // Aggregators
    final ArrayList<Set> setbank;
    final Hashtable<String, Room> roombank;
    private final ArrayList<Scene> scenebank;
    private final ArrayList<Integer> sceneOrder = new ArrayList<>();


    // Singleton Functionality

    static SetupManager uniqueInstance = null;

    private SetupManager () {
        this.setbank = new ArrayList<>();
        this.roombank = new Hashtable<>();
        this.scenebank = new ArrayList<>();
    }

    static SetupManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SetupManager();
        }
        return uniqueInstance;
    }


    // Initialization

    void initializeGame() {
        for (int i = 0; i < 40; i++) {
            sceneOrder.add(i);
        }
        Collections.shuffle(sceneOrder);
        constructPieces();
        distributeScenes(sceneOrder);
    }

    void constructPieces() {
        try{

            String filenamePrefix = "src/ID/deadwood/";
            Document testDoc = ParseXML.getDocFromFile(filenamePrefix + "XMLTestFile.xml");
            Document cardDoc = ParseXML.getDocFromFile(filenamePrefix + "cards.xml");
            Document boardDoc = ParseXML.getDocFromFile(filenamePrefix + "board.xml");
            ParseXML.parseSceneCards(cardDoc, scenebank);
            ParseXML.parseBoard(boardDoc, setbank, roombank);

        }catch (Exception e){

            System.out.println("Error = "+e);

        }
        Enumeration<String> keys = roombank.keys();
        while (keys.hasMoreElements()) {
            Room area = roombank.get(keys.nextElement());
            for (int i = 0; i < area.defaultNeighbors.size(); i++) {
                area.connectedAreas.add(roombank.get(area.defaultNeighbors.get(i)));
            }
        }
    }

    private void distributeScenes(ArrayList<Integer> sceneOrder) {
        for (int i = 0; i < 10; i++) {
            setbank.get(i).scene =  scenebank.get(sceneOrder.get(i));
        }
        for (int i = 0; i < 10; i++) {
            sceneOrder.remove(0);
        }
    }


    // Player Reset.

    void resetPlayers(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            players[i].currentRoom = roombank.get("trailer");
            players[i].role = null;
            players[i].working = false;
        }
    }

    void resetRooms() {
        for (Set set : setbank) {
            set.isWrapped = false;
        }
    }


    // Player Initialization

    Player[] setupPlayers(int num) {
        Player[] players = new Player[num];
        ArrayList<Integer> turnNo = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < num; i++) {
            players[i] = new Player(Integer.toString(i+1));
            players[i].currentRoom = roombank.get("trailer");
            players[i].name = "Player " + Integer.toString(i+1);
            turnNo.add(i);
        }
        Collections.shuffle(turnNo);
        for (int i = 0; i < num; i++) {
            players[i].turnNo = turnNo.get(i);
        }

        // Initial currency and ranks
        if (num == 5) {
            for (int i = 0; i < players.length; i++) {
                players[i].credits += 2;
            }
        } else if (num == 6) {
            for (int i = 0; i < players.length; i++) {
                players[i].credits += 4;
            }
        } else if (num == 7 || num == 8) {
            for (int i = 0; i < players.length; i++) {
                players[i].rank = 2;
            }
        }

        return players;
    }
}