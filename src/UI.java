
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class UI {

    GameLoop gl;

    public UI (GameLoop gameLoop) {
        this.gl = gameLoop;
    }


    // Reused Methods

    public String prompt(String descriptor, String[] actions) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.printf("%ss: \n", descriptor);
        int i = 1;
        for (String action : actions) {
            System.out.println("\t " + i + ". " + action);
            i++;
        }
        System.out.printf("Pick an %s (#): ", descriptor);
        String input = "[EMPTY]";
        while (true) {
            System.out.print(input + " ");
            input = in.readLine().trim();
            if (isInt(input)) { 
                int value = clamp(asInt(input), 1, actions.length);
//                in.close();
                return actions[value-1];
            }
        }
    }


    // Beginning

    private void startGame() {

    }

    public UIAction handlePlayerActions(String[] actions) throws IOException {
        String action = prompt("Action", actions);
        return new UIAction(action);
    }    

    private void choose(int option) {

    }


    // Moving

    private void chooseMove() {

    }

    private void handleMoveOptions() {

    }

    private void move(int option) {

    }


    // Role Choosing

    private void chooseRole() {

    }

    private void handleRoleOptions() {

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
}