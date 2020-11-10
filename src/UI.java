
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

// For anything visual; prompts, inputs, prints, etc.

class UI {

    GameLoop gl;

    UI(GameLoop gameLoop) {
        this.gl = gameLoop;
    }


    // Reused Methods

    private String prompt(String descriptor, String[] actions) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.printf("%ss: \n", descriptor);
        if (actions.length > 0) {
            int i = 1;
            for (String action : actions) {
                System.out.println("\t " + i + ". " + action);
                i++;
            }
            System.out.printf("Pick an %s (#): ", descriptor);
            String input = "";
            while (true) {
                input = in.readLine().trim();
                if (isInt(input)) {
                    int value = clamp(asInt(input), 1, actions.length);
//                in.close();
                    return actions[value - 1];
                }
            }
        }
        else {
            System.out.println("\t " + "[None Available]");
            return "";
        }
    }

    public UIAction handlePlayerAction(String descriptor, String[] options) throws IOException {
        String action = prompt(descriptor, options);
        return new UIAction(action);
    }


    // Acting

    public ArrayList<Integer> handleDice(int numDice, Player player) {
        ArrayList<Integer> diceRoll = new ArrayList<>();
        for (int i = 0; i < numDice; i++) {
            Random random = new Random();
            int currentRand = random.nextInt(6) + 1;
            diceRoll.add(currentRand);
        }
        bubbleSort(diceRoll);
        if (player.practiceTokens > 0) {
            for (int i = 0; i < numDice; i++) {
                diceRoll.set(i, diceRoll.get(i) + 1);
            }
        }
        return diceRoll;
    }


    // Rehearsing

    private void handleRehearsal() {

    }


    // Upgrade

    private void handleUpgradeOptions(String[] options) {

    }


    // Utility

    public static void bubbleSort(ArrayList<Integer> a) {
        boolean isSorted = false;
        int temp;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < a.size() - 1; i++) {
                if (a.get(i) < a.get(i+1)) {
                    temp = a.get(i);
                    a.set(i, a.get(i+1));
                    a.set(i+1, temp);
                    isSorted = false;
                }
            }
        }
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