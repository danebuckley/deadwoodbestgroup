package deadwoodbestgroup.src;

import javax.management.relation.Role;

public class Set {
    private Scene scene;
    private Role[] extraRoles;
    private List<Player> players;
    private int shotCounters;

    Area (Role[] extraRoles) {
        this.extraRoles = extraRoles;
        players = new List<Player>();
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

    public addPlayer(Player player) {
        players.add(player);
    }
}