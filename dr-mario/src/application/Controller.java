package application;

import javafx.scene.paint.ImagePattern;

/**
 * Provides static methods meant to move, flip, add, and remove a Pill
 * on a game mesh.
 * @author Andrew Leon
 *
 */
public class Controller {
	/**
	 * Attempts to move the given Pill one tile to the right.
	 * If the Pill can't move there, it doesn't. Updates the pill and
	 * game mesh.
	 * @param pill The Pill.
	 * @param gameMesh The game mesh that keeps track of which tiles are and
	 * aren't occupied.
	 */
	public static void moveRight(Pill pill, GameMesh gameMesh) {
		int SHIFT_AMOUNT = 1; // 1 tile right
		if (canMoveHorizontal(pill, gameMesh, SHIFT_AMOUNT)) {
			removePill(pill, gameMesh);
			pill.shiftHorizontal(SHIFT_AMOUNT);
			addPill(pill, gameMesh);
		}
	}
	
	/**
	 * Attempts to move the given Pill one tile to the left.
	 * If the Pill can't move there, it doesn't. Updates the Pill and game mesh.
	 * @param pill The Pill.
	 * @param gameMesh The game mesh that keeps track of which tiles are and
	 * aren't occupied.
	 */
	public static void moveLeft(Pill pill, GameMesh gameMesh) {
		int SHIFT_AMOUNT = -1; // 1 tile left
		if (canMoveHorizontal(pill, gameMesh, SHIFT_AMOUNT)) {
			removePill(pill, gameMesh);
			pill.shiftHorizontal(SHIFT_AMOUNT);
			addPill(pill, gameMesh);
		}
	}
	
	/**
	 * Attempts to move the given Pill one tile down.
	 * If the Pill can't move there, it doesn't. Updates the Pill and game mesh.
	 * @param pill The Pill.
	 * @param gameMesh The game mesh that keeps track of which tiles are and
	 * aren't occupied.
	 */
	public static void moveDown(Pill pill, GameMesh gameMesh) {
		int SHIFT_AMOUNT = 1; // 1 tile down
		if (canMoveVertical(pill, gameMesh, SHIFT_AMOUNT)) {
			removePill(pill, gameMesh);
			pill.shiftVertical(SHIFT_AMOUNT);
			addPill(pill, gameMesh);
		}
	}
	
	/**
	 * Attempts to flip the given Pill to its next orientation. If the
	 * Pill will collide with a tile or the wall, it doesn't flip. Updates
	 * the Pill and game mesh.
	 * @param pill The Pill.
	 * @param gameMesh The game mesh that keeps track of which tiles are and
	 * aren't occupied.
	 */
	public static void flip(Pill pill, GameMesh gameMesh) {
		if (canFlip(pill, gameMesh)) {
			removePill(pill, gameMesh);
			pill.flip(false);
			flipPillImages(pill);
			addPill(pill, gameMesh);
		}
	}
	
	/**
	 * Sets each pill half to their correct image fill
	 * (ex: If a blue pill is oriented vertically, the top half pill
	 *      and bottom half pill will receive their correct image
	 *      fills).
	 * @param pill The pill.
	 */
	private static void flipPillImages(Pill pill) {
		Tile tileA = pill.getTileA();
		Tile tileB = pill.getTileB();
		
		ImagePattern tileANewImage = getTileImage(tileA);
		ImagePattern tileBNewImage = getTileImage(tileB);
		
		tileA.setImageFill(tileANewImage);
		tileB.setImageFill(tileBNewImage);
	}
	
