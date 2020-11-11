import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

// Just for everything related to a single scene; and perhaps some accessors for it's roles or whatevs.

public class Scene {
    String name;
    String img;
    int budget;
    int number;
    String description;
    private ArrayList<Role> roles;

    Scene(String name, String img, int budget, int number, String description, ArrayList<Role> parts) {
        this.name = name;
        this.img = img;
        this.budget = budget;
        this.number = number;
        this.description = description;
        this.roles = parts;
    }

    Role[] getRoles() {
        return this.roles.toArray(new Role[0]);
    }
}
