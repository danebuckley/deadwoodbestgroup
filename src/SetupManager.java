
import java.util.ArrayList;
import java.util.Collections;

import org.w3c.dom.Document;

// For part creation, and perhaps xml parsing.

class SetupManager {

    private ArrayList<Set> setbank;
    private ArrayList<Scene> scenebank;
    private ArrayList<Role> rolebank;
    private ArrayList<Role> extrabank;

    private Set testSet;
    private Scene testScene;


    void initializeGame() {
        constructPieces();
        resetBoard();
        resetScenes();
    }


    // Reseters-- for each game played on the same board.

    private void resetBoard() {

    }

    private void resetScenes() {

    }


    // Initialization

    Player[] setupPlayers(int num) {
        Player[] players = new Player[num];
        ArrayList<Integer> turnNo = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            players[i] = new Player(Integer.toString(i+1));
            players[i].currentSet = testSet;
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

        // testScene = sceneBank.get(0);

        // testSet = setBank.get(0);
        // testSet.connectedSets = setBank;
        // testSet.setScene(testScene);

        Document document;
        try{
      
            document = ParseXML.getDocFromFile("XMLTestFile.xml");
            ParseXML.parseRoleDataTo(document, rolebank, "rolebank");
            ParseXML.parseRoleDataTo(document, extrabank, "extrabank");
            ParseXML.parseSceneDataTo(document, scenebank, rolebank);
            ParseXML.parseSetDataTo(document, setbank, scenebank, extrabank);
         
         }catch (Exception e){
         
            System.out.println("Error = "+e);
         
         }
        
    }
}