package application;

import java.util.ArrayList;

import javafx.scene.shape.Rectangle;

public class Controller {
	/**
	 * Attempts to move the given Tetris block one space to the right.
	 * If the Tetris block can't move there, it doesn't.
	 * @param tetrisBlock The tetris block.
	 * @param gameMesh The game mesh that keeps track of which tiles are &
	 * aren't occupied.
	 * @param tileLength The length of a tile (in pixels).
	 */
	public static void moveRight(TetrisBlock tetrisBlock, GameMesh gameMesh, int tileLength) {
		int SHIFT = 1;
		if (canMoveHorizontal(tetrisBlock, gameMesh, tileLength, SHIFT)) {
			removeTetrisBlock(tetrisBlock, gameMesh, tileLength);
			tetrisBlock.shiftHorizontal(tileLength);
			addTetrisBlock(tetrisBlock, gameMesh, tileLength);
		}
	}
	
	/**
	 * Attempts to move the given Tetris block one space to the left.
	 * If the Tetris block can't move there, it doesn't. Updates the Tetris
	 * block & game mesh.
	 * @param tetrisBlock The tetris block.
	 * @param gameMesh The game mesh that keeps track of which tiles are &
	 * aren't occupied.
	 * @param tileLength The length of a tile (in pixels).
	 */
	public static void moveLeft(TetrisBlock tetrisBlock, GameMesh gameMesh, int tileLength) {
		int SHIFT = -1;
		if (canMoveHorizontal(tetrisBlock, gameMesh, tileLength, SHIFT)) {
			removeTetrisBlock(tetrisBlock, gameMesh, tileLength);
			tetrisBlock.shiftHorizontal(-1 * tileLength);
			addTetrisBlock(tetrisBlock, gameMesh, tileLength);
		}
	}
	
	public static void moveDown(TetrisBlock tetrisBlock, GameMesh gameMesh, int tileLength) {
		int SHIFT = 1;
		if (canMoveVertical(tetrisBlock, gameMesh, tileLength, SHIFT)) {
			removeTetrisBlock(tetrisBlock, gameMesh, tileLength);
			tetrisBlock.shiftVertical(tileLength);
			addTetrisBlock(tetrisBlock, gameMesh, tileLength);
		}
	}
	
	
	private static void addTetrisBlock(TetrisBlock tetrisBlock, GameMesh gameMesh, int tileLength) {
		TetrisBlock.TetrisBlockCells cells = tetrisBlock.getCells(tileLength);
		TetrisBlock.Cell tileA = cells.tileA;
		TetrisBlock.Cell tileB = cells.tileB;
		TetrisBlock.Cell tileC = cells.tileC;
		TetrisBlock.Cell tileD = cells.tileD;
		
		gameMesh.addTile(tetrisBlock.getTileA(), tileA.row, tileA.column);
		gameMesh.addTile(tetrisBlock.getTileB(), tileB.row, tileB.column);
		gameMesh.addTile(tetrisBlock.getTileC(), tileC.row, tileC.column);
		gameMesh.addTile(tetrisBlock.getTileD(), tileD.row, tileD.column);
	}
	
	
	private static void removeTetrisBlock(TetrisBlock tetrisBlock, GameMesh gameMesh, int tileLength) {
		TetrisBlock.TetrisBlockCells cells = tetrisBlock.getCells(tileLength);
		TetrisBlock.Cell tileA = cells.tileA;
		TetrisBlock.Cell tileB = cells.tileB;
		TetrisBlock.Cell tileC = cells.tileC;
		TetrisBlock.Cell tileD = cells.tileD;
		
		gameMesh.removeTile(tileA.row, tileA.column);
		gameMesh.removeTile(tileB.row, tileB.column);
		gameMesh.removeTile(tileC.row, tileC.column);
		gameMesh.removeTile(tileD.row, tileD.column);
	}
	
	
	
	/**
	 * @param tetrisBlock The tetris block.
	 * @param gameMesh The game mesh that tracks which cells are occupied or not.
	 * @param tileLength The length of a tile (in pixels).
	 * @param shift The magnitude denotes the number of cells the Tetris block moves.
	 * The sign denotes the direction (negative = left; positive = right). 
	 * @return True if the given Tetris block can move the given number & direction
	 * of cells horizontally; False if otherwise.
	 */
	private static boolean canMoveHorizontal(TetrisBlock tetrisBlock, GameMesh gameMesh, int tileLength, int shift) {
		
		// 1st: Get the cells that the Tetris block CURRENTLY occupies.
		TetrisBlock.TetrisBlockCells cells = tetrisBlock.getCells(tileLength);
		TetrisBlock.Cell tileA = cells.tileA;
		TetrisBlock.Cell tileB = cells.tileB;
		TetrisBlock.Cell tileC = cells.tileC;
		TetrisBlock.Cell tileD = cells.tileD;
		
		// 2nd: Check that the cells the Tetris block WILL occupy are in bounds & unoccupied.
		
		// Temporarily remove the Tetris block
		removeTetrisBlock(tetrisBlock, gameMesh, tileLength);
		try {
			return ( gameMesh.isEmpty(tileA.row, tileA.column + shift) &&
					 gameMesh.isEmpty(tileB.row, tileB.column + shift) &&
					 gameMesh.isEmpty(tileC.row, tileC.column + shift) &&
					 gameMesh.isEmpty(tileD.row, tileD.column + shift));
		}
		
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		
		finally {
			addTetrisBlock(tetrisBlock, gameMesh, tileLength);
		}
		
	}
	
	private static boolean canMoveVertical(TetrisBlock tetrisBlock, GameMesh gameMesh, int tileLength, int shift) {
		// 1st: Get the cells that the Tetris block CURRENTLY occupies.
		TetrisBlock.TetrisBlockCells cells = tetrisBlock.getCells(tileLength);
		TetrisBlock.Cell tileA = cells.tileA;
		TetrisBlock.Cell tileB = cells.tileB;
		TetrisBlock.Cell tileC = cells.tileC;
		TetrisBlock.Cell tileD = cells.tileD;
		
		// 2nd: Check that the cells the Tetris block WILL occupy are in bounds & unoccupied.
		
		// Temporarily remove the Tetris block
		removeTetrisBlock(tetrisBlock, gameMesh, tileLength);
		try {			
			return ( gameMesh.isEmpty(tileA.row + shift, tileA.column) &&
					 gameMesh.isEmpty(tileB.row + shift, tileB.column) &&
					 gameMesh.isEmpty(tileC.row + shift, tileC.column) &&
					 gameMesh.isEmpty(tileD.row + shift, tileD.column));
		}
		
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		
		finally {
			addTetrisBlock(tetrisBlock, gameMesh, tileLength);
		}
	}
}
