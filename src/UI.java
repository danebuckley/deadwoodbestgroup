
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


// For anything visual; prompts, inputs, prints, etc.

class UI {

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

    // Rehearsing

    private void handleRehearsal() {

    }


    // Upgrade

    private void handleUpgradeOptions(String[] options) {

    }


    // Utility

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