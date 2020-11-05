
import java.util.Hashtable;

// This class contains any kind of information that would need to be passed back to the GameLoop from the UI.

class UIAction {
    String type;
    int index;

    UIAction(String type) {
        this.type = type;
    }

    UIAction(int index) {
        this.index = index;
    }

    UIAction(String type, int index) {
        this.type = type;
        this.index = index;
    }
}
