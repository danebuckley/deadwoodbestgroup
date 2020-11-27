package ID.deadwood;

// For all the player specific data!
class Player {

    // Finals
    String name; // name!

    // Scores
    int rank; // rank!
    int dollars; // dollars!
    int credits; // credits!
    int practiceTokens = 0;
    int finalScore = 0;

    // State
    Role role = null;
    Room currentArea;
    int turnNo; // Felt cute, might delete later.
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
}
