
// TODO: add rank costs and

import java.util.ArrayList;

public class CastingManager {
    private int[] optionInts = {2, 3, 4, 5, 6};
    private String[] optionStrings = {"Rank 2 -- 4 / 5", "Rank 3 -- 10 / 10", "Rank 4 -- 18 / 15", "Rank 5 -- 28 / 20", "Rank 6 -- 40 / 25"};
    private int[] dollarPrices = {4, 10, 18, 28, 40};
    private int[] creditPrices = {5, 10, 15, 20, 25};

    void setRankOf(Player player, int rank) {
        player.rank = rank;
    }

    int[] getRankOptions(Player player) {
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

    String[] getRankStrings(Player player) {
        ArrayList<String> options = new ArrayList<>();
        for (int i = 0; i < optionInts.length; i++) {
            if (player.credits >= creditPrices[i] || player.dollars >= dollarPrices[i]) {
                options.add(optionStrings[i]);
            }
        }
        return options.toArray(new String[0]);
    }
}
