package dominosgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Boneyard {
    private static List<String> BONEYARD;

    /**
     * Makes new dominos to play with. Max dot count can be changed.
     * Dominos created are unique.
     * @return
     */
    public static void makeNewDominoArray(){
        int MaxDomDot = 6;
        int offset = 0;

        BONEYARD = new ArrayList<String>();

        // Create Dominos + add to Boneyard
        for(int leftSide = MaxDomDot; leftSide >= 0; leftSide--){
            for(int rightSide = MaxDomDot-offset; rightSide >= 0; rightSide--){
                BONEYARD.add("[" + leftSide + " " + rightSide + "]");
            }
            offset++;
        }

        // Shuffle Domino order inside Graveyard to make it random
        Collections.shuffle(BONEYARD);
    }


    /**
     * Draws 1 Domino from Boneyard
     * @return 1 Domino
     */
    public static String drawBoneyardDomino(){
        return BONEYARD.remove(0);
    }


    /**
     * Checks if boneyard is empty (for win condition)
     * @return true if empty
     */
    public static boolean isEmpty(){
        return BONEYARD.size() == 0;
    }

    /**
     * Doesn't actually show boneyard, just size.
     */
    public static void showBoneyard(){
        GameManager.updateGameMsg(
                "Boneyard contains " + BONEYARD.size() + " pieces.");
    }


}
