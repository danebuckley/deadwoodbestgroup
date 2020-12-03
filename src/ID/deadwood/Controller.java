package ID.deadwood;

import ID.deadwood.GameLoop;
import ID.deadwood.UI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private UI ui = UI.getInstance();
    private static Label promptText;
    private static Button[] promptButtons;
//    private static String[] buttonActions;

    public void initialize(URL location, ResourceBundle resources) {
        promptText = pr_text;
        promptButtons = new Button[]{op_one, op_two, op_three, op_four, op_five, op_six, op_seven, op_eight, op_nine};
        for (int i = 0; i < promptButtons.length; i++) {
            final int idx = i;
            System.out.println(idx);
            promptButtons[i].setOnAction(event -> { triggerButton(idx); });
        }
    }

    public void triggerButton(int idx) {
        ui.triggerOptionEvent(idx);
    }

    public static void prompt(String descriptor, String[] actions) {
//        buttonActions = actions;
        promptText.setText(descriptor + "s");
        for (int i = 0; i < promptButtons.length; i++) {
            boolean excluded = actions.length <= i;
            String textValue = excluded ? "" : actions[i];
            promptButtons[i].setText(textValue);
            promptButtons[i].setDisable(excluded);
        }
    }
}
