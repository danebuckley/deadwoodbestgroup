package ID.deadwood;

import java.awt.*;
import java.util.ArrayList;

// NOTE: sets function in a sort of linked-list fashion; there is no map of sets, as they can be accessed through one another.

class Set extends Room {

    // Finals
    final ArrayList<Integer> takeNums;
    final ArrayList<Rectangle> takeAreas;
    private final ArrayList<Role> extraRoles;
    final int maxTakes;

    // State
    boolean isWrapped;
    Scene scene;
    int currShots;

    Set (String name, ArrayList<String> neighborStrings, Rectangle area, ArrayList<Integer> takeNums, ArrayList<Rectangle> takeAreas, ArrayList<Role> parts) {
        this.name = name;
        this.defaultNeighbors = neighborStrings;
        this.area = area;
        this.takeNums = takeNums;
        this.takeAreas = takeAreas;
        this.extraRoles = parts;
        this.maxTakes = takeNums.size();
        this.connectedAreas = new ArrayList<>();
        this.currShots = 0;
        this.playerList = new ArrayList<>();
        this.isWrapped = false;
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

    void resetTakes() {
        this.currShots = 0;
    }

    void addPlayer(Player player) {
        playerList.add(player);
    }
}