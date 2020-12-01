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
        Set currentSet = (Set) player.currentArea;
        int budget = currentSet.scene.budget;
        currentSet.currTakes += 1;

        boolean result;
        ArrayList<Integer> diceRoll = handleDice(player, 1);
        if (diceRoll.get(0) >= budget) {
            if (player.role.isExtra) {
                player.dollars += 1;
                player.credits += 1;
            }
            else {
                player.credits += 2;
            }
            result = true;
        }
        else {
            if (player.role.isExtra) {
                player.dollars += 1;
            }
            result = false;
        }

        if (result) {
            System.out.println("Success! You have removed 1 shot counter.");
            if (currentSet.maxTakes == currentSet.currTakes) {
                itsAWrap(currentSet);
                currentSet.resetTakes();
                System.out.print("That's a wrap! You've completed the scene.");
            }
        }
        else {
            System.out.println("You failed :( Try again next turn.");
        }

        player.hasWorked = true;
    }


    // Scoring and Resetting

    public void itsAWrap(Set currentSet) {

        for (int i = 0; i < currentSet.playerList.size(); i++) {
            Player player = currentSet.playerList.get(i);
            Scene scene = currentSet.scene;
            ArrayList<Role> roles = new ArrayList<>(Arrays.asList(scene.getRoles()));
            int pos = roles.indexOf(player.role);
            payOut(player, scene.budget, currentSet.getRoles().length, pos);
            player.working = false;
            player.practiceTokens = 0;
        }
        wrapCount++;
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
        player.role.chosen = false;
        player.role = null;
        player.working = false;
    }


    // Get Choices

    Role[] getRoleOptions(Player player) {
        Set currSet = (Set) player.currentArea;
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
        String[] strings = new String[roles.length];
        for (int i = 0; i < roles.length; i++) {
            strings[i] = roles[i].name;
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