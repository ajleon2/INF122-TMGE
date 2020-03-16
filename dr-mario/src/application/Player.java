package application;

/**
 * Holds the player's name, score, and number of
 * tile matches.
 * @author Andrew Leon
 *
 */
public class Player {

	/**
	 * This player's name.
	 */
	private String name;
	/**
	 * This player's current score.
	 */
	private int score;
	/**
	 * The number of tile matches this player has achieved.
	 */
	private int numTileMatches;
	
	/**
	 * Create a new player with a score of 0, tile matches of
	 * 0, and the provided name.
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		this.score = 0;
		this.numTileMatches = 0;
	}
	
	/**
	 * Change the player's score by the amount given.
	 * @param theChange Added to the player's score.
	 */
	public void changeScore(int theChange) {
		this.score += theChange;
	}
	
	/**
	 * Change the count of how many tile matches this player has
	 * achieved by the given amount.
	 * @param theChange Added to the player's count of
	 * tile matches they've achieved.
	 */
	public void changeTileMatches(int theChange) {
		this.numTileMatches += theChange;
	}
	
	/**
	 * @return This player's current score.
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * @return The number of tile matches this player has
	 * achieved.
	 */
	public int getNumTileMatches() {
		return this.numTileMatches;
	}
	
	/**
	 * @return This player's name.
	 */
	public String getName() {
		return this.name;
	}
}
