package deadwoodbestgroup.src;

public class Role {
    private String name;
    private String description;
    private int rank;

    public Role(String name, String description, int rank) {
        this.name = name;
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    public String getName() {
        return this.name;
    }
}
