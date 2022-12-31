package dominosgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Holds Player Info such as Name, Dominos in Hand, Input
 */
public class Player {
    boolean isUser;
    private List<String> Hand;

    /**
     * Player Constructor
     */
    public Player(boolean isHuman){
        isUser = isHuman;

        // Draw from Boneyard to create new Player's Hand
        Hand = new ArrayList<String>();
        for(int i = 0; i < 7; i++){
            Hand.add(Boneyard.drawBoneyardDomino());
        }
    }


    /**
     * Prints the Hand of Player.
     */
    public void showHand(){
        GameManager.showHand(isUser(), Hand);
    }


    /**
     * Gets what type of user a player is
     * (true if human user, false if computer)
     * @return true if user
     */
    public boolean isUser(){
        return isUser;
    }


    /**
     * This is how the main game loop can check if player has won, BOTH must
     * be true for player to win:
     *     1) Boneyard is empty (can't give dominos for player to match)
     *     2) Hand is empty
     * @return true if player won
     */
    public boolean hasWon(){
        return !Boneyard.isEmpty() && Hand.size() == 0;
    }



    /**
     * Iterates hand and checks if any of player's dominos matches board.
     *  If it does, record index of that domino in hand. 0 = wildcard.
     * @return Indexes of playable dominos
     */
    private List<Integer> getPlayableDominos(){
        List<Integer> usableDominos = new ArrayList<Integer>();

        for(int i = 0; i < Hand.size(); i++){
            // Get our possible board matches
            int boardLeft = Board.getBoardLeft();
            int boardRight = Board.getBoardRight();

            // Translate Numbers from Domino
            int leftInt = GameManager.translateDominoStr(
                    Hand.get(i), 'l');
            int rightInt = GameManager.translateDominoStr(
                    Hand.get(i), 'r');

            // Check if match from Board's Ends
            if(boardLeft == 0||boardRight == 0||leftInt == 0||rightInt == 0){
                usableDominos.add(i);
            }else if(boardLeft == leftInt || boardLeft == rightInt ||
                    boardRight == leftInt || boardRight == rightInt){
                usableDominos.add(i);
            }
        }

        return usableDominos;
    }


    /**
     * We don't need to pass the entire playable domino list outside the
     *  Player class. But we do sometimes need to reference IF a domino can be
     *  matched (or call boneyard). This is what this function is used for.
     * @return true if Player can make at least 1 match
     */
    public boolean canMatch(){
        return getPlayableDominos().size() > 0;
    }


    /**
     * Takes turn for user // computer. Players can end up in 4 scenarios:
     *     1) Place a domino
     *     2) Can't place domino -> Draw from Boneyard.
     *     3) Quit (User-Only Opt)
     *     4) Can't place domino, Boneyard empty -> Skip turn.
     * @return 1 if some type of action was made by player (play, draw, quit)
     */
    public int takeTurn(String userInput){
        if(isUser){
            if(Objects.equals(userInput,"p") && canMatch()){
                int dominoSelected = getUserDominoSelection()-1;
                drawDomino(dominoSelected);
                return 1;
            }else if(Objects.equals(userInput, "d") && !Boneyard.isEmpty()){
                // Infinite loop until matching domino is found or
                // boneyard is empty.
                while(!canMatch() && !Boneyard.isEmpty()){
                    String newDomino = Boneyard.drawBoneyardDomino();
                    GameManager.updateGameMsg(
                            "  >> Drawing from Boneyard..." + newDomino);
                    Hand.add(newDomino);
                    GameManager.sleep();// Sleep so user can see what's going on
                }
                return 1;
            }else if(Objects.equals(userInput, "q")){
                GameManager.updateGameMsg("Player Quits - FORFEITING.");
                Console.quit();
                return 1;
            }else{
                GameManager.updateGameMsg("  >> Skipping User's Turn");
                return 0;
            }

        }else{
            List<Integer> playableDominos = getPlayableDominos();

            if(canMatch()){
                GameManager.updateGameMsg("placing domino " +
                        Hand.get(playableDominos.get(0)));
                GameManager.setDominoGUIIndex(playableDominos.get(0)); //for GUI
                drawDomino(playableDominos.get(0));
                return 1;
            }else if(!Boneyard.isEmpty()){
                // Infinite loop until matching domino is found or
                // boneyard is empty.
                while(!canMatch() && !Boneyard.isEmpty()){
                    String newDomino = Boneyard.drawBoneyardDomino();
                    GameManager.updateGameMsg(
                            "  >> Drawing from Boneyard..." + newDomino);
                    Hand.add(newDomino);
                    GameManager.sleep();// Sleep so user can see what's going on
                }
                return 1;
            }else{
                GameManager.updateGameMsg("  >> Skipping Computer's Turn");
                return 0;
            }
        }
    }


    /**
     * Draws a single string domino from player's hand. Does not know
     *  orientation of domino.
     */
    private void drawDomino(int dominoIndex){
        Board.addToBoard(Hand.remove(dominoIndex));
    }


    /**
     * Gets a domino selection from user.
     * @return
     */
    private int getUserDominoSelection(){
        GameManager.updateGameMsg("Which Domino?");
        return Integer.parseInt(
                Objects.requireNonNull(GameManager.getUserInput(isUser())));
    }


    public void showUserOptions(){
        System.out.println();  // Used for formatting

        if(canMatch()){
            System.out.println("  [p] Place Domino");
        }
        if(!Boneyard.isEmpty() && !canMatch()){
            System.out.println("  [d] Draw from Boneyard");
        }
        System.out.println("  [q] Quit");
    }
}
