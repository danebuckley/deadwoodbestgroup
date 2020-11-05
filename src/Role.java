
public class Role {
    String name;
    private String description;
    private int rank;

    Role(String name, String description, int rank) {
        this.name = name;
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

}
