package application;

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
	 * The number of rows this player has cleared.
	 */
	private int rowsCleared;
	
	public Player(String name) {
		this.name = name;
		this.score = 0;
		this.rowsCleared = 0;
	}
	
	/**
	 * Change the player's score by the amount given.
	 * @param theChange Added to the player's score.
	 */
	public void changeScore(int theChange) {
		this.score += theChange;
	}
	
	/**
	 * Change the count of how many rows this player has cleared
	 * by the given amount.
	 * @param theChange Added to the player's count of how many rows
	 * they've cleared.
	 */
	public void changeRowsCleared(int theChange) {
		this.rowsCleared += theChange;
	}
	
	/**
	 * @return This player's current score.
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * @return The number of rows this player has cleared.
	 */
	public int getRowsCleared() {
		return this.rowsCleared;
	}
}
