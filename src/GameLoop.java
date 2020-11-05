import java.io.IOException;

class GameLoop {
    
    // Managers
    private SetupManager setupManager;
    private SetManager setManager;
    private MoveManager moveManager;
    private ScoringManager scoreManager;
    private UI ui;
    private CastingManager castingManager;

    private Player[] players;

    // Settings
    private int numPlayers;

    // State
    private int currPlayer;
    private boolean turnOver;
    private boolean dayOver;
    private boolean gameOver;

    GameLoop() {
        setupManager = new SetupManager();
        setManager = new SetManager();
        moveManager = new MoveManager();
        scoreManager = new ScoringManager();
        ui = new UI(this);
        castingManager = new CastingManager();
    }

    void runGame(int numPlayers) throws IOException {

        setupManager.initializeGame();

        this.numPlayers = numPlayers;
        players = setupManager.setupPlayers(numPlayers);

        gameLoop();
        
        scoreManager.scoreGame();
    }


    // Game Loops

    private void gameLoop() throws IOException {
        gameOver = false;
        while (!gameOver) {
            dayLoop();
            gameOver = true;
        }
    }

    private void dayLoop() throws IOException {
        currPlayer = 0;
        dayOver = false;
        while (!dayOver) {
            turnLoop();
            currPlayer += 1;
            dayOver = true;
        }
    }

    private void turnLoop() throws IOException {
        turnOver = false;
        while (!turnOver) {
            print("\nPicking Action...");
            String[] actionStrings = getActionsOf(players[currPlayer]);
            UIAction action = ui.handlePlayerAction("Action", actionStrings);
            Player player = players[currPlayer];
            switch (action.type) {
                case "Move": chooseMove(player); break;
                case "Choose Role": chooseRole(player); break;
                case "Act": chooseAct(player); break;
                case "Rehearse": chooseRehearse(player); break;
                case "Upgrade": chooseUpgrade(player); break;
                case "End Turn": chooseEndTurn(); break;
            }

            // turnOver = true;
        }
    }


    // Choices

    private void chooseMove(Player player) throws IOException {
        print("\nMoving...");
        Set[] sets = moveManager.getMoveOptions(player);
        String[] options = moveManager.setsAsStrings(sets);
        UIAction action = ui.handlePlayerAction("Move", options);
        if (sets.length > 0) {
            moveManager.move(player, sets[action.index]);
        }
    }

    private void chooseRole(Player player) throws IOException {
        print("\nChoosing Role...");
        Role[] roles = setManager.getRoleOptions(player);
        String[] options = setManager.rolesAsStrings(roles);
        UIAction action = ui.handlePlayerAction("Role", options);
        if (roles.length > 0) {
            setManager.assignRoleTo(player, roles[action.index]);
        }
    }

    private void chooseAct(Player player) {
        print("Acting...");
    }

    private void chooseRehearse(Player player) {
        print("Rehearsing...");
    }

    private void chooseUpgrade(Player player) throws IOException {
        print("\nUpgrading...");
        int[] ranks = castingManager.getRankOptions(player);
        String[] options = castingManager.getRankStrings(player);
        UIAction action = ui.handlePlayerAction("Upgrade", options);
        if (ranks.length > 0) {
            castingManager.setRankOf(player, ranks[action.index]);
        }
    }

    private void chooseEndTurn() {
        print("Ending Turn...");
        turnOver = true;
    }


    // Get Choices

    private String[] getActionsOf(Player player) {
        return new String[]{"Move", "Choose Role", "Act", "Rehearse", "Upgrade", "End Turn"};
    }


    // Utility
    private void print(String string) {
        System.out.println(string);
    }


    private static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}