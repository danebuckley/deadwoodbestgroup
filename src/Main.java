
// Just for running the game, is all.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

         GameLoop gameLoop = new GameLoop();
         Scanner getNum = new Scanner(System.in);
         System.out.println("Welcome to Deadwood! How many players are playing today? (2-8)");
         int numPlayers = getNum.nextInt();
         if (numPlayers < 2 || numPlayers > 8) {
             System.exit(0);
         }
         gameLoop.runGame(numPlayers);
    }
}
