package edu.up.cs301.set;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GamePlayer;

/**
 * A SetCardsAction is an action that represents the three cards
 * the player has chosen.
 * 
 * @author Dillon Arnold
 * @version 28 October 2013
 */
public class SetCardsAction extends SetMoveAction
{
	private static final long serialVersionUID = 3250639793499599047L;

	private Card[] cards = new Card[3];

	/**
	 * Constructor for the SetCardsAction class.
	 * 
	 * @param player  the player making the move
	 * @param c1 the first card chosen
	 * @param c2 the second card chosen
	 * @param c3 the third card chosen
	 */
	public SetCardsAction(GamePlayer player, Card c1, Card c2, Card c3)
	{
		// Initialize the player with the superclass constructor
		super(player);

		// Add cards to array
		cards[0] = c1;
		cards[1] = c2;
		cards[2] = c3;
	}

	public Card getCard(int c) {
		if (c >= 0 && c <= 2) {
			return cards[c];
		}
		else return null;
	}

	/**
	 * @return whether this action is a card 
	 */
	public boolean isCards() {
		return true;
	}

}
