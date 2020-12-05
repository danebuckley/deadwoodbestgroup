package ID.deadwood;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

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
    private String lastState;
    private String state;
    private Role[] roleChoices;  //hmm
    private int maxDays;
    private int curDays;
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
        state = "PlayerCount";
        System.out.println("Welcome to Deadwood! How many players are playing today? (2-8)");
        ui.displayOptionPrompt("Player", 1, 8);
//        numPlayers = getNum.nextInt();
//        if (numPlayers < 2 || numPlayers > 8) {
//            System.exit(0);
//        }
    }

    void awaitStart() {
        state = "Starting";
        ui.displayDialogPrompt("Start?", "Or not, we don't mind THAT much. :P");
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
        curDays = 0;
        if (numPlayers == 2 || numPlayers == 3) {
            maxDays = 3;
        }
        ui.displayGameState(setManager.wrapCount, curDays, maxDays);
        doGameLoop();
//        for (int i = 0; i < numDays; i++) {
//            startDayCycle();
//        }
//        System.out.println("Game has ended! now scoring...");
    }

    private void doGameLoop() {
        System.out.println("*do game loop...*");
        curDays += 1;
        ui.displayGameState(setManager.wrapCount, curDays, maxDays);
        if (curDays >= maxDays) {
            System.out.println("Game has ended! now scoring...");
            String winner = scoreManager.endScoring(scoreManager.scoreGame(players));
            state = "Restart";
            ui.displayDialogPrompt(winner + " wins!", "Play again?");
        }
        else {
            startDayCycle();
        }
    }

    private void startDayCycle() {
        currPlayerIdx = -1;
        setManager.wrapCount = 0;
        ui.displayGameState(setManager.wrapCount, curDays, maxDays);
        doDayLoop();
    }

    private void doDayLoop() {
        System.out.println("*do day loop...*");
        currPlayerIdx += 1;
        if (currPlayerIdx == players.length) {
            currPlayerIdx = 0;
        }
        ui.displayGameState(setManager.wrapCount, curDays, maxDays);
        System.out.println(setManager.wrapCount);
        if (setManager.wrapCount >= setupManager.setbank.size()-1) {
            System.out.println("Day completed!");
            setupManager.resetPlayers(players);
            setupManager.resetRooms();
            doGameLoop();
        }
        else {
            startTurnCycle();
        }
    }


    private void startTurnCycle() {
//        turnOver = false;
        Player player = players[currPlayerIdx];
        player.hasMoved = false;
        player.hasUpgraded = false;
        player.hasWorked = false;
        System.out.println(player.name + "'s turn:");
        ui.displayPlayer(player);
        doTurnLoop(player);
    }

    private void doTurnLoop(Player player) {
        ui.displayPlayer(player);
        print("\nPicking Action...");
        setState("Turn");
        String[] optionStrings = getActionsOf(player);
        ui.displayOptionPrompt("Action", optionStrings);
    }


    private void setState(String state) {
        this.lastState = this.state;
        this.state = state;
    }

    public void triggerDialogEvent(String button) {
        switch (button) {
            case "NEXT" :
                switch (state) {
                    case "Starting" : startGame(this.numPlayers); break;
                    case "Restarting" : run(); break;
                }
                break;
        }
    }

    public void triggerOptionEvent(int idx, String optionChose) {
        Player player = !state.equals("PlayerCount") ? players[currPlayerIdx] : null;
        switch(state) {
            case "PlayerCount":
                this.numPlayers = idx+1;
                awaitStart();
                break;
            case "Turn":
                switch(optionChose) {
                    case "Move" : chooseMove(player); break;
                    case "Choose Role" : chooseRole(player); break;
                    case "Upgrade" : chooseUpgrade(player); break;
                    case "End Turn" : chooseEndTurn(); break;
                    case "Act" : chooseAct(player); doTurnLoop(player); break;
                    case "Rehearse" : chooseRehearse(player); doTurnLoop(player); break;
                }
                break;
            case "Move":
                if (optionChose.equals("Go Back")) {
                    doTurnLoop(player);
                }
                else {
                    Hashtable<String, Room> roombank = setupManager.roombank;
                    if (roombank.containsKey(optionChose)) {
                        Room room = roombank.get(optionChose);
                        moveManager.move(player, room);
                        doTurnLoop(player);
                    }
                }
                break;
            case "Choose Role":
                if (optionChose.equals("Go Back")) {
                    doTurnLoop(player);
                }
                else {
                    setManager.assignRoleTo(player, roleChoices[idx]);
                    doTurnLoop(player);
                }
                break;
            case "Upgrade":
                if (optionChose.equals("Go Back")) {
                    doTurnLoop(player);
                }
                else {
                    int rank = castingManager.getUpgradeOptions(player)[idx];
                    castingManager.setRankOf(player, rank);
                    doTurnLoop(player);
                }
                break;
        }
    }


    // Choices

    private void chooseMove(Player player) {
        print("\nMoving...");
        setState("Move");
        Room[] areas = moveManager.getMoveOptions(player);
        String[] options = moveManager.areasAsStrings(areas);
        ui.displayOptionPrompt("Move", options);
    }

    private void chooseRole(Player player) {
        print("\nChoosing Role...");
        setState("Choose Role");
        Role[] roles = setManager.getRoleOptions(player);
        roleChoices = roles;
        String[] options = setManager.rolesAsStrings(roles);
        ui.displayOptionPrompt("Role", options);
    }

    private void chooseAct(Player player) {
        print("Acting...");
        setState("Act");
        setManager.act(player);
    }

    private void chooseRehearse(Player player) {
        print("Rehearsing...");
        setState("Rehearse");
        setManager.rehearse(player);
        print("You have gained a practice token! " + player.name + " now has " + player.practiceTokens + " practice tokens!");
    }

    private void chooseUpgrade(Player player) {
        print("\nUpgrading...");
        setState("Upgrade");
        int[] upgrades = castingManager.getUpgradeOptions(player);
        String[] options = castingManager.getUpgradeStrings(player);
        ui.displayOptionPrompt("Upgrade", options);
    }

    private void chooseEndTurn() {
        print("Ending Turn...");
        print("\n");
        doDayLoop();
    }


    // Get Choices

    private String[] getActionsOf(Player player) {
        ArrayList<String> actions = new ArrayList<>();

        Room curRoom = player.currentRoom;
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
            Set curSet = (Set) curRoom;
            if (!player.working) {
                if (!player.hasMoved && !player.hasWorked) {
                    actions.add("Move");
                }
                if (!curSet.isWrapped) {
                    actions.add("Choose Role");
                }
            }
            else if (!player.hasWorked && !player.hasMoved) {
                if (!curSet.isWrapped) {
                    actions.add("Act");
                }
                if (player.role.rank > player.practiceTokens + 1) {
                    actions.add("Rehearse");
                }
            }
        }
        if (!player.working || player.hasMoved || (player.hasWorked)) {
            actions.add("End Turn");
        }

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