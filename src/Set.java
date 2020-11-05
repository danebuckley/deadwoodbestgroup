import java.util.ArrayList;

public class Set {
    private Scene scene;
    private Role[] extraRoles;
    private ArrayList<Player> players;
    private int shotCounters;

    Set (Role[] extraRoles) {
        this.extraRoles = extraRoles;
        players = new ArrayList<Player>();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return this.scene;
    }

    public Role[] getRoles() {
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
}