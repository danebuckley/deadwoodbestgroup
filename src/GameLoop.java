import java.io.IOException;
import java.util.ArrayList;

// The core class; focused mainly on the general gameloops and player actions.

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
        ui = new UI();
        castingManager = new CastingManager();
    }

    void runGame(int numPlayers) throws IOException {

        setupManager.initializeGame();

        this.numPlayers = numPlayers;
        players = setupManager.setupPlayers(numPlayers);
        turnSort(players);
        gameLoop(numPlayers);
        
        scoreManager.scoreGame(players); //this needs to receive the player array as input
    }


    // Game Loops

    private void gameLoop(int numPlayers) throws IOException {
        int numDays = 4;
        if (numPlayers == 2 || numPlayers == 3) {
            numDays = 3;
        } else if (numPlayers == 5) {
            for (int i = 0; i < players.length; i++) {
                players[i].credits += 2;
            }
        } else if (numPlayers == 6) {
            for (int i = 0; i < players.length; i++) {
                players[i].credits += 4;
            }
        } else if (numPlayers == 7 || numPlayers == 8) {
            for (int i = 0; i < players.length; i++) {
                players[i].rank = 2;
            }
        }
        for (int i = 0; i < numDays; i++) {
            dayLoop();
        }
    }

    private void dayLoop() throws IOException {
        currPlayer = -1;
        dayOver = false;
        // Loop should finish after itsAWrap is called in SetManager (if there is only one scene left).
        while (!dayOver) {
            currPlayer += 1;
            if (currPlayer == players.length) {
                currPlayer = 0;
            }
            turnLoop();
            if (setManager.wrapCount == 9) {
                dayOver = true;
                setupManager.resetPlayers(players);
            }
        }
    }


    private void turnLoop() throws IOException {
        turnOver = false;

        Player player = players[currPlayer];

        player.hasMoved = false;
        player.hasWorked = false;
        player.hasUpgraded = false;

        System.out.println(players[currPlayer].name + "'s turn:");
        while (!turnOver) {
            print("\nPicking Action...");

            String[] actionStrings = getActionsOf(player);
            UIAction action = ui.handlePlayerAction("Action", actionStrings);
            switch (action.type) {
                case "Move": chooseMove(player); break;
                case "Choose Role": chooseRole(player); break;
                case "Act": chooseAct(player); break;
                case "Rehearse": chooseRehearse(player); break;
                case "Upgrade": chooseUpgrade(player); break;
                case "End Turn": chooseEndTurn(); break;
            }
        }
    }


    // Choices

    private void chooseMove(Player player) throws IOException {
        print("\nMoving...");
        IArea[] areas = moveManager.getMoveOptions(player);
        String[] options = moveManager.areasAsStrings(areas);
        UIAction action = ui.handlePlayerAction("Move", options);
        if (areas.length > 0) {
            moveManager.move(player, areas[action.index]);
        }
        player.hasMoved = true;
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
        Set currentSet = ((Set) player.currentArea);
        int budget = currentSet.getScene().budget;
        print("Acting...");
        boolean result = currentSet.act(player, budget);
        if (result) {
            System.out.println("Success! You have removed 1 shot counter.");
            currentSet.addShot();
            if (currentSet.getMaxShots() == currentSet.getShot()) {
                setManager.itsAWrap(player);
                currentSet.resetShots();
            }
        } else {
            System.out.println("You failed :( Try again next turn.");
        }
        player.hasWorked = true;
    }

    private void chooseRehearse(Player player) {
        print("Rehearsing...");
        player.practiceTokens = player.practiceTokens + 1;
        print("You have gained a practice token! " + player.name + " now has " + player.practiceTokens + " practice tokens!");
        player.hasWorked = true;
    }

    private void chooseUpgrade(Player player) throws IOException {
        print("\nUpgrading...");
        int[] ranks = castingManager.getRankOptions(player);
        String[] options = castingManager.getRankStrings(player);
        UIAction action = ui.handlePlayerAction("Upgrade", options);
        if (ranks.length > 0) {
            castingManager.setRankOf(player, ranks[action.index]);
        }
        player.hasUpgraded = true;
    }

    private void chooseEndTurn() {
        print("Ending Turn...");
        print("\n");
        turnOver = true;
    }


    // Get Choices

    private String[] getActionsOf(Player player) {
        ArrayList<String> actions = new ArrayList<>();

        IArea curArea = player.currentArea;
        String name = curArea.name;
        if ((curArea.name).equals("Trailer")) {
            if (!player.hasMoved) {
                actions.add("Move");
            }
        }
        else if ((curArea.name).equals("Office")) {
            if (!player.hasMoved) {
                actions.add("Move");
            }
            if (!player.hasUpgraded) {
                actions.add("Upgrade");
            }
        }
        else {
            if (!player.working) {
                if (!player.hasMoved) {
                    actions.add("Move");
                }
                actions.add("Choose Role");
            }
            else if (!player.hasWorked) {
                actions.add("Act");
                actions.add("Rehearse");
            }
        }
        actions.add("End Turn");

        return actions.toArray(new String[0]);
//        return new String[]{"Move", "Choose Role", "Act", "Rehearse", "Upgrade", "End Turn"};
    }


    // Utility
    private void print(String string) {
        System.out.println(string);
    }

    private static void turnSort(Player[] a) {
        boolean isSorted = false;
        Player temp;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < a.length - 1; i++) {
                if (a[i].turnNo > a[i+1].turnNo) {
                    temp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = temp;
                    isSorted = false;
                }
            }
        }
    }
}