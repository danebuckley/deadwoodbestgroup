import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

// NOTE: sets function in a sort of linked-list fashion; there is no map of sets, as they can be accessed through one another.

public class Set {
    String name;
    private Scene scene;
    private ArrayList<Role> extraRoles;
    ArrayList<Player> players;
    ArrayList<Set> connectedSets;
    private int maxShots;
    private int shotCounters;

    Set (String name, int maxShots, ArrayList<Role> extraRoles) {
        this.name = name;
        this.extraRoles = extraRoles;
        this.maxShots = maxShots;
        this.shotCounters = 0;
        this.players = new ArrayList<Player>();
    }

    void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return this.scene;
    }

    Role[] getRoles() {
        Role[] sceneRoles = scene.getRoles();
        int fal = sceneRoles.length;
        int sal = extraRoles.size();
        Role[] allRoles = new Role[fal + sal];
        System.arraycopy(sceneRoles, 0, allRoles, 0, fal);
        System.arraycopy(extraRoles.toArray(), 0, allRoles, fal, sal);
        return allRoles;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void payOut(Player player, int budget, int numRoles, int pos){
        handleDice(player, budget);
    }

    public void act(Player player) {

    }

    public void rehearse(Player player) { //on scene completion the players practice tokens need to be reset
        player.practiceTokens = player.practiceTokens + 1;
    }

    public ArrayList<Integer> handleDice(Player player, int numDice) {
        ArrayList<Integer> diceRoll = new ArrayList<>();
        for (int i = 0; i < numDice; i++) {
            Random random = new Random();
            int currentRand = random.nextInt(6) + 1;
            diceRoll.add(currentRand);
        }
        bubbleSort(diceRoll);
        if (player.practiceTokens > 0) {
            for (int i = 0; i < numDice; i++) {
                diceRoll.set(i, diceRoll.get(i) + 1);
            }
        }
        return diceRoll;
    }

    private static void bubbleSort(ArrayList<Integer> a) {
        boolean isSorted = false;
        int temp;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < a.size() - 1; i++) {
                if (a.get(i) < a.get(i+1)) {
                    temp = a.get(i);
                    a.set(i, a.get(i+1));
                    a.set(i+1, temp);
                    isSorted = false;
                }
            }
        }
    }

}