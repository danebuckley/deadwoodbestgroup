import java.lang.reflect.Array;
import java.util.ArrayList;

// NOTE: sets function in a sort of linked-list fashion; there is no map of sets, as they can be accessed through one another.

public class Set {
    String name;
    private Scene scene;
    private Role[] extraRoles;
    ArrayList<Player> players;
    ArrayList<Set> connectedSets;
    private int shotCounters;

    Set (String name, Role[] extraRoles) {
        this.extraRoles = extraRoles;
        players = new ArrayList<Player>();
    }

    void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return this.scene;
    }

    Role[] getRoles() {
        Role[] sceneRoles = scene.getRoles();
        int fal = sceneRoles.length;
        int sal = extraRoles.length;
        Role[] allRoles = new Role[fal + sal];
        System.arraycopy(sceneRoles, 0, allRoles, 0, fal);
        System.arraycopy(extraRoles, 0, allRoles, fal, sal);
        return allRoles;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void act(Player player) {

    }

    public void rehearse(Player player) {
        player.practiceTokens = player.practiceTokens + 1;
    }

}