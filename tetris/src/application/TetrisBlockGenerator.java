package application;

import java.util.Random;

import javafx.scene.shape.Rectangle;

public class TetrisBlockGenerator {
	
	private static final Random RANDOM = new Random();
	
	/**
	 * Create a new TetrisBlock (made of 4 rectangles).
	 * 
	 * @param tileLength Length of a tile (in pixels).
	 * @param screenWidth Width of the game portion of the screen (in # of tiles).
	 * @return A new TetrisBlock placed at the top of the game screen and halfway
	 * along the game screen's width.
	 */
	public static TetrisBlock createTetrisBlock(int tileLength, int screenWidth) {
		
		final int CENTER_COLUMN = (int)screenWidth/2;
		
		Tile tileA;
		Tile tileB;
		Tile tileC;
		Tile tileD;
		
		TetrisBlock.BlockType blockType;
		
		
		int randInt = RANDOM.nextInt(100);
		
		if (randInt < 15) { // J block
			tileA = new Tile(tileLength, 0, CENTER_COLUMN - 1);
			tileB = new Tile(tileLength, 1, CENTER_COLUMN - 1);
			tileC = new Tile(tileLength, 1, CENTER_COLUMN);
			tileD = new Tile(tileLength, 1, CENTER_COLUMN + 1);
			blockType = TetrisBlock.BlockType.J;
		}
		
		else if (randInt < 30) { // L block
			tileA = new Tile(tileLength, 1, CENTER_COLUMN - 1);
			tileB = new Tile(tileLength, 1, CENTER_COLUMN);
			tileC = new Tile(tileLength, 1, CENTER_COLUMN + 1);
			tileD = new Tile(tileLength, 0, CENTER_COLUMN + 1);
			blockType = TetrisBlock.BlockType.L;
		}
		
		else if (randInt < 45) { // O block
			tileA = new Tile(tileLength, 0, CENTER_COLUMN);
			tileB = new Tile(tileLength, 0, CENTER_COLUMN + 1);
			tileC = new Tile(tileLength, 1, CENTER_COLUMN);
			tileD = new Tile(tileLength, 1, CENTER_COLUMN + 1);
			blockType = TetrisBlock.BlockType.O;
		}
		
		else if (randInt < 60) { // S block
			tileA = new Tile(tileLength, 1, CENTER_COLUMN - 1);
			tileB = new Tile(tileLength, 1, CENTER_COLUMN);
			tileC = new Tile(tileLength, 0, CENTER_COLUMN);
			tileD = new Tile(tileLength, 0, CENTER_COLUMN + 1);
			blockType = TetrisBlock.BlockType.S;
		}
		
		else if (randInt < 75) { // T block
			tileA = new Tile(tileLength, 0, CENTER_COLUMN - 1);
			tileB = new Tile(tileLength, 0, CENTER_COLUMN);
			tileC = new Tile(tileLength, 0, CENTER_COLUMN + 1);
			tileD = new Tile(tileLength, 1, CENTER_COLUMN);
			blockType = TetrisBlock.BlockType.T;
		}
		
		else if (randInt < 90) { // Z block
			tileA = new Tile(tileLength, 0, CENTER_COLUMN - 1);
			tileB = new Tile(tileLength, 0, CENTER_COLUMN);
			tileC = new Tile(tileLength, 1, CENTER_COLUMN);
			tileD = new Tile(tileLength, 1, CENTER_COLUMN + 1);
			blockType = TetrisBlock.BlockType.Z;
		}
		
		else {
			tileA = new Tile(tileLength, 0, CENTER_COLUMN - 1);
			tileB = new Tile(tileLength, 0, CENTER_COLUMN);
			tileC = new Tile(tileLength, 0, CENTER_COLUMN + 1);
			tileD = new Tile(tileLength, 0, CENTER_COLUMN + 2);
			blockType = TetrisBlock.BlockType.I;
		}
		
		return new TetrisBlock(tileA, tileB, tileC, tileD, blockType);
	}
}
