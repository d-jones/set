package edu.up.cs301.set;

import java.util.ArrayList;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;
import android.graphics.Color;

/**
 * this is the primary activity for Slapjack game
 * 
 * @author Steven R. Vegdahl
 * @version July 2013
 */
public class SetMainActivity extends GameMainActivity {
	
	public static final int PORT_NUMBER = 4752;

	/** A Set game for two players. The default is human vs. computer */
	@Override
	public GameConfig createDefaultConfig() {

		// Define the allowed player types
		ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();
		
		playerTypes.add(new GamePlayerType("human player (green)") {
			public GamePlayer createPlayer(String name) {
				return new SetHumanPlayer(name, Color.GREEN);
			}});
		playerTypes.add(new GamePlayerType("human player (blue)") {
			public GamePlayer createPlayer(String name) {
				return new SetHumanPlayer(name, Color.BLUE);
			}});
		playerTypes.add(new GamePlayerType("human player (red") {
			public GamePlayer createPlayer(String name) {
				return new SetHumanPlayer(name, Color.RED);
			}});
		playerTypes.add(new GamePlayerType("human player (yellow)") {
			public GamePlayer createPlayer(String name) {
				return new SetHumanPlayer(name, Color.YELLOW);
			}
		});
		playerTypes.add(new GamePlayerType("computer player (dumb)") {
			public GamePlayer createPlayer(String name) {
				return new SetComputerPlayer(name);
			}
		});
		playerTypes.add(new GamePlayerType("computer player (smart)") {
			public GamePlayer createPlayer(String name) {
				return new SetComputerPlayer(name, 0.3);
			}
		});

		// Create a game configuration class for Set
		GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "Set", PORT_NUMBER);

		// Add the default players
		defaultConfig.addPlayer("Human", 0);
		defaultConfig.addPlayer("Computer", 2);
		
		// Set the initial information for the remote player
		defaultConfig.setRemoteData("Guest", "", 1);
		
		//Done
		return defaultConfig;
	}//createDefaultConfig

	@Override
	public LocalGame createLocalGame() {
		return new SetLocalGame();
	}
}
