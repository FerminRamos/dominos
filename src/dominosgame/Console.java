package dominosgame;

/**
 * Console runs Domino Game on Console
 */
public class Console {
    private static boolean QUITS;

    /**
     * Stops game loop.
     */
    public static void quit(){QUITS = true;}


    /**
     *
     */
    private static void printWinCondition(int roundMatches, boolean userWon,
                                          boolean computerWon){
        if(roundMatches == 0){
            GameManager.updateGameMsg(
                    "Draw! Boneyard empty & nobody has matches :(");
        }else if(userWon){
            GameManager.updateGameMsg("User Won!");
        }else if(computerWon || QUITS){
            GameManager.updateGameMsg("Computer Won!");
        }
        Board.showBoardToConsole();
    }


    /**
     * Main Console Loop
     */
    public static void main(String[] args){
        // Create list of new playable dominos
        Boneyard.makeNewDominoArray();

        // Add Players
        Player Computer = new Player(false);
        Player User = new Player(true);

        // Create Board for users to play on
        Board.createNewBoard();

        // Main Game Loop. Runs as long as at least 1 person matched a board
        // piece or player has not declared winner.
        int roundMatches = 1;
        while(roundMatches != 0&&!User.hasWon()&&!Computer.hasWon() && !QUITS){
            Boneyard.showBoneyard();
            System.out.println();
            roundMatches = 0;

            Board.showBoardToConsole();
            GameManager.updateGameMsg("\nUser's Turn");
            User.showHand();
            User.showUserOptions();
            roundMatches += User.takeTurn(
                    GameManager.getUserInput(true));
            GameManager.sleep();


            GameManager.updateGameMsg("- - - - - - - - - - - - - " +
                    "- - - - - - - - - ");
            Board.showBoardToConsole();
            GameManager.updateGameMsg("\nComputer's Turn");
            Computer.showHand();  // Does not literally show hand, just count.
            roundMatches += Computer.takeTurn(
                    GameManager.getUserInput(false));
            GameManager.updateGameMsg("- - - - - - - - - - - - - " +
                    "- - - - - - - - - ");
            GameManager.sleep();
        }

        printWinCondition(roundMatches, User.hasWon(), Computer.hasWon());

    }
}
