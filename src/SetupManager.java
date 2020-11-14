
import java.lang.reflect.Array;
import java.util.*;

import org.w3c.dom.Document;

// For part creation, and perhaps xml parsing.

class SetupManager {

    private ArrayList<Set> setbank;
    private Hashtable<String, IArea> areabank;
    private ArrayList<Scene> scenebank;
    private ArrayList<Integer> sceneOrder = new ArrayList<>();

    public SetupManager() {
        this.setbank = new ArrayList<>();
        this.areabank = new Hashtable<>();
        this.scenebank = new ArrayList<>();
//        this.rolebank = new ArrayList<Role>();
//        this.extrabank = new ArrayList<Role>();
    }

    void initializeGame() {
        for (int i = 0; i < 40; i++) {
            sceneOrder.add(i);
        }
        Collections.shuffle(sceneOrder);
        constructPieces();
        distributeScenes(sceneOrder);
    }


    // Reseters-- for each game played on the same board.

    public void resetPlayers(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            players[i].currentArea = areabank.get("trailer");
            players[i].role = null;
            players[i].working = false;
        }
    }

    private void distributeScenes(ArrayList<Integer> sceneOrder) {
        for (int i = 0; i < 10; i++) {
            setbank.get(i).setScene(scenebank.get(sceneOrder.get(i)));
        }
        for (int i = 0; i < 10; i++) {
            sceneOrder.remove(0);
        }
    }


    // Initialization

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

    public void constructPieces() {
        try{
      
            Document testDoc = ParseXML.getDocFromFile("src/XMLTestFile.xml");
            Document cardDoc = ParseXML.getDocFromFile("src/cards.xml");
            Document boardDoc = ParseXML.getDocFromFile("src/board.xml");
            ParseXML.parseSceneCards(cardDoc, scenebank);
            ParseXML.parseBoard(boardDoc, setbank, areabank);

         }catch (Exception e){
         
            System.out.println("Error = "+e);
         
         }
        Enumeration<String> keys = areabank.keys();
        while (keys.hasMoreElements()) {
            IArea area = areabank.get(keys.nextElement());
            for (int i = 0; i < area.defaultNeighbors.size(); i++) {
                area.connectedAreas.add(areabank.get(area.defaultNeighbors.get(i)));
            }
        }
    }
}