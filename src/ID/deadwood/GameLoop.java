package ID.deadwood;

import java.io.IOException;
import java.util.ArrayList;

// The core class; focused mainly on the general gameloops and player actions.

class GameLoop {
    
    // Managers
    private final SetupManager setupManager;
    private final SetManager setManager;
    private final MoveManager moveManager;
    private final ScoringManager scoreManager;
    private final UI ui;
    private final CastingManager castingManager;

    // Aggregators
    private Player[] players;

    // State
    private int currPlayer;
    private boolean turnOver;

    GameLoop() {
        setupManager = new SetupManager();
        setManager = new SetManager();
        moveManager = new MoveManager();
        scoreManager = new ScoringManager();
        ui = new UI();
        castingManager = new CastingManager();
    }

    void runGame(int numPlayers) {
        setupManager.initializeGame();
        players = setupManager.setupPlayers(numPlayers);
        turnSort(players);
        gameLoop(numPlayers);
        scoreManager.endScoring(scoreManager.scoreGame(players));
    }


    // Game Loops

    private void gameLoop(int numPlayers) {
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
        System.out.println("Game has ended! now scoring...");

    }

    private void dayLoop() {
        currPlayer = -1;
        boolean dayOver = false;
        while (!dayOver) {
            currPlayer += 1;
            if (currPlayer == players.length) {
                currPlayer = 0;
            }
            turnLoop();
            if (setManager.wrapCount == 9) {
                dayOver = true;
                setupManager.resetPlayers(players);
                System.out.println("Day completed!");
                setManager.wrapCount = 0;
            }
        }
    }


    private void turnLoop() {
        turnOver = false;

        Player player = players[currPlayer];

        player.hasMoved = false;
        player.hasWorked = false;
        player.hasUpgraded = false;

        System.out.println(players[currPlayer].name + "'s turn:");
        while (!turnOver) {
            print("\nPicking Action...");

            String[] actionStrings = getActionsOf(player);
            int actionIndex = ui.handlePlayerAction("Action", actionStrings);
            switch (actionStrings[actionIndex]) {
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

    private void chooseMove(Player player) {
        print("\nMoving...");
        Room[] areas = moveManager.getMoveOptions(player);
        String[] options = moveManager.areasAsStrings(areas);
        int actionIndex = ui.handlePlayerAction("Move", options);
        if (areas.length > 0) {
            moveManager.move(player, areas[actionIndex]);
        }
    }

    private void chooseRole(Player player) {
        print("\nChoosing Role...");
        Role[] roles = setManager.getRoleOptions(player);
        String[] options = setManager.rolesAsStrings(roles);
        int actionIndex = ui.handlePlayerAction("Role", options);
        if (roles.length > 0) {
            setManager.assignRoleTo(player, roles[actionIndex]);
        }
    }

    private void chooseAct(Player player) {
        print("Acting...");
        setManager.act(player);
    }

    private void chooseRehearse(Player player) {
        print("Rehearsing...");
        setManager.rehearse(player);
        print("You have gained a practice token! " + player.name + " now has " + player.practiceTokens + " practice tokens!");
    }

    private void chooseUpgrade(Player player) {
        print("\nUpgrading...");
        int[] upgrades = castingManager.getUpgradeOptions(player);
        String[] options = castingManager.getUpgradeStrings(player);
        int actionIndex = ui.handlePlayerAction("Upgrade", options);
        if (upgrades.length > 0) {
            castingManager.setRankOf(player, upgrades[actionIndex]);
        }
    }

    private void chooseEndTurn() {
        print("Ending Turn...");
        print("\n");
        turnOver = true;
    }


    // Get Choices

    private String[] getActionsOf(Player player) {
        ArrayList<String> actions = new ArrayList<>();

        Room curRoom = player.currentArea;
        if (curRoom.name.equals("trailer")) {
            if (!player.hasMoved) {
                actions.add("Move");
            }
        }
        else if (curRoom.name.equals("office")) {
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