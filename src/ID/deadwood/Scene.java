package ID.deadwood;

import java.util.ArrayList;

// Just for everything related to a single scene; and perhaps some accessors for it's roles or whatevs.
class Scene {

    // Finals
    final String name;
    final String img;
    final int budget;
    final int number;
    final String description;

    // Aggregators
    private final ArrayList<Role> roles;

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
