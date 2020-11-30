package ID.deadwood;

import java.util.*;

import org.w3c.dom.Document;

// For part creation, and perhaps xml parsing.

class SetupManager {

    // Aggregators
    private final ArrayList<Set> setbank;
    private final Hashtable<String, Room> areabank;
    private final ArrayList<Scene> scenebank;
    private final ArrayList<Integer> sceneOrder = new ArrayList<>();

    public SetupManager() {
        this.setbank = new ArrayList<>();
        this.areabank = new Hashtable<>();
        this.scenebank = new ArrayList<>();
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

    public void constructPieces() {
        try{

            String filenamePrefix = "src/ID/deadwood/";
            Document testDoc = ParseXML.getDocFromFile(filenamePrefix + "XMLTestFile.xml");
            Document cardDoc = ParseXML.getDocFromFile(filenamePrefix + "cards.xml");
            Document boardDoc = ParseXML.getDocFromFile(filenamePrefix + "board.xml");
            ParseXML.parseSceneCards(cardDoc, scenebank);
            ParseXML.parseBoard(boardDoc, setbank, areabank);

        }catch (Exception e){

            System.out.println("Error = "+e);

        }
        Enumeration<String> keys = areabank.keys();
        while (keys.hasMoreElements()) {
            Room area = areabank.get(keys.nextElement());
            for (int i = 0; i < area.defaultNeighbors.size(); i++) {
                area.connectedAreas.add(areabank.get(area.defaultNeighbors.get(i)));
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

    public void resetPlayers(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            players[i].currentArea = areabank.get("trailer");
            players[i].role = null;
            players[i].working = false;
        }
    }


    // Player Initialization

    Player[] setupPlayers(int num) {
        Player[] players = new Player[num];
        ArrayList<Integer> turnNo = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < num; i++) {
            players[i] = new Player(Integer.toString(i+1));
            players[i].currentArea = areabank.get("trailer");
            System.out.println("Please enter Player " + (i+1) + "'s name:");
            String name = scanner.next();
            players[i].name = name;
            turnNo.add(i);
        }
        Collections.shuffle(turnNo);
        for (int i = 0; i < num; i++) {
            players[i].turnNo = turnNo.get(i);
        }
        return players;
    }
}