package ID.deadwood;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// For anything visual; prompts, inputs, prints, etc.
class UI {

    // Reused Methods

    private String prompt(String descriptor, String[] actions) {
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
                try {
                    input = in.readLine().trim();
                    if (isInt(input)) {
                        int value = clamp(asInt(input), 1, actions.length);
                        return actions[value - 1];
                    }
                } catch (Exception ex) {
                    System.out.println("String parse failure; requires an integer value");
                }
            }
        }
        else {
            System.out.println("\t " + "[None Available]");
            return "";
        }
    }

    public int handlePlayerAction(String descriptor, String[] options) {
        String action = prompt(descriptor, options);
        return findIndex(options, action);
    }


    // Utility

    public static int findIndex(String arr[], String t)
    {
        if (arr == null) {
            return -1;
        }
        int len = arr.length;
        int i = 0;
        while (i < len) {
            if (arr[i].equals(t)) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
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