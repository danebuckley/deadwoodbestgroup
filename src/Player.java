
public class Player {
    private String name;
    private int rank;
    private int position;
    private int dollars;
    private int credits;
    private int turnNo;

    public Player (String name) {
        this.name = name;
        this.rank = 0;
        this.position = 0;
        this.dollars = 0;
        this.credits = 0;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }


    public void addDollars(int amount) {
        this.dollars += amount;
    }

    public void subDollars(int amount) {
        this.dollars -= amount;
    }

    public int getDollars() {
        return this.dollars;
    }


    public void addCredits(int amount) {
        this.credits += amount;
    }

    public void subCredits(int amount) {
        this.credits -= amount;
    }

    public int getCredits() {
        return this.credits;
    }


    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }
}
