import java.lang.reflect.Array;
import java.util.ArrayList;

// NOTE: sets function in a sort of linked-list fashion; there is no map of sets, as they can be accessed through one another.

public class Set {
    String name;
    private Scene scene;
    private ArrayList<Role> extraRoles;
    ArrayList<Player> players;
    ArrayList<Set> connectedSets;
    private int maxShots;
    private int shotCounters;

    Set (String name, int maxShots, ArrayList<Role> extraRoles) {
        this.name = name;
        this.extraRoles = extraRoles;
        this.maxShots = maxShots;
        this.shotCounters = 0;
        this.players = new ArrayList<Player>();
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
        int sal = extraRoles.size();
        Role[] allRoles = new Role[fal + sal];
        System.arraycopy(sceneRoles, 0, allRoles, 0, fal);
        System.arraycopy(extraRoles.toArray(), 0, allRoles, fal, sal);
        return allRoles;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void act(Player player) {

    }

    public void rehearse(Player player) { //on scene completion the players practice tokens need to be reset
        player.practiceTokens = player.practiceTokens + 1;
    }

}