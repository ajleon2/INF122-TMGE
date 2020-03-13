package application;

import java.util.ArrayList;

import javafx.scene.shape.Rectangle;

public class Controller {
	/**
	 * Attempts to move the given Tetris block one tile to the right.
	 * If the Tetris block can't move there, it doesn't.
	 * @param tetrisBlock The Tetris block.
	 * @param gameMesh The game mesh that keeps track of which tiles are and
	 * aren't occupied.
	 */
	public static void moveRight(TetrisBlock tetrisBlock, GameMesh gameMesh) {
		int SHIFT_AMOUNT = 1; // 1 tile right
		if (canMoveHorizontal(tetrisBlock, gameMesh, SHIFT_AMOUNT)) {
			removeTetrisBlock(tetrisBlock, gameMesh);
			tetrisBlock.shiftHorizontal(SHIFT_AMOUNT);
			addTetrisBlock(tetrisBlock, gameMesh);
		}
	}
	
	/**
	 * Attempts to move the given Tetris block one tile to the left.
	 * If the Tetris block can't move there, it doesn't. Updates the Tetris
	 * block & game mesh.
	 * @param tetrisBlock The Tetris block.
	 * @param gameMesh The game mesh that keeps track of which tiles are and
	 * aren't occupied.
	 */
	public static void moveLeft(TetrisBlock tetrisBlock, GameMesh gameMesh) {
		int SHIFT_AMOUNT = -1; // 1 tile left
		if (canMoveHorizontal(tetrisBlock, gameMesh, SHIFT_AMOUNT)) {
			removeTetrisBlock(tetrisBlock, gameMesh);
			tetrisBlock.shiftHorizontal(SHIFT_AMOUNT);
			addTetrisBlock(tetrisBlock, gameMesh);
		}
	}
	
	/**
	 * Attempts to move the given Tetris block one tile down.
	 * If the Tetris block can't move there, it doesn't. Updates the Tetris
	 * block & game mesh.
	 * @param tetrisBlock The Tetris block.
	 * @param gameMesh The game mesh that keeps track of which tiles are and
	 * aren't occupied.
	 */
	public static void moveDown(TetrisBlock tetrisBlock, GameMesh gameMesh) {
		int SHIFT_AMOUNT = 1; // 1 tile down
		if (canMoveVertical(tetrisBlock, gameMesh, SHIFT_AMOUNT)) {
			removeTetrisBlock(tetrisBlock, gameMesh);
			tetrisBlock.shiftVertical(SHIFT_AMOUNT);
			addTetrisBlock(tetrisBlock, gameMesh);
		}
	}
	
	/**
	 * Add the given Tetris block to the game mesh. Assumes there is space
	 * on the game mesh to place said Tetris block.
	 * @param tetrisBlock The Tetris block to add to the game mesh.
	 * @param gameMesh The game mesh that keeps track of which tiles are and aren't
	 * occupied.
	 */
	public static void addTetrisBlock(TetrisBlock tetrisBlock, GameMesh gameMesh) {
		gameMesh.addTile(tetrisBlock.getTileA());
		gameMesh.addTile(tetrisBlock.getTileB());
		gameMesh.addTile(tetrisBlock.getTileC());
		gameMesh.addTile(tetrisBlock.getTileD());
	}
	
	/**
	 * Remove the given Tetris block from the game mesh. Assumes the Tetris block is
	 * on the game mesh.
	 * @param tetrisBlock The Tetris block to remove from the game mesh.
	 * @param gameMesh The game mesh that keeps track of which tiles are and aren't
	 * occupied.
	 */
	public static void removeTetrisBlock(TetrisBlock tetrisBlock, GameMesh gameMesh) {
		Tile tileA = tetrisBlock.getTileA();
		Tile tileB = tetrisBlock.getTileB();
		Tile tileC = tetrisBlock.getTileC();
		Tile tileD = tetrisBlock.getTileD();
		
		gameMesh.removeTile(tileA.getRow(), tileA.getColumn());
		gameMesh.removeTile(tileB.getRow(), tileB.getColumn());
		gameMesh.removeTile(tileC.getRow(), tileC.getColumn());
		gameMesh.removeTile(tileD.getRow(), tileD.getColumn());
	}
	
	
	
	/**
	 * @param tetrisBlock The tetris block.
	 * @param gameMesh The game mesh that tracks which cells are occupied or not.
	 * @param shift The magnitude denotes the number of tiles the Tetris block moves.
	 * The sign denotes the direction (negative = left; positive = right). 
	 * @return True if the given Tetris block can move the given number & direction
	 * of cells horizontally; False if otherwise.
	 */
	private static boolean canMoveHorizontal(TetrisBlock tetrisBlock, GameMesh gameMesh, int shift) {		
		// 1st: Temporarily remove the Tetris block from the game mesh.
		removeTetrisBlock(tetrisBlock, gameMesh);
		
		// 2nd: Check that the cells the Tetris block will move to are in bounds & unoccupied.
		try {
			Tile tileA = tetrisBlock.getTileA();
			Tile tileB = tetrisBlock.getTileB();
			Tile tileC = tetrisBlock.getTileC();
			Tile tileD = tetrisBlock.getTileD();
			
			return ( gameMesh.isEmpty(tileA.getRow(), tileA.getColumn() + shift) &&
					 gameMesh.isEmpty(tileB.getRow(), tileB.getColumn() + shift) &&
					 gameMesh.isEmpty(tileC.getRow(), tileC.getColumn() + shift) &&
					 gameMesh.isEmpty(tileD.getRow(), tileD.getColumn() + shift));
		}
		
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		
		// Re-add the Tetris block to the game mesh
		finally {
			addTetrisBlock(tetrisBlock, gameMesh);
		}
	}
	
	/**
	 * @param tetrisBlock The tetris block.
	 * @param gameMesh The game mesh that tracks which cells are occupied or not.
	 * @param shift The magnitude denotes the number of tiles the Tetris block moves.
	 * The sign denotes the direction (negative = up; positive = down). 
	 * @return True if the given Tetris block can move the given number & direction
	 * of cells horizontally; False if otherwise.
	 */
	private static boolean canMoveVertical(TetrisBlock tetrisBlock, GameMesh gameMesh, int shift) {
		// 1st: Temporarily remove the Tetris block from the game mesh.
		removeTetrisBlock(tetrisBlock, gameMesh);
		
		// 2nd: Check that the cells the Tetris block will move to are in bounds & unoccupied.
		try {
			Tile tileA = tetrisBlock.getTileA();
			Tile tileB = tetrisBlock.getTileB();
			Tile tileC = tetrisBlock.getTileC();
			Tile tileD = tetrisBlock.getTileD();
			
			return ( gameMesh.isEmpty(tileA.getRow() + shift, tileA.getColumn()) &&
					 gameMesh.isEmpty(tileB.getRow() + shift, tileB.getColumn()) &&
					 gameMesh.isEmpty(tileC.getRow() + shift, tileC.getColumn()) &&
					 gameMesh.isEmpty(tileD.getRow() + shift, tileD.getColumn()));
		}
		
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		
		// Re-add the Tetris block to the game mesh
		finally {
			addTetrisBlock(tetrisBlock, gameMesh);
		}
	}
}
