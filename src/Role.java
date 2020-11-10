// Just for everything related to a single role.

public class Role {
    String name; // name!
    private String description; // description!
    private int rank; // rank!

    Role(String name, String description, int rank) {
        this.name = name;
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

}
