package dominosgame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;


/**
 * GUI runs Domino Game on GUI
 */
public class GUI extends Application {
    private static Player User;
    private static Player Computer;
    private static HBox userHand_GUI;
    private static HBox compHand_GUI;
    private static HBox board_GUI_Top;
    private static HBox board_GUI_Bot;
    private static Text userWonText;
    private static Text compWonText;

    public static void main(String[] args){launch(args);}


    /**
     * Adds a domino to user's hand in GUI
     * @param Hand Is a GUI version of user's hand
     */
    public static void updateUserHand_GUI(List<StackPane> Hand){
        // Clear Hand GUI
        userHand_GUI.getChildren().clear();

        // Add to GUI
        int handSize = Hand.size();
        for(int i = 0; i < handSize; i++){
            userHand_GUI.getChildren().add(Hand.get(i));
        }
    }


    /**
     * Adds a domino to computer's hand in GUI
     * @param Hand Is a GUI version of computer's hand
     */
    public static void updateCompHand_GUI(List<StackPane> Hand){
        // Clear Hand GUI
        compHand_GUI.getChildren().clear();

        // Add to GUI
        int handSize = Hand.size();
        for(int i = 0; i < handSize; i++){
            compHand_GUI.getChildren().add(Hand.get(i));
        }
    }


    /**
     * Updates board in GUI
     */
    public static void updateBoard_GUI(){
        // Clear GUI Board (needed to remove duplicate GUIs)
        board_GUI_Top.getChildren().clear();
        board_GUI_Bot.getChildren().clear();

        // Spacing (insert top or bottom, depending on Start_Top_Row)
        HBox spacing = new HBox();
        spacing.setPrefSize(60,60);

        // Add board components to Board GUI
        int boardSize = Board.getBoard().size();
        if(Board.isStartTopRow()){
            for(int i = 0; i < boardSize; i+=2){
                int leftDot = GameManager.translateDominoStr(
                        Board.getBoard().get(i), 'l');
                int rightDot = GameManager.translateDominoStr(
                        Board.getBoard().get(i), 'r');

                // Create and add to Domino GUI
                board_GUI_Top.getChildren().add(
                        makeGUIDomino(leftDot,rightDot,-1));
            }

            board_GUI_Bot.getChildren().add(spacing);

            for(int i = 1; i < boardSize; i+=2){
                int leftDot = GameManager.translateDominoStr(
                        Board.getBoard().get(i), 'l');
                int rightDot = GameManager.translateDominoStr(
                        Board.getBoard().get(i), 'r');

                // Create and add to Domino GUI
                board_GUI_Bot.getChildren().add(
                        makeGUIDomino(leftDot,rightDot,-1));
            }
        }else{
            board_GUI_Top.getChildren().add(spacing);

            for(int i = 1; i < boardSize; i+=2){
                int leftDot = GameManager.translateDominoStr(
                        Board.getBoard().get(i), 'l');
                int rightDot = GameManager.translateDominoStr(
                        Board.getBoard().get(i), 'r');

                // Create and add to Domino GUI
                board_GUI_Top.getChildren().add(makeGUIDomino(
                        leftDot,rightDot,-1));
            }
            for(int i = 0; i < boardSize; i+=2){
                int leftDot = GameManager.translateDominoStr(
                        Board.getBoard().get(i), 'l');
                int rightDot = GameManager.translateDominoStr(
                        Board.getBoard().get(i), 'r');

                // Create and add to Domino GUI
                board_GUI_Bot.getChildren().add(
                        makeGUIDomino(leftDot,rightDot,-1));
            }
        }

    }


    /**
     * Gets User Player
     * @return user object
     */
    public static Player getUser(){
        return User;
    }

    /**
     * Gets Computer Player
     * @return computer object
     */
    public static Player getComputer(){
        return Computer;
    }


    /**
     * Called by MouseEvent, if a Player won, show on GUI.
     */
    public static void checkWin(){
        if(getUser().hasWon()){
            // Tell User
            userWonText.setFill(Color.GREEN);

        }else if(getComputer().hasWon()){
            // Tell User
            compWonText.setFill(Color.RED);

            // TODO: Prevent User from clicking on Dominos (cont. game)

        }
    }

