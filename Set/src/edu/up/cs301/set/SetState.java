package edu.up.cs301.set;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * Contains the state of a Set game. Sent by the game when
 * a player wants to enquire about the state of the game.  (E.g., to display
 * it, or to help figure out its next move.)
 * 
 * @author Dillon Arnold
 * @version 28 October 2013
 */
public class SetState extends GameState
{
	private static final long serialVersionUID = -8269749892027578792L;

	// Instance variables
	
	// The decks of cards:
    //  - 0: Game deck
    //  - 1: Cards in play
    //  - 2: Discard deck
	private Deck[] decks;
    
    // Whose turn it is to select a set
    // Set to -1 when it is no player's turn
    private int toPlay;
    
    // Score of each player sorted by index
    private int[] scores;
    
    // If a set can be formed from the play area
    private boolean setPossible;

    /**
     * Constructor for objects of class SetState. Initializes for the beginning of the
     * game.
     *  
     */
    public SetState() {
    	// Initially no player's turn
    	toPlay = -1;
    	
    	// Initialize the decks as follows:
    	// - Game deck has all 81 cards shuffled
    	// - Move 12 cards to play deck
    	// - Discard deck empty
    	decks = new Deck[3];
    	decks[0] = new Deck(); // create empty deck
    	decks[1] = new Deck(); // create empty deck
    	decks[2] = new Deck(); // create empty deck
    	decks[0].add81(); // initialize game deck
    	decks[0].shuffle(); // shuffle the cards
    	
    	// Initialize scores to 0
    	scores[0] = 0;
    	scores[1] = 0;
    	scores[2] = 0;
    	scores[3] = 0;
    }
    
    
    /**
     * Copy constructor for objects of class SJState. Makes a copy of the given state
     *  
     * @param orig  the state to be copied
     */
    public SetState(SetState orig) {
    	// Copy toPlay
    	toPlay = orig.toPlay;
    	
    	// Copy decks
    	decks = new Deck[3];
    	decks[0] = orig.decks[0]; 
    	decks[1] = orig.decks[1]; 
    	decks[2] = orig.decks[2]; 
 
    	// Copy scores
    	scores[0] = orig.scores[0];
    	scores[1] = orig.scores[1];
    	scores[2] = orig.scores[2];
    	scores[3] = orig.scores[3];
    }
    
    /**
     * Tells which player's turn it is
     * 
     * @return the index (0-4, -1 if none) of the player whose turn it is.
     */
    public int toPlay() {
        return toPlay;
    }
    
    /**
     * Change whose move it is
     * 
     * @param idx - the index of the player whose move it now is
     */
    public void setToPlay(int idx) {
    	toPlay = idx;
    }
    
    /**
     * Returns the score of the player
     * 
     * @return idx - the index of the player
     */
    public int getScore(int idx) {
        return scores[idx];
    }
    
    /**
     * Sets the score of the player
     * 
     * @param idx - the index of the player
     * @param score - score of player
     */
    public void setScore(int idx, int score) {
        scores[idx] = score;
    }
    
    /**
     * Returns the deck
     * 
     * @return d - the deck
     */
    public Deck getDeck(int d) {
        return decks[d];
    }
    
    /**
     * Set if set can be formed
     * @return 
     */
    public void setSetPossible(boolean b) {
        setPossible = b;
    }
    
    /**
     * Returns if a set is possible
     */
    public boolean getSetPossible() {
        return setPossible;
    }
}
