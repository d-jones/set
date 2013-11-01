package edu.up.cs301.set;

import android.util.Log;
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
     * checks whether the game is over; if so, returns a string giving the result
     * 
     * @result
     * 		the end-of-game message, or null if the game is not over
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
     * sends the updated state to the given player. In our case, we need to
     * make a copy of the Deck, and null out all the cards except the top card
     * in the middle deck, since that's the only one they can "see"
     * 
     * @param p
     * 		the player to which the state is to be sent
     */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// if there is no state to send, ignore
		if (state == null) {
			return;
		}

		// make a copy of the state; null out all cards except for the
		// top card in the middle deck
		SetState stateForPlayer = new SetState(state); // copy of state
		stateForPlayer.nullAllButTopOf2(); // put nulls except for visible card
		
		// send the modified copy of the state to the player
		p.sendInfo(stateForPlayer);
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
	 * makes a move on behalf of a player
	 * 
	 * @param action
	 * 		the action denoting the move to be made
	 * @return
	 * 		true if the move was legal; false otherwise
	 */
	@Override
	protected boolean makeMove(GameAction action) {
		
		// Check that we have slap-jack action; if so cast it
		if (!(action instanceof SetMoveAction)) {
			return false;
		} 
		SetCallAction sma = (SetMoveAction) action;
		
		// Get the index of the player making the move; return false
		int playerIdx = getPlayerIdx(sma.getPlayer());
		
		if (playerIdx < 0) { // illegal player
			return false;
		}

		if (sma.isCall()) {
			// If we have a call 
			state.setToPlay(playerIdx);
		}
		else if (sma.isCards()) { // we have a "play" action
			if (thisPlayerIdx != state.toPlay()) {
				// attempt to play when it's the other player's turn
				return false;
			}
			else {
				// it's the correct player's turn: move the top card from the
				// player's deck to the top of the middle deck
				state.getDeck(thisPlayerIdx).moveTopCardTo(state.getDeck(2));
				// if the opponent has any cards, make it the opponent's move
				if (state.getDeck(1-thisPlayerIdx).size() > 0) {
					state.setToPlay(1-thisPlayerIdx);
				}
			}
		}
		else { // some unexpected action
			return false;
		}

		// return true, because the move was successful if we get her
		return true;
	}
	
	/**
	 * helper method that gives all the cards in the middle deck to
	 * a given player; also shuffles the target deck
	 * 
	 * @param idx
	 * 		the index of the player to whom the cards should be given
	 */
	private void giveMiddleCardsToPlayer(int idx) {
		// illegal player: ignore
		if (idx < 0 || idx > 1) return;
		
		// move all cards from the middle deck to the target deck
		state.getDeck(2).moveAllCardsTo(state.getDeck(idx));
		
		// shuffle the target deck
		state.getDeck(idx).shuffle();
	}
}
