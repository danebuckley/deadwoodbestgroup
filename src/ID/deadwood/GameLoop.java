package ID.deadwood;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

// The core class; focused mainly on the general gameloops and player actions.

public class GameLoop {
    
    // Managers
    private static final SetupManager setupManager = SetupManager.getInstance();
    private static final SetManager setManager = SetManager.getInstance();
    private static final MoveManager moveManager = MoveManager.getInstance();
    private static final ScoringManager scoreManager = ScoringManager.getInstance();
    public static final UI ui = UI.getInstance();
    private static final CastingManager castingManager = CastingManager.getInstance();

    // Aggregators
    private Player[] players;
    private int numPlayers;

    // State
    private String state;
    private int maxDays;
    private int currPlayerIdx;
    private boolean dayOver;
    private boolean turnOver;

    // Singleton Functionality

    static GameLoop uniqueInstance = null;

    private GameLoop() { }

    public static GameLoop getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new GameLoop();
        }
        return uniqueInstance;
    }


    // Run Method

    void run() {

//        Scanner getNum = new Scanner(System.in);
        System.out.println("Welcome to Deadwood! How many players are playing today? (2-8)");
        ui.displayOptionPrompt("Player", 1, 8);
//        numPlayers = getNum.nextInt();
//        if (numPlayers < 2 || numPlayers > 8) {
//            System.exit(0);
//        }
    }

    void startGame(int numPlayers) {
        this.numPlayers = numPlayers;
        setupManager.initializeGame();
        players = setupManager.setupPlayers(numPlayers);
        turnSort(players);
        startGameCycle(numPlayers);
//        scoreManager.endScoring(scoreManager.scoreGame(players));
    }


    // Game Loops

    private void startGameCycle(int numPlayers) {
        maxDays = 4;
        if (numPlayers == 2 || numPlayers == 3) {
            maxDays = 3;
        }
        startDayCycle();
//        for (int i = 0; i < numDays; i++) {
//            startDayCycle();
//        }
//        System.out.println("Game has ended! now scoring...");
    }

    private void startDayCycle() {
        currPlayerIdx = -1;
        dayOver = false;
        doDayLoop();
    }

    private void doDayLoop() {
        currPlayerIdx += 1;
        if (currPlayerIdx == players.length) {
            currPlayerIdx = 0;
        }
        startTurnCycle();
        if (setManager.wrapCount == 9) {
            dayOver = true;
            setupManager.resetPlayers(players);
            System.out.println("Day completed!");
            setManager.wrapCount = 0;
        }
    }


    private void startTurnCycle() {
        turnOver = false;

        Player player = players[currPlayerIdx];

        player.hasMoved = false;
        player.hasWorked = false;
        player.hasUpgraded = false;

        System.out.println(player.name + "'s turn:");
        doTurnLoop(player);
    }

    private void doTurnLoop(Player player) {
        print("\nPicking Action...");

        String[] optionStrings = getActionsOf(player);
        ui.displayOptionPrompt("Action", optionStrings);

//            switch (actionStrings[actionIndex]) {
//                case "Move": chooseMove(player); break;
//                case "Choose Role": chooseRole(player); break;
//                case "Act": chooseAct(player); break;
//                case "Rehearse": chooseRehearse(player); break;
//                case "Upgrade": chooseUpgrade(player); break;
//                case "End Turn": chooseEndTurn(); break;
    }


    private void triggerOptionEvent(int idx) {

    }


    // Choices

    private void chooseMove(Player player) {
        print("\nMoving...");
        Room[] areas = moveManager.getMoveOptions(player);
        String[] options = moveManager.areasAsStrings(areas);
        ui.displayOptionPrompt("Move", options);
//        if (areas.length > 0) {
//            moveManager.move(player, areas[actionIndex]);
//        }
    }

    private void chooseRole(Player player) {
        print("\nChoosing Role...");
        Role[] roles = setManager.getRoleOptions(player);
        String[] options = setManager.rolesAsStrings(roles);
        ui.displayOptionPrompt("Role", options);
//        if (roles.length > 0) {
//            setManager.assignRoleTo(player, roles[actionIndex]);
//        }
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
        ui.displayOptionPrompt("Upgrade", options);
//        if (upgrades.length > 0) {
//            castingManager.setRankOf(player, upgrades[actionIndex]);
//        }
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