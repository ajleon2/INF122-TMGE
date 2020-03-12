package application;

import java.util.Random;

import javafx.scene.shape.Rectangle;

public class TetrisBlockGenerator {
	
	private static final Random RANDOM = new Random();
	
	/**
	 * Create a new TetrisBlock (made of 4 rectangles).
	 * 
	 * @param tileLength Length of a tile (in pixels).
	 * @param screenWidth Width of the game portion of the screen (in pixels).
	 * @return A new TetrisBlock placed at the top of the game screen and halfway
	 * along the game screen's width.
	 */
	public static TetrisBlock createTetrisBlock(int tileLength, int screenWidth) {
		
		int randInt = RANDOM.nextInt(100);
		
		// Create tiles w/ identical dimensions
		Rectangle tileA = new Rectangle(tileLength, tileLength);
		Rectangle tileB = new Rectangle(tileLength, tileLength );
		Rectangle tileC = new Rectangle(tileLength, tileLength);
		Rectangle tileD = new Rectangle(tileLength, tileLength);
		
		TetrisBlock.BlockType blockType;
		
		if (randInt < 15) { // J block
			tileA.setX(screenWidth / 2 - tileLength);
			tileB.setX(screenWidth / 2 - tileLength);
			tileC.setX(screenWidth / 2);
			tileD.setX(screenWidth / 2 + tileLength);
			
			tileB.setY(tileLength);
			tileC.setY(tileLength);
			tileD.setY(tileLength);
			blockType = TetrisBlock.BlockType.J;
		}
		
		else if (randInt < 30) { // L block
			tileA.setX(screenWidth / 2 + tileLength);
			tileB.setX(screenWidth / 2 - tileLength);
			tileC.setX(screenWidth / 2);
			
			tileB.setY(tileLength);
			tileC.setY(tileLength);
			tileD.setX(screenWidth / 2 + tileLength);
			tileD.setY(tileLength);
			blockType = TetrisBlock.BlockType.L;
		}
		
		else if (randInt < 45) { // O block
			tileA.setX(screenWidth / 2 - tileLength);
			tileB.setX(screenWidth / 2);
			tileC.setX(screenWidth / 2 - tileLength);
			tileD.setX(screenWidth / 2);
			
			tileC.setY(tileLength);
			tileD.setY(tileLength);
			blockType = TetrisBlock.BlockType.O;
		}
		
		else if (randInt < 60) { // S block
			tileA.setX(screenWidth / 2 + tileLength);
			tileB.setX(screenWidth / 2);
			tileC.setX(screenWidth / 2);
			tileD.setX(screenWidth / 2 - tileLength);
			
			tileC.setY(tileLength);
			tileD.setY(tileLength);
			blockType = TetrisBlock.BlockType.S;
		}
		
		else if (randInt < 70) { // T block
			tileA.setX(screenWidth / 2 - tileLength);
			tileB.setX(screenWidth / 2);
			tileC.setX(screenWidth / 2);
			tileD.setX(screenWidth / 2 + tileLength);
			
			tileC.setY(tileLength);
			blockType = TetrisBlock.BlockType.T;
		}
		
		else if (randInt < 90) { // Z block
			tileA.setX(screenWidth / 2 + tileLength);
			tileB.setX(screenWidth / 2);
			tileC.setX(screenWidth / 2 + tileLength);
			tileD.setX(screenWidth / 2 + tileLength + tileLength);
			
			tileC.setY(tileLength);
			tileD.setY(tileLength);
			blockType = TetrisBlock.BlockType.Z;
		}
		
		else {
			tileA.setX(screenWidth / 2 - tileLength - tileLength);
			tileB.setX(screenWidth / 2 - tileLength);
			tileC.setX(screenWidth / 2);
			tileD.setX(screenWidth / 2 + tileLength);
			blockType = TetrisBlock.BlockType.I;
		}
		
		return new TetrisBlock(tileA, tileB, tileC, tileD, blockType);
	}
}
