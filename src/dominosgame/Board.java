package dominosgame;

import java.util.ArrayList;
import java.util.List;


public class Board {
    private static List<String> BOARD;
    private static int LEFT;
    private static int RIGHT;
    private static boolean START_TOP_ROW;

    public static void createNewBoard(){
        BOARD = new ArrayList<String>();
        LEFT = 0;
        RIGHT = 0;
        START_TOP_ROW = true;
    }


    /**
     * Moves a single domino piece from Player's Hand to Board & figures out
     *  where to place domino on board (right = 1st option). Assumes domino
     *  will match to either left or right sides. 0 = wildcard, can match to
     *  any number.
     *   1. Check right
     *   2. Check left
     *   3. Check right-flipped
     *   4. Check left-flipped
     * @param domino String Domino to be added
     */
    public static void addToBoard(String domino){
        int boardLeft = getBoardLeft();
        int boardRight = getBoardRight();

        int dominoLeft = GameManager.translateDominoStr(domino, 'l');
        int dominoRight = GameManager.translateDominoStr(domino, 'r');

        String flippedDomino = flipDominoStr(domino);

        // If first domino added, change left and right to domino sides
        if(BOARD.size() == 0){
            setBoardLeft(dominoLeft);
            setBoardRight(dominoRight);
        }

        // Add domino according to rules (see javadoc comment above)
        if(boardRight == dominoLeft || boardRight == 0 || dominoLeft == 0){
            GameManager.updateGameMsg("  >> Added to Right");

            BOARD.add(domino);
            setBoardRight(dominoRight);
        }else if(boardLeft == dominoRight||boardLeft == 0||dominoRight == 0){
            GameManager.updateGameMsg("  >> Added to Left");

            BOARD.add(0, domino);
            setBoardLeft(dominoLeft);
            switchStartRow();
        }else if(boardRight == dominoRight){
            GameManager.updateGameMsg("  >> Flipped");
            GameManager.updateGameMsg("  >> Added to Right");

            BOARD.add(flippedDomino);
            setBoardRight(dominoLeft);
        }else{
            GameManager.updateGameMsg("  >> Flipped");
            GameManager.updateGameMsg("  >> Added to Left");

            BOARD.add(0, flippedDomino);
            setBoardLeft(dominoRight);
            switchStartRow();
        }
    }


    /**
     * Gets the value of left-most domino to be paired with
     */
    public static int getBoardLeft(){
        return LEFT;
    }


    /**
     * Sets new left-most board value to match to
     * @param left left-most domino dots on board
     */
    private static void setBoardLeft(int left){LEFT = left;}


    /**
     * Gets the value of right-most domino to be paired with
     */
    public static int getBoardRight(){
        return RIGHT;
    }

    /**
     * Sets new right-most board value to match to
     * @param right right-most domino dots on board
     */
    private static void setBoardRight(int right){RIGHT = right;}

    /**
     * Returns board
     * @return BOARD
     */
    public static List<String> getBoard(){
        return BOARD;
    }


    /**
     * Used for printing the Board to Console. If domino was added to left,
     *  switch topRow -> botRow or botRow -> topRow. The showBoard() function
     *  will take care of the rest of formatting issues. If domino was added to
     *  right, formatting remains the same, just w/ appended domino.
     */
    private static void switchStartRow(){START_TOP_ROW = !START_TOP_ROW;}


    /**
     * Used for GUI, for formatting.
     * @return
     */
    public static boolean isStartTopRow(){
        return START_TOP_ROW;
    }


    /**
     * Flips a domino string [l r] --> [r l]
     * @param domino original domino string
     * @return flipped domino string
     */
    public static String flipDominoStr(String domino){
        int leftInt = GameManager.translateDominoStr(domino, 'l');
        int rightInt = GameManager.translateDominoStr(domino, 'r');

        return "[" + rightInt + " " + leftInt + "]";
    }


    /**
     * Prints formatted board in format:
     *  [# #] [# #] [# #]
     *    [# #] [# #]
     *  Function makes sure that domino pieces previously printed on top row,
     *   stay on top row.
     */
    public static void showBoardToConsole(){
        int boardSize = BOARD.size();

        System.out.print("Playing Field: ");
        if(START_TOP_ROW){
            // Print Top Row
            for(int i = 0; i < boardSize; i+=2){
                System.out.print(BOARD.get(i) + " ");
            }

            System.out.print("\n                 ");  // Formatting Purposes

            //Print Bottom Row
            for(int i = 1; i < boardSize; i+=2){
                System.out.print(BOARD.get(i) + " ");
            }
            System.out.println();  // Formatting Purposes

        }else{
            System.out.print("  ");  // Formatting Purposes

            // Print Top Row
            for(int i = 1; i < boardSize; i+=2){
                System.out.print(BOARD.get(i) + " ");
            }

            System.out.print("\n               "); // Formatting Purposes

            // Print Bottom Row
            for(int i = 0; i < boardSize; i+=2){
                System.out.print(BOARD.get(i) + " ");
            }
            System.out.println();  // Formatting Purposes
        }
    }
}
