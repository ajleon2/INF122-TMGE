package application;
import javafx.scene.shape.Rectangle;

public class Controller 
{
	public static final int move = Tetris.mv;
	public static final int size = Tetris.sz;
	public static int w = Tetris.width;
	public static int h = Tetris.height;
	public static int[][] m = Tetris.mesh;
	
	public static Tetromino makeTetro() 
	{
		int block = (int) (Math.random() * 100);
		String name;
		Rectangle a = new Rectangle(size-1, size-1), 
				b = new Rectangle(size-1, size-1), 
				c = new Rectangle(size-1, size-1),
				d = new Rectangle(size-1, size-1);
		if (block < 15) 
		{ 
			a.setX(w / 2 - size);
			b.setX(w / 2 - size);
			b.setY(size);
			c.setX(w / 2);
			c.setY(size);
			d.setX(w / 2 + size);
			d.setY(size);
			name = "j";
		} 
		else if (block < 30) 
		{ 
			a.setX(w / 2 + size);
			b.setX(w / 2 - size);
			b.setY(size);
			c.setX(w / 2);
			c.setY(size);
			d.setX(w / 2 + size);
			d.setY(size);
			name = "l";
		} 
		else if (block < 45) 
		{ 
			a.setX(w / 2 - size);
			b.setX(w / 2);
			c.setX(w / 2 - size);
			c.setY(size);
			d.setX(w / 2);
			d.setY(size);
			name = "o";
		} 
		else if (block < 60) 
		{ 
			a.setX(w / 2 + size);
			b.setX(w / 2);
			c.setX(w / 2);
			c.setY(size);
			d.setX(w / 2 - size);
			d.setY(size);
			name = "s";
		} 
		else if (block < 75) 
		{ 
			a.setX(w / 2 - size);
			b.setX(w / 2);
			c.setX(w / 2);
			c.setY(size);
			d.setX(w / 2 + size);
			name = "t";
		} 
		else if (block < 90) 
		{ 
			a.setX(w / 2 + size);
			b.setX(w / 2);
			c.setX(w / 2 + size);
			c.setY(size);
			d.setX(w / 2 + size + size);
			d.setY(size);
			name = "z";
		} 
		else 
		{ 
			a.setX(w / 2 - size - size);
			b.setX(w / 2 - size);
			c.setX(w / 2);
			d.setX(w / 2 + size);
			name = "i";
		}
		return new Tetromino(a, b, c, d, name);
	}
}
