
import java.util.Hashtable;

public class UIAction {
    public String type;
    public Hashtable<String, Integer> args;

    public UIAction (String type) {
        this.type = type;
        this.args = new Hashtable<>();
    }
}
