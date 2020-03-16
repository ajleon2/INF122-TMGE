package application;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * Contains static resources used by the game. 
 * 
 * Currently, contains:
 *     - Pill images
 *     - Virus images
 * @author Andrew Leon
 *
 */
public class Resources {	
	public static final ImagePattern BLUE_PILL_UP_IMG = new ImagePattern(new Image("bluePillUp.png"));
	public static final ImagePattern BLUE_PILL_DOWN_IMG = new ImagePattern(new Image("bluePillDown.png"));
	public static final ImagePattern BLUE_PILL_LEFT_IMG = new ImagePattern(new Image("bluePillLeft.png"));
	public static final ImagePattern BLUE_PILL_RIGHT_IMG = new ImagePattern(new Image("bluePillRight.png"));
	
	public static final ImagePattern RED_PILL_UP_IMG = new ImagePattern(new Image("redPillUp.png"));
	public static final ImagePattern RED_PILL_DOWN_IMG = new ImagePattern(new Image("redPillDown.png"));
	public static final ImagePattern RED_PILL_LEFT_IMG = new ImagePattern(new Image("redPillLeft.png"));
	public static final ImagePattern RED_PILL_RIGHT_IMG = new ImagePattern(new Image("redPillRight.png"));
	
	public static final ImagePattern YELLOW_PILL_UP_IMG = new ImagePattern(new Image("yellowPillUp.png"));
	public static final ImagePattern YELLOW_PILL_DOWN_IMG = new ImagePattern(new Image("yellowPillDown.png"));
	public static final ImagePattern YELLOW_PILL_LEFT_IMG = new ImagePattern(new Image("yellowPillLeft.png"));
	public static final ImagePattern YELLOW_PILL_RIGHT_IMG = new ImagePattern(new Image("yellowPillRight.png"));
	
	public static final ImagePattern RED_VIRUS_IMG = new ImagePattern(new Image("redVirus.png"));
	public static final ImagePattern BLUE_VIRUS_IMG = new ImagePattern(new Image("blueVirus.png"));
	public static final ImagePattern YELLOW_VIRUS_IMG = new ImagePattern(new Image("yellowVirus.png"));
}