	/**
	 * Gets the correct image fill for the provided tile.
	 * @param tile The tile.
	 * @return The correct image fill for the provided tile.
	 */
	private static ImagePattern getTileImage(Tile tile) {
		Tile.Direction companionDirection = tile.getCompanionDirection();
		Tile.TileType tileType = tile.getTileType();
		
		if (companionDirection == Tile.Direction.UP) { // Down image
			if (tileType == Tile.TileType.BLUE_PILL)
				return Resources.BLUE_PILL_DOWN_IMG;
			else if (tileType == Tile.TileType.RED_PILL)
				return Resources.RED_PILL_DOWN_IMG;
			else
				return Resources.YELLOW_PILL_DOWN_IMG;
		}
		
		else if (companionDirection == Tile.Direction.RIGHT) { // Left image
			if (tileType == Tile.TileType.BLUE_PILL)
				return Resources.BLUE_PILL_LEFT_IMG;
			else if (tileType == Tile.TileType.RED_PILL)
				return Resources.RED_PILL_LEFT_IMG;
			else
				return Resources.YELLOW_PILL_LEFT_IMG;
		}
		
		else if (companionDirection == Tile.Direction.DOWN) { // Up image
			if (tileType == Tile.TileType.BLUE_PILL)
				return Resources.BLUE_PILL_UP_IMG;
			else if (tileType == Tile.TileType.RED_PILL)
				return Resources.RED_PILL_UP_IMG;
			else
				return Resources.YELLOW_PILL_UP_IMG;
		}
		
		else { // Right image
			if (tileType == Tile.TileType.BLUE_PILL)
				return Resources.BLUE_PILL_RIGHT_IMG;
			else if (tileType == Tile.TileType.RED_PILL)
				return Resources.RED_PILL_RIGHT_IMG;
			else
				return Resources.YELLOW_PILL_RIGHT_IMG;
		}
	}
	
	
	/**
	 * Add the given Pill to the game mesh. Assumes there is space
	 * on the game mesh to place said Pill.
	 * @param pill The Pill to add to the game mesh.
	 * @param gameMesh The game mesh that keeps track of which tiles are and aren't
	 * occupied.
	 */
	public static void addPill(Pill pill, GameMesh gameMesh) {
		gameMesh.addTile(pill.getTileA());
		gameMesh.addTile(pill.getTileB());
	}
	
	/**
	 * Remove the given Pill from the game mesh. Assumes the Pill is
	 * on the game mesh.
	 * @param pill The Pill to remove from the game mesh.
	 * @param gameMesh The game mesh that keeps track of which tiles are and aren't
	 * occupied.
	 */
	public static void removePill(Pill pill, GameMesh gameMesh) {
		Tile tileA = pill.getTileA();
		Tile tileB = pill.getTileB();
		
		gameMesh.removeTile(tileA.getRow(), tileA.getColumn());
		gameMesh.removeTile(tileB.getRow(), tileB.getColumn());
	}
	
	
	/**
	 * @param pill The pill.
	 * @param gameMesh The game mesh that tracks which cells are occupied or not.
	 * @param shift The magnitude denotes the number of tiles the Pill moves.
	 * The sign denotes the direction (negative = left; positive = right). 
	 * @return True if the given Pill can move the given number & direction
	 * of tiles horizontally; False if otherwise.
	 */
	private static boolean canMoveHorizontal(Pill pill, GameMesh gameMesh, int shift) {			
		// 1st: Temporarily remove the Pill from the game mesh.
		removePill(pill, gameMesh);
		
		// 2nd: Move the block to get its new position
		pill.shiftHorizontal(shift); 
		
		// 3rd: Check that the cells the Pill will move to are in bounds & unoccupied.
		try {
			Tile tileA = pill.getTileA();
			Tile tileB = pill.getTileB();
			
			return ( gameMesh.isEmpty(tileA.getRow(), tileA.getColumn()) &&
					 gameMesh.isEmpty(tileB.getRow(), tileB.getColumn()) );
		}
		
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		
		// Re-add the Pill to the game mesh
		finally {
			pill.shiftHorizontal(-1 * shift); // Move the Pill back to its original position
			addPill(pill, gameMesh);
		}
	}
	
