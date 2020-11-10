
// For all the player specific data!

public class Player {
    int practiceTokens = 0;
    int finalScore = 0;
    
    String name; // name!
    int rank; // rank!
    int position; // Probably obsolete-- we have no real way of indicating a finite position XD
    int dollars; // dollars!
    int credits; // credits!
    int turnNo; // Felt cute, might delete later.

    Set currentSet; // The set the player is on.

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
}
