/**
 * CS062: silverdollar
 *	a simple coin-moving game implemented with ArrayLists
 *
 * @author Khai Mohammad
 */

package silverdollar;

import java.util.Random;
import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class TextCoinStrip {

    /**
     * the strip of coins. It is an arraylist with locations indexed starting at 0. A square is occupied by a coin if the boolean value at that
     * location is true.
     */
    protected ArrayList<Boolean> theStrip;

    /** The number of coins on the strip */
    protected int numCoins;

    /**
     * Constructs a representation of the Silver Dollar Game
     *
     * @param numSquares the number of squares
     * @param numCoins   the number of coins pre: 0 < numCoins < numSquares
     */
    public TextCoinStrip(int numSquares, int numCoins) {
        // Check precondition
        if (numCoins <= 0 || numCoins >= numSquares) {
            System.out.println("Game must be played with number of\n" + "coins less than number of squares");
            throw new IllegalArgumentException("# coins: " + numCoins + " must be positive " + "and less than # squares: " + numSquares);
        }

        theStrip = new ArrayList<Boolean>();

        // Populate all squares with false values
        for (int i = 0; i < numSquares; i++) {
            theStrip.add(false);
        }

        this.numCoins = numCoins;

        // Place #coins randomly on the strip
        Random rand = new Random();
        while (0 < numCoins) {
            int i = rand.nextInt(numSquares);
            if (!theStrip.get(i)) {
                theStrip.set(i, true);
                numCoins--;
            }
        }
    }

    /**
     * toString returns the string representation of a strip. e.g., "_o____oo_oo_" Note the autograder is very sensitive to whitespace.
     *
     * @return the string representation
     */
    public String toString() {
        String returnStr = "";
        for(boolean state : theStrip){
            returnStr += (state? 'o' : '_');
        }
        return returnStr;
    }

    /**
     * isLegalMove determines if a move is legal.
     *
     * @param start    the location of the coin to be moved
     * @param distance how far the coin is to move
     * @return true if the move is legal
     */
    public boolean isLegalMove(int start, int distance) {
        // first we check some conditions; is the starting move actually a puck, is the distance valid, is it possible to move there?
        if(start >= theStrip.size() || start < 0 || start - distance < 0 || distance == 0 || !theStrip.get(start)){
            return false;
        }
        // we check to see if there are any pucks in our way we need to skip over
        for(int i = 1; i<=distance;i++){
            if(theStrip.get(start-i)){
                return false;
            }
        }
        return true;
    }

    /**
     * makeMove makes a (legal) move.
     *
     * @param start    the location of the coin to be moved
     * @param distance how far the coin is to move pre-condition: the move must be a legal one
     */
    public void makeMove(int start, int distance) {
        // we remove the original element
        theStrip.remove(start);
        // we then replace it with something empty
        theStrip.add(start, false);
        // we remove the new element
        theStrip.remove(start-distance);
        // we then replace it with a new puck
        theStrip.add(start-distance, true);
    }

    /**
     * gameIsOver determines if a game is completed.
     *
     * @return true if there are no more moves
     */
    public boolean gameIsOver() {
        // first, we set a boolean in place to check if all falses are on the right
        boolean rightHandSide = false;
        // we check the Strip to see if there's a true
        for(int i = theStrip.size() - 1; i>=0;i--){
            if(rightHandSide){
                if(!theStrip.get(i)){
                    return false;
                }
            }
            // if we get a puck, we start asking if only pucks are after it. If there's a false after a puck, clearly all pucks aren't on the left yet
            if(theStrip.get(i)){
                rightHandSide = true;
            }
            
        }
        return true;

    }

    /**
     * play is a method to play the Silver Dollar Game until it is finished.
     */
    public void play() {
        Scanner scanner = new Scanner(System.in);
        int start = 0;
        int distance = 0;

        while (!gameIsOver()) {
            System.out.print(toString() + " Next move? ");
            start = scanner.nextInt();
            distance = scanner.nextInt();

            if (isLegalMove(start, distance)) {
                makeMove(start, distance);
            } else {
                System.out.println("Illegal move!");
            }
        }

        System.out.println(toString() + "You win!!");
        scanner.close();
    }

    /**
     * A demonstration main method. It simply constructs a 12-square strip with 5 coins and then plays the Silver Dollar Game.
     */
    public static void main(String[] args) {
        TextCoinStrip tcs = new TextCoinStrip(12, 5);
        tcs.play();
    }
}
