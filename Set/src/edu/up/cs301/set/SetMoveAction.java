package edu.up.cs301.set;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * A game-move object that a set player sends to the game to make
 * a move.
 * 
 * @author Dillon Arnold
 * @version 1 November 2013
 */
public abstract class SetMoveAction extends GameAction {

	private static final long serialVersionUID = -6123440624650325087L;

	/**
     * Constructor for SetMoveAction
     *
     * @param player the player making the move
     */
    public SetMoveAction(GamePlayer player)
    {
        // invoke superclass constructor to set source
        super(player);
    }
    
    /**
     * @return whether the move was a call
     */
    public boolean isCall() {
    	return true;
    }
    
    /**
     * @return whether the move was a card
     */
    public boolean isCards() {
    	return false;
    }
    
    /**
     * @return whether the move was quit
     */
    public boolean isQuit() {
    	return false;
    }
}
