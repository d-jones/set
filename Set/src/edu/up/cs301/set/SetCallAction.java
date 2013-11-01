package edu.up.cs301.set;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * A game-move object that a Set player sends to the game to make
 * a move.
 * 
 * @author Dillon Arnold
 * @version 1 November 2013
 */
public abstract class SetCallAction extends SJMoveAction {
	
	private static final long serialVersionUID = -3107100271012188849L;

    /**
     * Constructor for SetCallAction
     *
     * @param player - the player making the move
     */
    public SetCallAction(GamePlayer player)
    {
        // Invoke superclass constructor to set source
        super(player);
    }
    
    /**
     * @return whether the move was a call
     */
    public boolean isCall() {
    	return true;
    }
}
