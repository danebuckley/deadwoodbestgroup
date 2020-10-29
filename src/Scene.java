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
        return new Role[1];
    }

    public String getName() {
        return "";
    }

    public String getDescription() {
        return "";
    }
}
