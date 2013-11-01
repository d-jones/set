package edu.up.cs301.set;

import android.util.Log;
import edu.up.cs301.card.Card;
import edu.up.cs301.card.Rank;
import edu.up.cs301.game.Game;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.config.GameConfig;

/**
 * The LocalGame class for a Set game.  Defines and enforces
 * the game rules; handles interactions between players.
 * 
 * @author Dillon Arnold 
 * @version 1 November 2013
 */

public class SetLocalGame extends LocalGame implements Game {

    // The game's state
    SetState state;

    /**
     * Constructor for the SetLocalGame.
     */
    public SetLocalGame() {
        Log.i("SetLocalGame", "creating game");
        // Create the state for the beginning of the game
        state = new SetState();
    }


    /**
     * Checks whether the game is over; if so, returns a string giving the result
     * 
     * @result The end-of-game message, or null if the game is not over
     */
    @Override
    protected String checkIfGameOver() {
    	// No more sets can be formed
    	if(state.getDeck(0).size() == 0) {
    		if(state.getSetPossible() == false) {
    			int maxScore;
    			int playerIdx;
    			maxScore = state.getScore(0);
    			playerIdx = 0;
    			boolean draw = false;
    			for (int i = 1; i < numPlayers; i++) {
    				if (state.getScore(i) > maxScore) {
    					maxScore = state.getScore(i);
    	    			playerIdx = i;
    	    			draw = false;
    				}
    				else if (state.getScore(i) == maxScore) {
    					draw = true;
    				}
    			}
    			if (!draw) {
    				return this.playerNames[playerIdx]+" is the winner";
    			}
    			else return "Game is a draw";
    		}
    	}
    	else {
    		// Sets can still be formed
    		return null;
    	}
    }

    /**
     * Sends state to player
     * 
     * @param p - the player to which the state is to be sent
     */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// If there is no state to send, ignore
		if (state == null) {
			return;
		}
		
		// Send state to player
		p.sendInfo(state);
	}
	
	/**
	 * whether a player is allowed to move
	 * 
	 * @param playerIdx
	 * 		the player-number of the player in question
	 */
	protected boolean canMove(int playerIdx) {
		if (playerIdx < 0 || playerIdx > 1) {
			// If our player-number is out of range, return false
			return false;
		}
		else {
			// Player can move
			return state.toPlay() == playerIdx;
		}
	}

	/**
	 * Makes a move on behalf of a player
	 * 
	 * @param action - the action denoting the move to be made
	 * 
	 * @return true if the move was legal; false otherwise
	 */
	@Override
	protected boolean makeMove(GameAction action) {
		
		// Check that we have slap-jack action; if so cast it
		if (!(action instanceof SetMoveAction)) {
			return false;
		} 
		SetMoveAction sma = (SetMoveAction) action;
		
		// Get the index of the player making the move; return false
		int playerIdx = getPlayerIdx(sma.getPlayer());
		
		if (playerIdx < 0) { // illegal player
			return false;
		}

		if (sma.isCall()) {
			// If we have a call 
			state.setToPlay(playerIdx);
		}
		else if (sma.isCards()) { // Player selected cards
			if (playerIdx != state.toPlay()) {
				// Attempt to play when it's the other player's turn
				return false;
			}
			else {
				// It's the correct player's turn: process the chosen cards
				// and update state accordingly
				Card c1 = ((SetCardsAction) sma).getCard(0);
				Card c2 = ((SetCardsAction) sma).getCard(1);
				Card c3 = ((SetCardsAction) sma).getCard(2);
				
				if (isSet(c1, c2, c3)) {
					state.setScore(playerIdx,state.getScore(playerIdx) + 1);
				}
				else {
					//Penalty
				}
				state.setToPlay(-1);
			}
		}
		// Some unexpected action
		else return false;

		// Move was successful 
		return true;
	}
	
}
