package application;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application
{	
	public static final int mv = 25;
	public static final int sz = 25;
	public static int width = sz*12;
	public static int height = sz*24;
	
	public static int[][] mesh = new int[12][24];
	private static Pane group = new Pane();
	private static Scene s = new Scene(group, width+150, height);
	private static int top;
	private static Tetromino obj;
	private static Tetromino nextObj = Controller.makeTetro();
	private static boolean gameOver = false;
	
	@Override
	public void start(Stage stage) throws Exception
	{
		for (int[] a: mesh)
			Arrays.fill(a, 0);
		
		Line line = new Line(width, 0, width, height);
		Text lvl = new Text("Lines: ");
		lvl.setY(100);
		lvl.setX(width + 5);
		group.getChildren().addAll(line, lvl);
		
		Tetromino a = nextObj;
		group.getChildren().addAll(a.a, a.b, a.c, a.d);
		obj = a;
		nextObj = Controller.makeTetro();
		
		stage.setScene(s);
		stage.setTitle("TETRIS");
		stage.show();
		
		Timer fall = new Timer();
		TimerTask task = new TimerTask()
		{
			public void run()
			{
				Platform.runLater(new Runnable()
				{
					public void run()
					{
						if (obj.a.getY() == 0 
								|| obj.b.getY() == 0 
								|| obj.c.getY() == 0
								|| obj.d.getY() == 0)
							top++;
						else
							top = 0;
						
						if(!gameOver)
						{
							mvDown(obj);
							//
						}
					}
				});
			}
		};
		fall.schedule(task, 0, 300);
	}
	
	private void mvDown(Rectangle rect)
	{
		if(rect.getX() + mv < height)
			rect.setY(rect.getY() + mv);
	}
	
	private void mvDown(Tetromino t)
	{
		if (t.a.getY() + mv < height && t.b.getY() + mv < height && t.c.getY() + mv < height
				&& t.d.getY() + mv < height) 
		{
			int mva = mesh[(int) t.a.getX() / sz][((int) t.a.getY() / sz) + 1];
			int mvb = mesh[(int) t.b.getX() / sz][((int) t.b.getY() / sz) + 1];
			int mvc = mesh[(int) t.c.getX() / sz][((int) t.c.getY() / sz) + 1];
			int mvd = mesh[(int) t.d.getX() / sz][((int) t.d.getY() / sz) + 1];
			if (mva == 0 && mva == mvb && mvb == mvc && mvc == mvd) 
			{
				t.a.setY(t.a.getY() + mv);
				t.b.setY(t.b.getY() + mv);
				t.c.setY(t.c.getY() + mv);
				t.d.setY(t.d.getY() + mv);
			}
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}


