package application;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * Creates random pills and viruses. Pills are automatically
 * positioned at the top of the screen, centered horizontally
 * along the game screen's width. Viruses are positioned on the
 * game mesh at the provided row and column.
 * @author Andrew Leon
 *
 */
public class ShapeGenerator {
	
	/**
	 * The random number generator.
	 */
	private static final Random RANDOM = new Random();
	
	/**
	 * Create and return a virus with the given length and with
	 * the given row and column position.
	 * @param tileLength The length of a tile (in pixels). Viruses are
	 * single tiles.
	 * @param row The row on the game mesh this virus resides on.
	 * @param column The column on the game mesh this virus resides on.
	 * @return A virus tile with the given tile length and (row, column)
	 * game mesh position.
	 */
	public static Tile createRandomVirus(int tileLength, int row, int column) {
		int randInt = RANDOM.nextInt(3);
		
		Tile.TileType tileType = randIntToVirusType(randInt);
		ImagePattern tileImage = getVirusImage(tileType);
		
		return new Tile(tileImage, tileType, Tile.Direction.NONE, tileLength, row, column);
	}
	
	/**
	 * @param tileType The virus type.
	 * @return The correct virus image for the provided virus type.
	 */
	private static ImagePattern getVirusImage(Tile.TileType tileType) {
		if (tileType == Tile.TileType.BLUE_VIRUS)
			return Resources.BLUE_VIRUS_IMG;
		else if (tileType == Tile.TileType.RED_VIRUS)
			return Resources.RED_VIRUS_IMG;
		else
			return Resources.YELLOW_VIRUS_IMG;
	}
	
	/**
	 * @param randInt A random integer between 0 and 2 (inclusive).
	 * @return The corresponding virus type.
	 */
	private static Tile.TileType randIntToVirusType(int randInt) {
		if (randInt == 0)
			return Tile.TileType.RED_VIRUS;
		else if (randInt == 1)
			return Tile.TileType.BLUE_VIRUS;
		else
			return Tile.TileType.YELLOW_VIRUS;
	}
	
	
	/**
	 * Create a new pill (made of 2 tiles).
	 * 
	 * @param tileLength Length of a tile (in pixels).
	 * @param screenWidth Width of the game portion of the screen (in # of tiles).
	 * @return A new Pill placed at the top of the game screen and halfway
	 * along the game screen's width.
	 */
	public static Pill createRandomPill(int tileLength, int screenWidth) {
		
		final int CENTER_COLUMN = (int)screenWidth/2;
		
		int randIntA = RANDOM.nextInt(3);
		int randIntB = RANDOM.nextInt(3);
		
		Tile.TileType tileAType = randIntToPillType(randIntA);
		Tile.TileType tileBType = randIntToPillType(randIntB);
		
		ImagePattern tileAImage = getPillImage(tileAType, true);
		ImagePattern tileBImage = getPillImage(tileBType, false);
		
		int tileARow = 0;
		int tileBRow = 0;
		int tileACol = CENTER_COLUMN - 1;
		int tileBCol = CENTER_COLUMN;
		
		Tile tileA = new Tile(tileAImage, tileAType, Tile.Direction.RIGHT, tileLength, tileARow, tileACol);
		Tile tileB = new Tile(tileBImage, tileBType, Tile.Direction.LEFT, tileLength, tileBRow, tileBCol);
		
		return new Pill(tileA, tileB);
	}
	
	/**
	 * @param tileType The pill tile type.
	 * @param isLeftTile True if this is the leftmost tile of the pill;
	 * False if it's the rightmost tile.
	 * @return The image pattern for this pill tile.
	 */
	private static ImagePattern getPillImage(Tile.TileType tileType, boolean isLeftTile) {
		if (isLeftTile) {
			if (tileType == Tile.TileType.BLUE_PILL)
				return Resources.BLUE_PILL_LEFT_IMG;
			else if (tileType == Tile.TileType.RED_PILL)
				return Resources.RED_PILL_LEFT_IMG;
			else
				return Resources.YELLOW_PILL_LEFT_IMG;
		}
		
		else {
			if (tileType == Tile.TileType.BLUE_PILL)
				return Resources.BLUE_PILL_RIGHT_IMG;
			else if (tileType == Tile.TileType.RED_PILL)
				return Resources.RED_PILL_RIGHT_IMG;
			else
				return Resources.YELLOW_PILL_RIGHT_IMG;
		}
	}

	/**
	 * 
	 * @param randInt A random integer from 0 to 2 (inclusive).
	 * @return The corresponding pill tile type.
	 */
	private static Tile.TileType randIntToPillType(int randInt) {
		if (randInt == 0)
			return Tile.TileType.RED_PILL;
		else if (randInt == 1)
			return Tile.TileType.BLUE_PILL;
		else
			return Tile.TileType.YELLOW_PILL;
	}	
}









