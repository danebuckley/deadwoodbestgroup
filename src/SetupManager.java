
import java.lang.reflect.Array;
import java.util.*;

import org.w3c.dom.Document;

// For part creation, and perhaps xml parsing.

class SetupManager {

    private ArrayList<Set> setbank;
    private Hashtable<String, IArea> areabank;
    private ArrayList<Scene> scenebank;
    private ArrayList<Integer> sceneOrder = new ArrayList<>();
//    private ArrayList<Role> rolebank;
//    private ArrayList<Role> extrabank;


    private Scene testScene;


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
            players[i].currentArea = areabank.get("Trailer");
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
            players[i].currentArea = areabank.get("Trailer");
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
        // roleBank = new ArrayList<Role>();
        // roleBank.add(new Role("Pied Piper", "I like to eat pies while fixing pipes.", 3));

        // sceneBank = new ArrayList<Scene>();
        // sceneBank.add(new Scene("Pie Time", "So many pies! XD", roleBank));

        // extraRoleBank = new ArrayList<Role>();
        // extraRoleBank.add(new Role("Bunny", "I also like pies :3", 3));

        // setBank = new ArrayList<Set>();
        // setBank.add(new Set("The Forest", extraRoleBank));

        try{
      
            Document testDoc = ParseXML.getDocFromFile("src/XMLTestFile.xml");
            Document cardDoc = ParseXML.getDocFromFile("src/cards.xml");
            Document boardDoc = ParseXML.getDocFromFile("src/board.xml");
            ParseXML.parseSceneCards(cardDoc, scenebank);
            ParseXML.parseBoard(boardDoc, setbank, areabank);
//            ParseXML.parseRoleDataTo(document, rolebank, "rolebank");
//            ParseXML.parseRoleDataTo(document, extrabank, "extrabank");
//            ParseXML.parseSceneDataTo(document, scenebank, rolebank);
//            ParseXML.parseSetDataTo(document, setbank, scenebank, extrabank);


         
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

//        testScene = scenebank.get(0);
//
//        testSet = setbank.get(0);
//        testSet.connectedSets = setbank;
//        testSet.setScene(testScene);
    }
}