package ID.deadwood;

import java.util.ArrayList;
import java.util.Hashtable;

class CastingManager {

    // Finals
    private final int[] optionInts = {2, 3, 4, 5, 6};
    private final String[] optionStrings = {"Rank 2 -- 4 / 5", "Rank 3 -- 10 / 10", "Rank 4 -- 18 / 15", "Rank 5 -- 28 / 20", "Rank 6 -- 40 / 25"};
    private final int[] dollarPrices = {4, 10, 18, 28, 40};
    private final int[] creditPrices = {5, 10, 15, 20, 25};


    // Singleton Functionality

    static CastingManager uniqueInstance = null;

    private CastingManager () { }

    static CastingManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CastingManager();
        }
        return uniqueInstance;
    }


    void setRankOf(Player player, int rank) { //dollars OR credits
        player.hasUpgraded = true;

        switch (rank) {
            case 2: player.dollars -= 4;
                    player.credits -= 5;
                    player.rank = rank;
                    break;
            case 3: player.dollars -= 10;
                    player.credits -= 10;
                    player.rank = rank;
                    break;
            case 4: player.dollars -= 18;
                    player.credits -= 15;
                    player.rank = rank;
                    break;
            case 5: player.dollars -= 28;
                    player.credits -= 20;
                    player.rank = rank;
                    break;
            case 6: player.dollars -= 40;
                    player.credits -= 25;
                    player.rank = rank;
                    break;
        }
    }


    // Get Choices

    int[] getUpgradeOptions(Player player) {
        ArrayList<Integer> options = new ArrayList<>();
        for (int i = 0; i < optionInts.length; i++) {
            if (player.credits >= creditPrices[i] || player.dollars >= dollarPrices[i]) {
                options.add(optionInts[i]);
            }
        }
        int[] intOptions = new int[options.size()];
        for (int i = 0; i < options.size(); i++) {
            intOptions[i] = options.get(i);
        }
        return intOptions;
    }

    String[] getUpgradeStrings(Player player) {
        ArrayList<String> options = new ArrayList<>();
        for (int i = 0; i < optionInts.length; i++) {
            if (player.credits >= creditPrices[i] || player.dollars >= dollarPrices[i]) {
                options.add(optionStrings[i]);
            }
        }
        return options.toArray(new String[0]);
    }
}
