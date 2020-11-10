import java.util.ArrayList;

// For part creation, and perhaps xml parsing.

class SetupManager {

    private ArrayList<Set> setBank;
    private ArrayList<Scene> sceneBank;
    private ArrayList<Role> roleBank;
    private ArrayList<Role> extraRoleBank;

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
        for (int i = 0; i < num; i++) {
            players[i] = new Player(Integer.toString(i+1));
            players[i].currentSet = testSet;
        }
        return players;
    }

    public void constructPieces() {
        roleBank = new ArrayList<Role>();
        roleBank.add(new Role("Pied Piper", "I like to eat pies while fixing pipes.", 3));

        sceneBank = new ArrayList<Scene>();
        sceneBank.add(new Scene("Pie Time", "So many pies! XD", roleBank));

        extraRoleBank = new ArrayList<Role>();
        extraRoleBank.add(new Role("Bunny", "I also like pies :3", 3));

        setBank = new ArrayList<Set>();
        setBank.add(new Set("The Forest", extraRoleBank.toArray(new Role[0])));

        testScene = sceneBank.get(0);

        testSet = setBank.get(0);
        testSet.connectedSets = setBank;
        testSet.setScene(testScene);
    }
}