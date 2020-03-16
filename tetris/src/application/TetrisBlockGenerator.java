package application;

import java.util.Random;

import application.TetrisBlock.BlockType;
import javafx.scene.paint.Color;

/**
 * Creates a random Tetris block positioned at its starting
 * point on the game mesh.
 * @author Andrew Leon
 *
 */
public class TetrisBlockGenerator {
	
	/**
	 * The random number generator.
	 */
	private static final Random RANDOM = new Random();
	
	/**
	 * Create a new TetrisBlock (made of 4 Tiles).
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
		Color blockColor;
		
		int randInt = RANDOM.nextInt(100);
		
		if (randInt < 15) { // J block
			blockType = TetrisBlock.BlockType.J;
			blockColor = blockTypeToColor(blockType);
			tileA = new Tile(blockColor, tileLength, 0, CENTER_COLUMN - 1);
			tileB = new Tile(blockColor, tileLength, 1, CENTER_COLUMN - 1);
			tileC = new Tile(blockColor, tileLength, 1, CENTER_COLUMN);
			tileD = new Tile(blockColor, tileLength, 1, CENTER_COLUMN + 1);
		}
		
		else if (randInt < 30) { // L block
			blockType = TetrisBlock.BlockType.L;
			blockColor = blockTypeToColor(blockType);
			tileA = new Tile(blockColor, tileLength, 1, CENTER_COLUMN - 1);
			tileB = new Tile(blockColor, tileLength, 1, CENTER_COLUMN);
			tileC = new Tile(blockColor, tileLength, 1, CENTER_COLUMN + 1);
			tileD = new Tile(blockColor, tileLength, 0, CENTER_COLUMN + 1);
		}
		
		else if (randInt < 45) { // O block
			blockType = TetrisBlock.BlockType.O;
			blockColor = blockTypeToColor(blockType);
			tileA = new Tile(blockColor, tileLength, 0, CENTER_COLUMN);
			tileB = new Tile(blockColor, tileLength, 0, CENTER_COLUMN + 1);
			tileC = new Tile(blockColor, tileLength, 1, CENTER_COLUMN);
			tileD = new Tile(blockColor, tileLength, 1, CENTER_COLUMN + 1);
		}
		
		else if (randInt < 60) { // S block
			blockType = TetrisBlock.BlockType.S;
			blockColor = blockTypeToColor(blockType);
			tileA = new Tile(blockColor, tileLength, 1, CENTER_COLUMN - 1);
			tileB = new Tile(blockColor, tileLength, 1, CENTER_COLUMN);
			tileC = new Tile(blockColor, tileLength, 0, CENTER_COLUMN);
			tileD = new Tile(blockColor, tileLength, 0, CENTER_COLUMN + 1);
		}
		
		else if (randInt < 75) { // T block
			blockType = TetrisBlock.BlockType.T;
			blockColor = blockTypeToColor(blockType);
			tileA = new Tile(blockColor, tileLength, 0, CENTER_COLUMN - 1);
			tileB = new Tile(blockColor, tileLength, 0, CENTER_COLUMN);
			tileC = new Tile(blockColor, tileLength, 0, CENTER_COLUMN + 1);
			tileD = new Tile(blockColor, tileLength, 1, CENTER_COLUMN);
		}
		
		else if (randInt < 90) { // Z block
			blockType = TetrisBlock.BlockType.Z;
			blockColor = blockTypeToColor(blockType);
			tileA = new Tile(blockColor, tileLength, 0, CENTER_COLUMN - 1);
			tileB = new Tile(blockColor, tileLength, 0, CENTER_COLUMN);
			tileC = new Tile(blockColor, tileLength, 1, CENTER_COLUMN);
			tileD = new Tile(blockColor, tileLength, 1, CENTER_COLUMN + 1);
		}
		
		else {
			blockType = TetrisBlock.BlockType.I;
			blockColor = blockTypeToColor(blockType);
			tileA = new Tile(blockColor, tileLength, 0, CENTER_COLUMN - 1);
			tileB = new Tile(blockColor, tileLength, 0, CENTER_COLUMN);
			tileC = new Tile(blockColor, tileLength, 0, CENTER_COLUMN + 1);
			tileD = new Tile(blockColor, tileLength, 0, CENTER_COLUMN + 2);
		}
		
		return new TetrisBlock(tileA, tileB, tileC, tileD, blockType);
	}
	
	
	/**
	 * @param blockType The block type.
	 * @return The block type's associated color.
	 */
	private static Color blockTypeToColor(BlockType blockType) {
		switch(blockType) {
		case J:
			return Color.SLATEGRAY;
		case L:
			return Color.DARKGOLDENROD;
		case O:
			return Color.INDIANRED;
		case S:
			return Color.FORESTGREEN;
		case T:
			return Color.CADETBLUE;
		case Z:
			return Color.HOTPINK;
		case I:
			return Color.SANDYBROWN;
		default:
			return Color.DARKGRAY;
		}
	}	
}
