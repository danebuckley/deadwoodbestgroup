package ID.deadwood;

// For anything visual; prompts, inputs, prints, etc.
public class UI {

    // Singleton Functionality

    private static UI uniqueInstance = null;
    private static GameLoop gameloop = null;

    private UI() {
    }

    public static UI getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new UI();
        }
        return uniqueInstance;
    }

    // Properties

    private String[] options;

    public void displayGameState(int wrapped, int complete, int total) {
        Controller.gameStateDisplay(wrapped, complete, total);
    }

    public void displayPlayer(Player player) {
        String room = "None";
        int shots = 0;
        if (player.currentRoom != null) {
            room = player.currentRoom.name;
            try {
                shots = ((Set) player.currentRoom).currShots;
            } catch (Exception ex) {}
        }

        String role = "None";
        if (player.role != null) {
            role = player.role.name;
        }
        Controller.playerDisplay(room, role, shots, player.rank, player.dollars, player.credits);
    }

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
        Controller.optionPrompt(descriptor, options);
    }

    void displayDialogPrompt(String descriptor) {
        displayDialogPrompt(descriptor, "");
    }

    void displayDialogPrompt(String descriptor, String detail) {
        Controller.notifPrompt(descriptor, detail);
    }

    public void triggerOptionEvent(int idx) {
        if (options.length > idx) {
            System.out.println(options[idx]);
        }
        String optionChose = options[idx];
        gameloop.getInstance().triggerOptionEvent(idx, optionChose);
    }

    public void triggerDialogEvent(String button) {
        gameloop.getInstance().triggerDialogEvent(button);
    }
}