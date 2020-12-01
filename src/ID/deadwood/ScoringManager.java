package ID.deadwood;

import java.util.ArrayList;

// For scoring and stuff... perhaps the ideal place for anything that needs to change numbers on multiple players?
class ScoringManager {

    // Singleton Functionality

    static ScoringManager uniqueInstance = null;

    private ScoringManager () { }

    static ScoringManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ScoringManager();
        }
        return uniqueInstance;
    }


    // Methods

    // Calculates final scores of players.
    public ArrayList<Player> scoreGame(Player[] players) {
        ArrayList<Player> finalList = new ArrayList<>();
        for (int i = 0; i < players.length; i++) {
            players[i].finalScore = players[i].finalScore + players[i].dollars;
            players[i].finalScore = players[i].finalScore + players[i].credits;
            players[i].finalScore = players[i].finalScore + (players[i].rank*5);
            finalList.add(players[i]);
        }
        return finalList;
    }

    // Orders players based on final score to determine winner.
    public void endScoring(ArrayList<Player> finalList){
            boolean isSorted = false;
            Player temp;
            while (!isSorted) {
                isSorted = true;
                for (int i = 0; i < finalList.size() - 1; i++) {
                    if (finalList.get(i).finalScore < finalList.get(i+1).finalScore) {
                        temp = finalList.get(i);
                        finalList.set(i, finalList.get(i+1));
                        finalList.set(i+1, temp);
                        isSorted = false;
                    }
                }
            }
                bubbleSort(finalList);
            System.out.println("And the winner is... " + finalList.get(0).name);
    }


    // Utility

    private static void bubbleSort(ArrayList<Player> a) {
        boolean isSorted = false;
        Player temp;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < a.size() - 1; i++) {
                if (a.get(i).finalScore < a.get(i+1).finalScore) {
                    temp = a.get(i);
                    a.set(i, a.get(i+1));
                    a.set(i+1, temp);
                    isSorted = false;
                }
            }
        }
    }
    }

