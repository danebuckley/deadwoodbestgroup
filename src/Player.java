
// For all the player specific data!

public class Player {
    int practiceTokens = 0;
    int finalScore = 0;
    String name; // name!
    int rank; // rank!
    int dollars; // dollars!
    int credits; // credits!
    int turnNo; // Felt cute, might delete later.
    String scene = "";
    Role role = null;

    Room currentArea; // The set the player is on.
    boolean working = false;
    boolean hasMoved = false;
    boolean hasWorked = false;
    boolean hasUpgraded = false;

    Player(String name) {
        this.name = name;
        this.rank = 1;
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
}
