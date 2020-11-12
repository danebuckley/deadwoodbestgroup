

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

// NOTE: sets function in a sort of linked-list fashion; there is no map of sets, as they can be accessed through one another.

public class Set implements IArea {
    String name;
    ArrayList<String> defaultNeighbors = null;
    private Rectangle area;
    private ArrayList<Integer> takeNums;
    private ArrayList<Rectangle> takeAreas;
    private ArrayList<Role> extraRoles;
    private int maxShots;

    ArrayList<IArea> connectedAreas = null;

    private Scene scene;
    private int shotCounters;
    ArrayList<Player> players;

    Set (String name, ArrayList<String> neighborStrings, Rectangle area, ArrayList<Integer> takeNums, ArrayList<Rectangle> takeAreas, ArrayList<Role> parts) {
        this.name = name;
        this.defaultNeighbors = neighborStrings;
        this.area = area;
        this.takeNums = takeNums;
        this.takeAreas = takeAreas;
        this.extraRoles = parts;
        this.maxShots = takeNums.size();

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

    public void payOut(Player player, int budget, int numRoles, int pos) { //ASSUMES PLAYER IS ON SCENE AND NOT EXTRA ROLE, does not include bonuses
        ArrayList<Integer> diceRoll = handleDice(player, budget);
        for (int i = (pos - 1); i < diceRoll.size(); i = i + numRoles) {
            try {
                player.dollars = player.dollars + diceRoll.get(i);
            } catch (ArrayIndexOutOfBoundsException e) {
                return;
            }
        }
    }

    public boolean act(Player player, int budget) { //currently assumes that the player on the card
        ArrayList<Integer> diceRoll = handleDice(player, 1);
        if (diceRoll.get(0) > budget) {
            player.credits = player.credits + 2;
            return true;
        } else {
            return false;
        }
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