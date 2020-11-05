import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Scene {
    private String name;
    private String description;
    private int budget;
    private ArrayList<Role> roles;

    Scene(String name, String description, ArrayList<Role> roles) {
        this.name = name;
        this.description = description;
        this.roles = roles;
    }

    Role[] getRoles() {
        return this.roles.toArray(new Role[0]);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.name;
    }
}
