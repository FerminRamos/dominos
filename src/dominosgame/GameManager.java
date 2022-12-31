package dominosgame;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * GameManager is a translator and game master. It translates string to GUI,
 *  dictates whether to print to console or update gui, get input from
 *  keyboard or mouse click. This is where you can switch between console or
 *  GUI modes.
 */
public class GameManager {
    //This determines game type
    final private static boolean ConsoleGame = false;
    private static int dominoGUIIndex;


    /**
     * Gets input based on game type (console game = keyboard)
     * @return user input
     */
    public static String getUserInput(boolean isUser){
        if(ConsoleGame){
            if(isUser){
                Scanner scanner = new Scanner(System.in);
                return scanner.nextLine();
            }else{
                return null;  // Computer turn, never used by player.
            }
        }else{
            // Returns domino clicked, previously recorded from getMouseEvent()
            return String.valueOf(dominoGUIIndex);
        }
    }

    /**
     * Register Mouse Input from User's Hand (Does NOT register input from
     * dominos on board). Contains most of GUI Logic for who to take a turn.
     * @return MouseEvent
     */
    public static EventHandler<MouseEvent> getMouseEvent(){
        // Register Mouse Input
        EventHandler<MouseEvent> mouseEventEventHandler = event -> {
            // Figure out which domino was clicked, ID later used for takeTurn()
            Rectangle dominoPressed = (Rectangle) event.getTarget();
            setDominoGUIIndex(Integer.parseInt(dominoPressed.getId()) + 1);

            // User Takes Turn
            GUI.getUser().takeTurn("p");
            GUI.getUser().showHand();

            // Computer Takes Turn
            GUI.getComputer().takeTurn("p");
            GUI.getComputer().showHand();

            // Hands out dominos if players can't match new board & not won yet
            if(!GUI.getUser().canMatch() && !GUI.getUser().hasWon()){
                GUI.getUser().takeTurn("d");  //Calls infinite loop
                GUI.getUser().showHand();
            }
            if(!GUI.getComputer().canMatch() && !GUI.getComputer().hasWon()){
                GUI.getComputer().takeTurn("d");  //Calls infinite loop
                GUI.getComputer().showHand();
            }

            GUI.checkWin();  // Updates GUI, if someone won.

            // Update Board based on User's Domino Picked
            GUI.updateBoard_GUI();

        };
        return mouseEventEventHandler;
    }


    public static void setDominoGUIIndex(int dominoIndex){
        dominoGUIIndex = dominoIndex;
    }


    /**
     * Updates game messages (mainly used for Console)
     */
    public static void updateGameMsg(String gameMsg){
        if(ConsoleGame){
            System.out.println(gameMsg);
        }
    }


    /**
     * Shows hand of user (to GUI or Console)
     * @param isUser
     */
    public static void showHand(boolean isUser, List<String> Hand){
        if(ConsoleGame){
            if(isUser){
                GameManager.updateGameMsg("User's Hand:" + Hand);
            }else{
                updateGameMsg("Playing with " + Hand.size() + " Dominos.");
            }
        }else{
            List<StackPane> GUI_Hand = new ArrayList<StackPane>();
            int handSize = Hand.size();

            // Send GUI dominos to display in GUI
            if(isUser){
                int leftDot;
                int rightDot;
                int ID = 0;

                // Translate each domino str into GUI domino
                for(int i = 0; i < handSize; i++){
                    leftDot = translateDominoStr(Hand.get(i), 'l');
                    rightDot = translateDominoStr(Hand.get(i), 'r');

                    // Add mouse input to newly created domino GUI
                    //  + set Identifier to know which domino was clicked
                    //  in hand.
                    StackPane domino = GUI.makeGUIDomino(leftDot, rightDot, ID);
                    domino.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            getMouseEvent());

                    // Send to GUI
                    GUI_Hand.add(domino);
                    ID++;
                }

                GUI.updateUserHand_GUI(GUI_Hand);
            }else{
                // Makes empty domino str into GUI domino (to hide hand)
                for(int i = 0; i < handSize; i++){
                    StackPane domino =
                            GUI.makeGUIDomino(-1,-1, -1);

                    GUI_Hand.add(domino);
                }

                GUI.updateCompHand_GUI(GUI_Hand);
            }
        }
    }



    /**
     * Console Only.
     * Makes program sleep so game doesn't go too fast for user
     */
    public static void sleep(){
        int milliseconds = 3000;
        if(ConsoleGame){
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Translates a single domino string (in format [c,c]) and returns the
     * int values. Domino string may contain a 0 = wildcard.
     * @param domino Domino String
     * @param side left or right dot to translate: l r
     * @return value of domino side (in int format)
     */
    public static int translateDominoStr(String domino, char side){
        if(side == 'l'){
            String leftInt = domino.split(" ")[0].split("")[1];
            return Integer.parseInt(leftInt);
        }else{
            String rightInt = domino.split(" ")[1].split("")[0];
            return Integer.parseInt(rightInt);
        }
    }


    /**
     * Runs a game type
     * @param args
     */
    public static void main(String[] args){
        if(ConsoleGame){
            Console.main(args);
        }else{
            GUI.main(args);
        }
    }
}
