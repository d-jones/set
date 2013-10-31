package edu.up.cs301.set;

import java.io.Serializable;
import java.util.ArrayList;

import edu.up.cs301.card.Card;

/**
 * Deck class - represents a deck of cards (not necessarily a full one)
 * 
 * @author Steven R. Vegdahl
 * @author Dillon Arnold
 * @version 30 October 2013
 *
 */
public class Deck implements Serializable {

	// to satisfy Serializable interface
	private static final long serialVersionUID = 3216223171210121485L;

	// The cards in our deck; the last card in the ArrayList is the top card
	// in the deck
	private ArrayList<Card> cards;

	/**
	 * Constructor, creating an empty deck
	 */
	public Deck() {
		cards = new ArrayList<Card>();
	}

	/** Copy constructor, making an exact copy of a deck
	 * 
	 * @param orig - the deck from which the copy is made
	 */
	public Deck(Deck orig) {
		// Synchronize to ensure that original is not being modified as we
		// iterate over it
		synchronized(orig.cards) {
			// Create a new arrayList for our new deck; add each card in it
			cards = new ArrayList<Card>();
			for (Card c: orig.cards) {
				cards.add(c);
			}
		}
	}

	/**
	 * Adds one of each card, increasing the size of the deck by 81. 
	 * 
	 * @return - the deck
	 */
	public Deck add81() {
		// add the cards
		for (char s1 : "DOT".toCharArray()) {
			for (char s2 : "ESF".toCharArray()) {
				for (char c : "RBG".toCharArray()) {
					for (int i : "123".toCharArray()) {
						this.add(Card.fromString(""+s1+s2+c+i));
					}
				}
			}
		}
		// return the deck
		return this;
	}

	/**
	 * Shuffle all of the cards in the deck
	 */
	public Deck shuffle() {
		// Synchronize so that we don't have someone trying to modify the
		// deck as we're modifying it
		synchronized (this.cards) {
			// Go through a loop that randomly rearranges the cards
			for (int i = cards.size(); i > 1; i--) {
				int spot = (int)(i*Math.random());
				Card temp = cards.get(spot);
				cards.set(spot, cards.get(i-1));
				cards.set(i-1, temp);
			}
		}
		// return the deck
		return this;
	}

	/**
	 * Moves the top card of the current deck to the top of another; does nothing if
	 * the first deck is empty
	 * 
	 * @param targetDeck - the deck to which the card should be moved
	 */
	public void moveTopCardTo(Deck targetDeck) {

		// Will hold the card
		Card c = null;

		// size of the first deck
		int size;

		// Indivisibly check the deck for empty, and remove the card, to
		// avoid a race condition
		synchronized(this.cards) {
			size = this.size();
			if (size > 0) {
				c = cards.remove(cards.size()-1);
			}
		}

		// If the original size was non-zero, add the card to the top of the
		// target deck
		if (size > 0) {
			targetDeck.add(c);
		}
	}
	
	/**
	 * Moves the target card of the current deck to the top of another; does nothing if
	 * the first deck is empty
	 * 
	 * @param targetCard - the card that will be moved
	 * @param targetDeck - the deck to which the card should be moved
	 */
	public void moveCardTo(Card targetCard, Deck targetDeck) {
	
	}

	/**
	 * Add a card to the top of a deck
	 * 
	 * @param c - the card to add
	 */
	public void add(Card c) {
		// Synchronize so that the underlying ArrayList is not accessed
		// inconsistently
		synchronized(this.cards) {
			cards.add(c);
		}
	}

	/**
	 * @return the number of cards in the deck
	 */
	public int size() {
		return cards.size();
	}

}
