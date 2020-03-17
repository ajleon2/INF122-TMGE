package application;

import java.util.Arrays;
import java.util.Random;

import application.Tile.TileType;

public class GameLogic {
	
	/**
	 * The min number of tiles that must match in a row (or column)
	 * for those tiles to be considered "matched".
	 */
	private static final int MIN_TILES_MATCHED = 4;
	
	/**
	 * Converts the number of viruses killed to number of points earned.
	 * @param numVirusesKilled The number of viruses killed.
	 * @return The corresponding points earned.
	 */
	public static int getPoints(int numVirusesKilled) {
		return numVirusesKilled * 100;
	}
	
	/**
	 * @param gameMesh The game mesh.
	 * @return True if the given game mesh is devoid of viruses;
	 * False if otherwise.
	 */
	public static boolean allVirusesKilled(GameMesh gameMesh) {
		for (int row = 0; row < gameMesh.getNumRows(); row++) {
			for (int col = 0; col < gameMesh.getNumColumns(); col++) {
				if (!gameMesh.isEmpty(row, col) && gameMesh.getTile(row, col).isVirus()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param pill The Pill.
	 * @param gameMesh Manages the positions of tiles on the game mesh.
	 * @return True if the given Pill doesn't collide with a tile on the game mesh.
	 * False if otherwise.
	 */
	public static boolean canSpawnPill(Pill pill, GameMesh gameMesh) {
		Tile tileA = pill.getTileA();
		Tile tileB = pill.getTileB();
		
		try {
			return (gameMesh.isEmpty(tileA.getRow(), tileA.getColumn()) && 
					gameMesh.isEmpty(tileB.getRow(), tileB.getColumn()) );
		}
		
		catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	
	/**
	 * Clears all matching tiles on the game mesh, then returns the number
	 * of viruses killed. Also marks any pill tiles that are left alone.
	 * @param gameMesh Manages the positions of tiles on the game mesh.
	 * @return Number of viruses killed.
	 */
	public static int clearMatchingTiles(GameMesh gameMesh) {
		
		// 1st: Get a 2-D boolean array that marks every tile which needs
		//      to be deleted.
		boolean[][] tilesToDelete = getTilesToDelete(gameMesh, MIN_TILES_MATCHED);
		
		// 2nd: Delete marked tiles. If a pill tile w/ a companion pill tile
		//      is deleted, mark the companion as no longer having a companion.
		//      Also, count the number of deleted viruses.
		int numVirusesKilled = deleteTiles(gameMesh, tilesToDelete);
		
		// 3rd: For each pill Tile that no longer has a companion, move said pill
		// as far down as it can go (from bottom row to top row).
		//moveLonerPillsDown(gameMesh);
		
		return numVirusesKilled;
	}
	
	/**
	 * Deletes all tiles in "gameMesh" that are marked for deletion
	 * by "tilesToDelete". If a pill tile w/ a companion pill tile
	 * is deleted, mark the companion as no longer having a companion.
	 * Returns the number of deleted viruses.
	 * @param gameMesh Manages the positions of tiles on the game mesh.
	 * @param tilesToDelete A 2D boolean array that marks which tiles need
	 * to be deleted w/ "True" and those that don't with "False".
	 * @return The number of viruses deleted.
	 */
	private static int deleteTiles(GameMesh gameMesh, boolean[][] tilesToDelete) {
		int numVirusesKilled = 0;
		for (int row = 0; row < gameMesh.getNumRows(); row++) {
			for (int col = 0; col < gameMesh.getNumColumns(); col++) {
				if (tilesToDelete[row][col] == true) {
					Tile tile = gameMesh.getTile(row, col);
					if (tile.isVirus())
						numVirusesKilled += 1;
					else if (tile.hasCompanion()) { // If the deletion creates a lone pill tile, mark the lone pill tile as such
						int companionRow = tile.getCompanionRow();
						int companionCol = tile.getCompanionColumn();
						gameMesh.getTile(companionRow, companionCol).setCompanionDirection(Tile.Direction.NONE);
					}
					gameMesh.removeTile(row, col);
				}
			}
		}
		
		return numVirusesKilled;
	}

	
	/**
	 * @param gameMesh Manages the positions of tiles on the game mesh.
	 * @param minMatching How many tiles of the same color must match
	 * for those tiles to be considered "matched".
	 * @return A 2D array of boolean values whose cells represent
	 * the tiles of the game mesh. If a cell is True, the corresponding
	 * game mesh tile should be deleted; if a cell is False, the 
	 * corresponding tile shouldn't be deleted.
	 */
	private static boolean[][] getTilesToDelete(GameMesh gameMesh, int minMatching) {		
		final int NUM_ROWS = gameMesh.getNumRows();
		final int NUM_COLS = gameMesh.getNumColumns();
		
		boolean[][] tilesToDelete = new boolean[NUM_ROWS][NUM_COLS];
		for (int row = 0; row < NUM_ROWS; row++)
			Arrays.fill(tilesToDelete[row], false);
		
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				if (!gameMesh.isEmpty(row, col))
					tilesToDelete = markMatching(gameMesh, tilesToDelete, gameMesh.getTile(row, col), minMatching);
			}
		}
		
		return tilesToDelete;
	}
	
	/**
	 * Checks tiles horizontally and vertically about a given center
	 * tile. If the number of tile matches is at least "minMatching",
	 * returns a 2D boolean array that marks those matched tiles for
	 * deletion; otherwise, returns "tilesToDelete".
	 * @param gameMesh Manages the positions of tiles on the game mesh.
	 * @param tilesToDelete A 2D array that marks tiles for deletion.
	 * @param centerTile The center tile to search horizontally and
	 * vertically from for matches.
	 * @param minMatching The minimum number of tiles to match in a 
	 * row (or column) for tiles to be considered "matching".
	 * @return A 2D boolean array that marks those matched tiles for
	 * deletion; otherwise, returns "tilesToDelete".
	 */
	private static boolean[][] markMatching(GameMesh gameMesh, boolean[][] tilesToDelete, Tile centerTile, int minMatching) {
		boolean[][] result;
		
		// Check for matches horizontally about the center tile
		// If the number of horizontal matches >= numMatching, mark those spots as matching
		result = markHorizontalMatches(gameMesh, tilesToDelete, centerTile, minMatching);
		
		// Check for matches vertically about the center tile
		// If the number of vertical matches >= numMatching, mark those spots as matching
		result = markVerticalMatches(gameMesh, result, centerTile, minMatching);
		
		return result;
	}
	
	/**
	 * Helper function for "markMatching"
	 */
	private static boolean[][] markVerticalMatches(GameMesh gameMesh, boolean[][] tilesToDelete, Tile centerTile, int minMatching) {
		final int CENTER_ROW = centerTile.getRow();
		final int CENTER_COL = centerTile.getColumn();
		
		// 1st: Create a copy of tilesToDelete which we can modify. Mark the center
		//      tile as needing to be deleted.
		boolean ttdCopy[][] = copy2DArray(tilesToDelete);
		ttdCopy[CENTER_ROW][CENTER_COL] = true;
		
		// 2nd: Mark any tiles that match vertically along the center tile. Count
		//      how many tiles have been matched so far.
		int numberOfMatches = 1; // initially 1 for the center tile
		// Search Up
		for (int row = CENTER_ROW - 1; row >= 0; row--) {
			if (gameMesh.isEmpty(row, CENTER_COL) || !matches(centerTile, gameMesh.getTile(row, CENTER_COL)))
				break;
			
			ttdCopy[row][CENTER_COL] = true;
			numberOfMatches += 1;
		}
		
		// Search Down
		for (int row = CENTER_ROW + 1; row < gameMesh.getNumRows(); row++) {
			if (gameMesh.isEmpty(row, CENTER_COL) || !matches(centerTile, gameMesh.getTile(row, CENTER_COL)))
				break;
			
			ttdCopy[row][CENTER_COL] = true;
			numberOfMatches += 1;
		}
		
		// 3rd: If we've reached the minimum number of matches, return the modified ttd. Else, return
		//      the original.
		if (numberOfMatches >= minMatching)
			return ttdCopy;
		else
			return tilesToDelete;
	}
	
	private static boolean[][] copy2DArray(boolean[][] original) {
		boolean[][] copy = new boolean[original.length][original[0].length];
		for (int r = 0; r < original.length; r++) {
			for (int c = 0; c < original[0].length; c++) {
				copy[r][c] = original[r][c];
			}
		}
		
		return copy;
	}
	
	/**
	 * Helper function for "markMatching"
	 */
	private static boolean[][] markHorizontalMatches(GameMesh gameMesh, boolean[][] tilesToDelete, Tile centerTile, int minMatching) {
		final int CENTER_ROW = centerTile.getRow();
		final int CENTER_COL = centerTile.getColumn();
		
		// 1st: Create a copy of tilesToDelete which we can modify. Mark the center
		//      tile as needing to be deleted.
		boolean ttdCopy[][] = copy2DArray(tilesToDelete);
		ttdCopy[CENTER_ROW][CENTER_COL] = true;
		
		// 2nd: Mark any tiles that match horizontally along the center tile. Count
		//      how many tiles have been matched so far.
		int numberOfMatches = 1; // Initially 1 for center tile
		// Search left
		for (int col = CENTER_COL - 1; col >= 0; col--) {
			if (gameMesh.isEmpty(CENTER_ROW, col) || !matches(centerTile, gameMesh.getTile(CENTER_ROW, col)))
				break;
			
			ttdCopy[CENTER_ROW][col] = true;
			numberOfMatches += 1;
		}
		
		// Search right
		for (int col = CENTER_COL + 1; col < gameMesh.getNumColumns(); col++) {
			if (gameMesh.isEmpty(CENTER_ROW, col) || !matches(centerTile, gameMesh.getTile(CENTER_ROW, col)))
				break;
			
			ttdCopy[CENTER_ROW][col] = true;
			numberOfMatches += 1;
		}
		
		// 3rd: If we've reached the minimum number of matches, return the modified ttd. Else, return
		//      the original.
		if (numberOfMatches >= minMatching)
			return ttdCopy;
		else
			return tilesToDelete;
	}
	
	/**
	 * @param tileA A tile.
	 * @param tileB A tile.
	 * @return True if (according to the logic of this game) the given
	 * tiles match. False if otherwise.
	 */
	private static boolean matches(Tile tileA, Tile tileB) {
		Tile.TileType tileAType = tileA.getTileType();
		Tile.TileType tileBType = tileB.getTileType();
		
		boolean redMatch = ((tileAType == TileType.RED_PILL || tileAType == TileType.RED_VIRUS) && 
				            (tileBType == TileType.RED_PILL || tileBType == TileType.RED_VIRUS) );
		
		boolean blueMatch = ((tileAType == TileType.BLUE_PILL || tileAType == TileType.BLUE_VIRUS) && 
	                         (tileBType == TileType.BLUE_PILL || tileBType == TileType.BLUE_VIRUS) );
		
		boolean yellowMatch = ((tileAType == TileType.YELLOW_PILL || tileAType == TileType.YELLOW_VIRUS) && 
	                           (tileBType == TileType.YELLOW_PILL || tileBType == TileType.YELLOW_VIRUS) );
		
		return (redMatch || blueMatch || yellowMatch);
	}
	
	
	public enum Difficulty {
		EASY, MEDIUM, HARD
	}
	
	/**
	 * Adds the initial viruses to the provided game mesh. How many
	 * viruses spawn is determined by the provided difficulty level.
	 * @param gameMesh The game mesh.
	 * @param difficulty The difficulty level.
	 * @param tileLength The length of a tile (in pixels).
	 */
	public static void spawnViruses(GameMesh gameMesh, Difficulty difficulty, int tileLength) {
		int probability = 0;
		if (difficulty == Difficulty.EASY) 
			probability = 5;
		else if (difficulty == Difficulty.MEDIUM) {
			probability = 10;
		}
		else
			probability = 20;
		
		Random rand = new Random();
		final int HIGHEST_ROW = (int)gameMesh.getNumRows()/2;
		
		for (int row = HIGHEST_ROW; row < gameMesh.getNumRows(); row++) { // So the user isn't blocked, start at row 3
			for (int col = 0; col < gameMesh.getNumColumns(); col++) {
				int randInt = rand.nextInt(100);
				
				if (randInt <= probability)
					gameMesh.addTile(ShapeGenerator.createRandomVirus(tileLength, row, col));
			}
		}
	}
}