    /**
     * Makes a single GUI Domino. Default max dot count = 6.
     *  Function generates all possible dot positions for max dot count 6,
     *  and later chooses which dots to include for our domino design.
     *  [-1 -1] = null domino. Used to show computer's hand w/out revealing dots
     *  Domino Cover is used to cover a Domino's components under 1 "clickable"
     *  blanket, user can click a dot or line or rectangle, and the click ID
     *  will register for the entire domino, not for an individual component
     *  of a domino.
     */
    public static StackPane makeGUIDomino(int leftDots, int rightDots, int ID){
        // Domino Cover
        Rectangle dominoCover = new Rectangle();
        dominoCover.setHeight(60);
        dominoCover.setWidth(130);
        dominoCover.setFill(Color.TRANSPARENT);
        dominoCover.setId(String.valueOf(ID));

        // Domino Outline
        Rectangle dominoOutline = new Rectangle();
        dominoOutline.setHeight(60);
        dominoOutline.setWidth(130);
        dominoOutline.setFill(Color.BEIGE);
        dominoOutline.setStroke(Color.BLACK);
        dominoOutline.setStrokeWidth(1);
        // Domino Line Separator
        Line dominoLine = new Line();
        dominoLine.setEndY(60);
        // {Left Dot} Top Left
        Circle leftDot_TopLeft = new Circle();
        leftDot_TopLeft.setRadius(5);
        leftDot_TopLeft.setTranslateX(leftDot_TopLeft.getTranslateX() - 55);
        leftDot_TopLeft.setTranslateY(leftDot_TopLeft.getTranslateY() - 20);
        // {Left Dot} Mid Left
        Circle leftDot_MidLeft = new Circle();
        leftDot_MidLeft.setRadius(5);
        leftDot_MidLeft.setTranslateX(leftDot_MidLeft.getTranslateX() - 55);
        // {Left Dot} Bot Left
        Circle leftDot_BotLeft = new Circle();
        leftDot_BotLeft.setRadius(5);
        leftDot_BotLeft.setTranslateX(leftDot_BotLeft.getTranslateX() - 55);
        leftDot_BotLeft.setTranslateY(leftDot_BotLeft.getTranslateY() + 20);
        // {Left Dot} Mid Mid
        Circle leftDot_MidMid = new Circle();
        leftDot_MidMid.setRadius(5);
        leftDot_MidMid.setTranslateX(leftDot_MidMid.getTranslateX() - 35);
        // {Left Dot} Top Right
        Circle leftDot_TopRight = new Circle();
        leftDot_TopRight.setRadius(5);
        leftDot_TopRight.setTranslateX(leftDot_TopRight.getTranslateX() - 15);
        leftDot_TopRight.setTranslateY(leftDot_TopRight.getTranslateY() - 20);
        // {Left Dot} Mid Right
        Circle leftDot_MidRight = new Circle();
        leftDot_MidRight.setRadius(5);
        leftDot_MidRight.setTranslateX(leftDot_MidRight.getTranslateX() - 15);
        // {Left Dot} Bot Right
        Circle leftDot_BotRight = new Circle();
        leftDot_BotRight.setRadius(5);
        leftDot_BotRight.setTranslateX(leftDot_BotRight.getTranslateX() - 15);
        leftDot_BotRight.setTranslateY(leftDot_BotRight.getTranslateY() + 20);



        // {Right Dot} Top Left
        Circle rightDot_TopLeft = new Circle();
        rightDot_TopLeft.setRadius(5);
        rightDot_TopLeft.setTranslateX(rightDot_TopLeft.getTranslateX() + 55);
        rightDot_TopLeft.setTranslateY(rightDot_TopLeft.getTranslateY() + 20);
        // {Right Dot} Mid Left
        Circle rightDot_MidLeft = new Circle();
        rightDot_MidLeft.setRadius(5);
        rightDot_MidLeft.setTranslateX(rightDot_MidLeft.getTranslateX() + 55);
        // {Right Dot} Bot Left
        Circle rightDot_BotLeft = new Circle();
        rightDot_BotLeft.setRadius(5);
        rightDot_BotLeft.setTranslateX(rightDot_BotLeft.getTranslateX() + 55);
        rightDot_BotLeft.setTranslateY(rightDot_BotLeft.getTranslateY() - 20);
        // {Right Dot} Mid Mid
        Circle rightDot_MidMid = new Circle();
        rightDot_MidMid.setRadius(5);
        rightDot_MidMid.setTranslateX(rightDot_MidMid.getTranslateX() + 35);
        // {Right Dot} Top Right
        Circle rightDot_TopRight = new Circle();
        rightDot_TopRight.setRadius(5);
        rightDot_TopRight.setTranslateX(rightDot_TopRight.getTranslateX() + 15);
        rightDot_TopRight.setTranslateY(rightDot_TopRight.getTranslateY() + 20);
        // {Right Dot} Mid Right
        Circle rightDot_MidRight = new Circle();
        rightDot_MidRight.setRadius(5);
        rightDot_MidRight.setTranslateX(rightDot_MidRight.getTranslateX() + 15);
        // {Right Dot} Bot Right
        Circle rightDot_BotRight = new Circle();
        rightDot_BotRight.setRadius(5);
        rightDot_BotRight.setTranslateX(rightDot_BotRight.getTranslateX() + 15);
        rightDot_BotRight.setTranslateY(rightDot_BotRight.getTranslateY() - 20);



        StackPane domino = new StackPane();
        domino.getChildren().addAll(dominoOutline, dominoLine);

        switch (leftDots){
            case -1:
                domino.getChildren().remove(dominoLine);
            case 0:
                break;
            case 1:
                domino.getChildren().addAll(leftDot_MidMid);
                break;
            case 2:
                domino.getChildren().addAll(leftDot_TopLeft, leftDot_BotRight);
                break;
            case 3:
                domino.getChildren().addAll(
                        leftDot_TopLeft,leftDot_MidMid,leftDot_BotRight);
                break;
            case 4:
                domino.getChildren().addAll(
                        leftDot_TopLeft,leftDot_BotLeft,leftDot_TopRight,
                        leftDot_BotRight);
                break;
            case 5:
                domino.getChildren().addAll(
                        leftDot_TopLeft,leftDot_BotLeft,leftDot_TopRight,
                        leftDot_BotRight,leftDot_MidMid);
                break;
            default:
                domino.getChildren().addAll(
                        leftDot_TopLeft,leftDot_MidLeft,leftDot_BotLeft,
                        leftDot_TopRight,leftDot_MidRight,leftDot_BotRight);
                break;
        }

        switch (rightDots){
            case -1:
            case 0:
                break;
            case 1:
                domino.getChildren().addAll(rightDot_MidMid);
                break;
            case 2:
                domino.getChildren().addAll(rightDot_TopLeft,rightDot_BotRight);
                break;
            case 3:
                domino.getChildren().addAll(
                        rightDot_TopLeft,rightDot_MidMid,rightDot_BotRight);
                break;
            case 4:
                domino.getChildren().addAll(
                        rightDot_TopLeft,rightDot_BotLeft,rightDot_TopRight,
                        rightDot_BotRight);
                break;
            case 5:
                domino.getChildren().addAll(
                        rightDot_TopLeft,rightDot_BotLeft,rightDot_TopRight,
                        rightDot_BotRight,rightDot_MidMid);
                break;
            default:
                domino.getChildren().addAll(
                        rightDot_TopLeft,rightDot_MidLeft,rightDot_BotLeft,
                        rightDot_TopRight,rightDot_MidRight,rightDot_BotRight);
                break;
        }

        // Add cover to unify domino's components
        domino.getChildren().add(dominoCover);

        return domino;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create list of new playable dominos
        Boneyard.makeNewDominoArray();

        // Initialized GUI Hands for User & Computer
        userHand_GUI = new HBox();
        compHand_GUI = new HBox();
        userHand_GUI.setTranslateX(40);
        userHand_GUI.setTranslateY(userHand_GUI.getTranslateY()+520);
        userHand_GUI.setSpacing(5);
        compHand_GUI.setTranslateX(40);
        compHand_GUI.setTranslateY(compHand_GUI.getTranslateY()+20);
        compHand_GUI.setSpacing(5);

        // Create Players
        Computer = new Player(false);
        User = new Player(true);

        // Create Board for users to play on
        board_GUI_Top = new HBox();
        board_GUI_Bot = new HBox();
        //board_GUI_Top.setBackground(Background.fill(Color.RED));
        //board_GUI_Bot.setBackground(Background.fill(Color.RED));
        board_GUI_Top.setSpacing(5);
        board_GUI_Bot.setSpacing(5);
        board_GUI_Bot.setTranslateY(board_GUI_Bot.getTranslateY()+60);
        Board.createNewBoard();

        // Create Scroll Wheel for Board
        Group scrollGroup = new Group(board_GUI_Top, board_GUI_Bot);

        ScrollPane scroll = new ScrollPane(scrollGroup);
        scroll.setTranslateX(scroll.getTranslateX()+100);
        scroll.setTranslateY(scroll.getTranslateY()+250);
        scroll.setPrefSize(900, 135);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Show User & Computer Hands in GUI
        User.showHand();
        Computer.showHand();

        // Win Text (for finale)
        userWonText = new Text("User Won!!");
        compWonText = new Text("Computer Won!?");
        compWonText.setTranslateY(compWonText.getTranslateY()+170);
        compWonText.setTranslateX(compWonText.getTranslateX()+450);
        compWonText.setFont(Font.font("verdana", FontWeight.BOLD,
                FontPosture.ITALIC, 24));
        compWonText.setFill(Color.TRANSPARENT);
        userWonText.setTranslateY(userWonText.getTranslateY()+450);
        userWonText.setTranslateX(userWonText.getTranslateX()+450);
        userWonText.setFont(Font.font("verdana", FontWeight.BOLD,
                FontPosture.ITALIC, 24));
        userWonText.setFill(Color.TRANSPARENT);


        Group sceneGroup = new Group(userHand_GUI, compHand_GUI, scroll,
                userWonText, compWonText);

        Scene scene = new Scene(sceneGroup, 1200, 600);
        //scene.setFill(Color.FORESTGREEN);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Domino Game");
        primaryStage.show();
    }
}
