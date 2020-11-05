
// NOTE: Has access to the set it is currently on.

public class Player {
    String name;
    int rank;
    int position;
    int dollars;
    int credits;
    int turnNo;

    Set currentSet;

    Player(String name) {
        this.name = name;
        this.rank = 0;
        this.position = 0;
        this.dollars = 0;
        this.credits = 0;
    }


    public void addDollars(int amount) {
        this.dollars += amount;
    }

    public void subDollars(int amount) {
        this.dollars -= amount;
    }


    public void addCredits(int amount) {
        this.credits += amount;
    }

    public void subCredits(int amount) {
        this.credits -= amount;
    }


    public void setPosition(int position) {
        this.position = position;
    }
}