	/**
	 * @param pill The pill.
	 * @param gameMesh The game mesh that tracks which cells are occupied or not.
	 * @param shift The magnitude denotes the number of tiles the Pill moves.
	 * The sign denotes the direction (negative = up; positive = down). 
	 * @return True if the given Pill can move the given number & direction
	 * of tiles horizontally; False if otherwise.
	 */
	private static boolean canMoveVertical(Pill pill, GameMesh gameMesh, int shift) {		
		// 1st: Temporarily remove the Pill from the game mesh.
		removePill(pill, gameMesh);
		
		// 2nd: Move the block to get its new position
		pill.shiftVertical(shift);
		
		// 3rd: Check that the cells the Pill will move to are in bounds & unoccupied.
		try {
			Tile tileA = pill.getTileA();
			Tile tileB = pill.getTileB();
			
			return ( gameMesh.isEmpty(tileA.getRow(), tileA.getColumn()) &&
					 gameMesh.isEmpty(tileB.getRow(), tileB.getColumn()) );
		}
		
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		
		// Re-add the Pill to the game mesh
		finally {
			pill.shiftVertical(-1 * shift); // Move the Pill back to its original position
			addPill(pill, gameMesh);
		}
	}
	
	/**
	 * @param pill The Pill.
	 * @param gameMesh The game mesh that tracks which cells are occupied or not.
	 * @return True if the Pill can flip to its next orientation; False if otherwise.
	 */
	private static boolean canFlip(Pill pill, GameMesh gameMesh) {		
		// 1st: Temporarily remove the Pill from the game mesh.
		removePill(pill, gameMesh);
		
		// 2nd: Flip pill to its new position	
		pill.flip(false);
		
		// 3rd: Check that the cells the Pill will move to are in bounds & unoccupied.
		try {
			Tile tileA = pill.getTileA();
			Tile tileB = pill.getTileB();
			
			return ( gameMesh.isEmpty(tileA.getRow(), tileA.getColumn()) &&
					 gameMesh.isEmpty(tileB.getRow(), tileB.getColumn()) );
		}
		
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		
		// Re-add the Pill to the game mesh
		finally {
			pill.flip(true); // Reset the pill to its original orientation
			addPill(pill, gameMesh);
		}
	}
	
	/**
	 * Moves any "floating" pills down 1 tile. Returns True if any floating 
	 * pills moved down, False if otherwise. Updates the game mesh.
	 * @param gameMesh Manages the positions of tiles on the game mesh.
	 * @return True if any floating pills were moved down, False if otherwise.
	 */
	public static boolean moveFloatingPillsDown(GameMesh gameMesh) {
		boolean pillDidMove = false;
		
		// Start at the 2nd to the bottom row, then move up
		for (int row = gameMesh.getNumRows() - 2; row >= 0; row--) {
			for (int col = 0; col < gameMesh.getNumColumns(); col++) {
				if (!gameMesh.isEmpty(row, col)) {
					Tile tile = gameMesh.getTile(row, col);
					
					// If this tile is a pill, we check to see if it is "floating".
					if (tile.isPill() && gameMesh.isEmpty(row + 1, col)) {
						
						// If this tile has no companion, it can move down 1 tile.
						if (!tile.hasCompanion()) {
							gameMesh.removeTile(row, col);
							tile.setRow(row + 1);
							gameMesh.addTile(tile);
							pillDidMove = true;
						}
						
						// If this tile has a companion, we check if its companion is also
						// "floating". If it is, both this tile and its companion move down
						// 1 tile.
						else {
							int companionRow = tile.getCompanionRow();
							int companionCol = tile.getCompanionColumn();
							if (gameMesh.isEmpty(companionRow + 1, companionCol)) {
								Tile companionTile = gameMesh.getTile(companionRow, companionCol);
								gameMesh.removeTile(row, col);
								gameMesh.removeTile(companionRow, companionCol);
								tile.setRow(row + 1);
								companionTile.setRow(row + 1);
								gameMesh.addTile(tile);
								gameMesh.addTile(companionTile);
								pillDidMove = true;
							}
						}
					}
				}
			}
		}
		
		return pillDidMove;
	}
}
