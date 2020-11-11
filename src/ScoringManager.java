import java.util.ArrayList;

// For scoring and stuff... perhaps the ideal place for anything that needs to change numbers on multiple players?

public class ScoringManager {
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
        }
    }

