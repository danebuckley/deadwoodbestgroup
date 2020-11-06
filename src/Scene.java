import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Scene {
    String name;
    String description;
    int budget;
    private ArrayList<Role> roles;

    Scene(String name, String description, ArrayList<Role> roles) {
        this.name = name;
        this.description = description;
        this.roles = roles;
    }

    Role[] getRoles() {
        return this.roles.toArray(new Role[0]);
    }
}
