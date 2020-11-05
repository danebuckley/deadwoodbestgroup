
public class SetupManager {
    
    public Player[] setupPlayers(int num) {
        Player[] players = new Player[num];
        for (int i = 0; i < num; i++) {
            players[i] = new Player(Integer.toString(i+1));
        }
        return players;
    }

    public void resetBoard() {
        
    }

    private void resetScenes() {

    }

    private void resetTokens() {

    }
}