package ID.deadwood;

import ID.deadwood.GameLoop;
import ID.deadwood.UI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private Label pr_text;
    @FXML private Button op_one;
    @FXML private Button op_two;
    @FXML private Button op_three;
    @FXML private Button op_four;
    @FXML private Button op_five;
    @FXML private Button op_six;
    @FXML private Button op_seven;
    @FXML private Button op_eight;
    @FXML private Button op_nine;

    @FXML private DialogPane dialog_pane;
    @FXML private Label dialog_header;
    @FXML private Label dialog_description;

    @FXML private Label location_label;
    @FXML private Label role_label;
    @FXML private Label shots_label;
    @FXML private Label rank_label;
    @FXML private Label dollar_label;
    @FXML private Label credit_label;

    @FXML private Label sceneWrap_label;
    @FXML private Label complete_label;
    @FXML private Label totalDays_label;

    private UI ui = UI.getInstance();
    private static Label promptText;
    private static Button[] promptButtons;

    private static DialogPane dialog;
    private static Label dialogHeader;
    private static Label dialogDescription;

    private static Label playerRoom;
    private static Label playerRole;
    private static Label playerShots;
    private static Label playerRank;
    private static Label playerDollars;
    private static Label playerCredits;

    private static Label scenesWrapped;
    private static Label daysComplete;
    private static Label totalDays;

    public void initialize(URL location, ResourceBundle resources) {
        promptText = pr_text;
        promptButtons = new Button[]{op_one, op_two, op_three, op_four, op_five, op_six, op_seven, op_eight, op_nine};
        for (int i = 0; i < promptButtons.length; i++) {
            final int idx = i;
            System.out.println(idx);
            promptButtons[i].setOnAction(event -> { triggerButton(idx); });
        }
        dialog = dialog_pane;
        Node nextNode = dialog.lookupButton(ButtonType.NEXT);
        if (nextNode instanceof Button) {
            Button nextButton = (Button) nextNode;
            nextButton.setOnAction(event -> { triggerButton("NEXT"); });
        }

        dialogHeader = dialog_header;
        dialogDescription = dialog_description;
        dialog.setVisible(false);

        playerRoom = location_label;
        playerRole = role_label;
        playerShots = shots_label;
        playerRank = rank_label;
        playerDollars = dollar_label;
        playerCredits = credit_label;

        scenesWrapped = sceneWrap_label;
        daysComplete = complete_label;
        totalDays = totalDays_label;
    }

    public static void playerDisplay(String location, String role, int shots, int rank, int dollars, int credits) {
        playerRoom.setText(location);
        playerRole.setText(role);
        playerShots.setText(Integer.toString(shots));
        playerRank.setText(Integer.toString(rank));
        playerDollars.setText(Integer.toString(dollars));
        playerCredits.setText(Integer.toString(credits));
    }

    public static void gameStateDisplay(int wrapped, int complete, int total) {
        scenesWrapped.setText(Integer.toString(wrapped));
        daysComplete.setText(Integer.toString(complete));
        totalDays.setText(Integer.toString(total));
    }

    public void triggerButton(int idx) {
        ui.triggerOptionEvent(idx);
    }

    public void triggerButton(String button) {
        dialog.setVisible(false);
        ui.triggerDialogEvent(button);
    }

    public static void optionPrompt(String descriptor, String[] actions) {
//        buttonActions = actions;
        promptText.setText(descriptor + "s");
        for (int i = 0; i < promptButtons.length; i++) {
            boolean excluded = actions.length <= i;
            String textValue = excluded ? "" : actions[i];
            promptButtons[i].setText(textValue);
            promptButtons[i].setDisable(excluded);
        }
    }

    public static void notifPrompt(String descriptor) {
        notifPrompt(descriptor, "");
    }

    public static void notifPrompt(String descriptor, String detail) {
        dialog.setVisible(true);
        dialogHeader.setText(descriptor);
        dialogDescription.setText(detail);
        disableOptionButtons();
    }

    public static void disableOptionButtons() {
        for (Button button : promptButtons) {
            button.setDisable(true);
        }
    }

    public static void enableOptionButtons() {
        for (Button button : promptButtons) {
            button.setDisable(false);
        }
    }

}
