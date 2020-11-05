package deadwoodbestgroup.src;

public class UI {

    GameLoop gl;

    public UI (GameLoop gameLoop) {
        this.gl = gameLoop;
    }


    // Utility

    private int parseInput() {
        return 0;
    }

    private static boolean isInt(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static int asInt(String strNum) {
        return Integer.parseInt(strNum);
    }

    private static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }


    // Beginning

    private void startGame() {

    }

    public String promptPlayerActions(String[] actions) {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose an Action (enter a number): ");
        int i = 1;
        for (String action : actions) {
            System.out.println("\t " + i + ". " + action);
            i++;
        }
        String input = "";
        boolean found = false;
        while (!found) {
            input = nextLine().trim();
            if (isNumeric(input)) { 
                int value = clamp(asInt(input), 1, actions.length);
                return actions[value-1];
            }
        }
    }

    private void choose(int option) {

    }


    // Moving

    private void chooseMove() {

    }

    private void displayMoveOptions() {

    }

    private void move(int option) {

    }


    // Role Choosing

    private void chooseRole() {

    }

    private void displayRoleOptions() {

    }

    private void roleChoice(int option) {

    }


    // Acting

    private void chooseAct() {

    }

    private void displayDice() {

    }

    private void act() {

    }


    // Rehearsing

    private void chooseRehearse() {

    }

    private void rehearseWith(Player player) {

    }

    private void rehearse() {

    }


    // Upgrade

    private void chooseUpgrade() {

    }

    private void displayUpgradeOptions() {

    }

    private void upgrade(int option) {

    }


    // End Turn
    private void endTurn() {

    }
}