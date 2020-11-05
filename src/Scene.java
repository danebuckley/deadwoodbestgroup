package deadwoodbestgroup.src;

public class Scene {
    private String name;
    private String description;
    private Role[] roles;

    public Scene(String name, String description, Role[] roles) {
        this.name = name;
        this.description = description;
        this.roles = roles;
    }

    public Role[] getRoles() {
        return this.roles;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.name;
    }
}
