package ID.deadwood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// The ideal place for digging into scenes and roles that have been assigned to a set. (probably)
// This class debatably has the most expansive job in our program.

class SetManager {
    int wrapCount;


    // Singleton Functionality

    static SetManager uniqueInstance = null;

    private SetManager () { }

    static SetManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SetManager();
        }
        return uniqueInstance;
    }


    // Method

    void assignRoleTo(Player player, Role role) {
        if (player.rank >= role.rank) {
            player.role = role;
            player.working = true;
            role.chosen = true;
        }
        else {
            System.out.println("You do not have the rank for this role!");
        }
    }

    private Role[] getRoles(Set set) {
        System.out.println(set.name);
        return set.getRoles();
    }


    // Acting and Rehearsing

    void rehearse(Player player) {
        player.practiceTokens = player.practiceTokens + 1;
        player.hasWorked = true;
    }

    void act(Player player) { //currently assumes that the player on the card
        Set currentSet = (Set) player.currentRoom;
        int budget = currentSet.scene.budget;

        boolean result;
        ArrayList<Integer> diceRoll = handleDice(player, 1);
        result = diceRoll.get(0) >= budget;
        if (result) {
            currentSet.currShots += 1;
            System.out.println("Success! You have removed 1 shot counter.");
            if (player.role.isExtra) {
                player.dollars += 1;
                player.credits += 1;
            }
            else {
                player.credits += 2;
            }
            if (currentSet.maxTakes <= currentSet.currShots) {
                itsAWrap(currentSet);
                currentSet.resetTakes();
            }
        }
        else {
            System.out.println("You failed :( Try again next turn.");
            if (player.role.isExtra) {
                player.dollars += 1;
            }
        }

        player.hasWorked = true;
    }


    // Scoring and Resetting

    public void itsAWrap(Set currentSet) {

        System.out.print("That's a wrap! You've completed the scene. ");

        for (int i = 0; i < currentSet.playerList.size(); i++) {
            Player player = currentSet.playerList.get(i);
            System.out.println(player.name);
            Scene scene = currentSet.scene;
            ArrayList<Role> roles = new ArrayList<>(Arrays.asList(scene.getRoles()));
            int pos = roles.indexOf(player.role);
            payOut(player, scene.budget, currentSet.getRoles().length, pos);
            player.working = false;
            player.practiceTokens = 0;
            player.role.chosen = false;
            player.role = null;
        }
        wrapCount++;
        currentSet.isWrapped = true;
    }

    void payOut(Player player, int budget, int numRoles, int pos) { //ASSUMES PLAYER IS ON SCENE AND NOT EXTRA ROLE, does not include bonuses
        ArrayList<Integer> diceRoll = handleDice(player, budget);
        if (player.role.isExtra) {
            player.dollars += player.role.rank;
        }
        else {
            for (int i = (pos - 1); i < diceRoll.size(); i = i + numRoles) {
                try {
                    player.dollars = player.dollars + diceRoll.get(i);
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }
            }
        }
    }


    // Get Choices

    Role[] getRoleOptions(Player player) {
        Set currSet = (Set) player.currentRoom;
        Role[] allRoles = getRoles(currSet);

        ArrayList<Role> availRoles = new ArrayList<Role>(Arrays.asList(allRoles));

        for (int i = 0; i < availRoles.size(); i++) {
            Role role = availRoles.get(i);
            if (role.chosen) {
                availRoles.remove(role);
            }
        }
        
        return availRoles.toArray(new Role[availRoles.size()]);
    }

    String[] rolesAsStrings(Role[] roles) {
        String[] strings = new String[roles.length + 1];
        for (int i = 0; i < roles.length + 1; i++) {
            if (i < roles.length) {
                strings[i] = roles[i].name + " (" + roles[i].rank + ")";
            }
            else {
                strings[i] = "Go Back";
            }
        }
        return strings;
    }


    // Randomization

    ArrayList<Integer> handleDice(Player player, int numDice) {
        ArrayList<Integer> diceRoll = new ArrayList<>();
        for (int i = 0; i < numDice; i++) {
            Random random = new Random();
            int currentRand = random.nextInt(100) + 1;
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


    // Utility

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