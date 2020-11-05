package deadwoodbestgroup.src;

public class GameLoop {
    
    // Managers
    private SetupManager setupManager;
    private SetManager setManager;
    private MoveManager moveManager;
    private ScoringManager scoreManager;
    private UI ui;

    private Player[] players;

    // Settings
    private int numPlayers;

    // State
    private int currPlayer;
    private boolean turnOver;
    private boolean dayOver;
    private boolean gameOver;

    public GameLoop() {
        setupManager = new SetupManager();
        setManager = new setManager();
        moveManager = new moveManager();
        scoreManager = new scoreManager();
    }

    public void runGame(int numPlayers) {
        this.numPlayers = numPlayers;
        players = setupManager.setupPlayers(numPlayers);
        setupManager.resetBoard();

        gameLoop();
        
        scoreManager.scoreGame();
    }

    private void gameLoop() {
        gameOver = false;
        while (!gameOver) {
            dayLoop();
            gameOver = true;
        }
    }

    private void dayLoop() {
        currPlayer = 0;
        dayOver = false;
        while (!dayOver) {
            turnLoop();
            dayOver = true;
        }
    }

    private void turnLoop() {
        turnOver = false;
        while (!turnOver) {
            String[] actions = getActionsOf(player[currPlayer]);
            String action = ui.promptPlayerActions(actions);
            switch (action) {
                case "Move": print("Moving Player..."); break;
                case "Choose Role": print("Choosing Role..."); break;
                case "Act": print("Acting..."); break;
                case "Rehearse": print("Rehearsing..."); break;
                case "Upgrade": print("Upgrading..."); break;
                case "End Turn": print("Ending Turn..."); turnOver = true; break;
            }

            // turnOver = true;
        }
    }

    private void moveAction(Player player) {

    }

    private void chooseAction(Player player) {

    }

    private void chooseAct(Player player) {

    }

    private void chooseRehearse(Player player) {

    }

    private void chooseUpgrade(Player player) {

    }

    private void chooseEndTurn(Player player) {

    }
    

    public String[] getActionsOf(Player player) {
        return new String[1];
    }

    public String[] getMoveOptions(Player player) {
        return new String[1];
    }

    public String[] getRoleOptions(Player player) {
        return new String[1];
    }

    public String[] getUpgradeOptions(Player player) {
        return new String[1];
    }

    // Utility
    private void print(String string) {
        System.out.println(string);
    }
}