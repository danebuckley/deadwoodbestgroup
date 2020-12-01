package ID.deadwood;

import java.util.ArrayList;

// For anything visual; prompts, inputs, prints, etc.
public class UI {

    // Singleton Functionality

    private static UI uniqueInstance = null;

    private UI () { }

    public static UI getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new UI();
        }
        return uniqueInstance;
    }


    // Properties

    private String[] options;


    // Reused Methods

//    private String prompt(String descriptor, String[] actions) {
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        System.out.printf("%ss: \n", descriptor);
//        if (actions.length > 0) {
//            int i = 1;
//            for (String action : actions) {
//                System.out.println("\t " + i + ". " + action);
//                i++;
//            }
//            System.out.printf("Pick an %s (#): ", descriptor);
//            String input = "";
//            while (true) {
//                try {
//                    input = in.readLine().trim();
//                    if (isInt(input)) {
//                        int value = clamp(asInt(input), 1, actions.length);
//                        return actions[value - 1];
//                    }
//                } catch (Exception ex) {
//                    System.out.println("String parse failure; requires an integer value");
//                }
//            }
//        }
//        else {
//            System.out.println("\t " + "[None Available]");
//            return "";
//        }
//    }

//    public int handlePlayerAction(String descriptor, String[] options) {
//        String action = prompt(descriptor, options);
//        return findIndex(options, action);
//    }

    void displayOptionPrompt(String descriptor, int from, int to) {
        int capacity = (to - from) + 1;
        String[] options = new String[capacity];
        for (int i = 0; i < to; i++) {
            options[i] = Integer.toString(i + from);
        }
        displayOptionPrompt(descriptor, options);
    }

    void displayOptionPrompt(String descriptor, String[] options) {
        this.options = options;
        Controller.prompt(descriptor, options);
    }

    public void triggerOptionEvent(int idx) {
        if (options.length > idx) {
            System.out.println(options[idx]);
        }
        System.out.println("");
    }

//    public static void triggerActionEvent(String action, String[] options) {
//        findIndex(action, options);
//    }

    // Utility

    private int findIndex(String t, String arr[])
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

    private boolean isInt(String strNum) {
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

    private int asInt(String strNum) {
        return Integer.parseInt(strNum);
    }

    private int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}