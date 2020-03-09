package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tetromino 
{
	public int form = 1;
	public Color color;
	Rectangle a;
	Rectangle b;
	Rectangle c;
	Rectangle d;
	
	private String name;
	
	Tetromino(Rectangle a, Rectangle b, Rectangle c, Rectangle d)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	Tetromino(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String n)
	{
		this.name = n;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		
		switch (name) 
		{
		case "j":
			color = Color.SLATEGRAY;
			break;
		case "l":
			color = Color.DARKGOLDENROD;
			break;
		case "o":
			color = Color.INDIANRED;
			break;
		case "s":
			color = Color.FORESTGREEN;
			break;
		case "t":
			color = Color.CADETBLUE;
			break;
		case "z":
			color = Color.HOTPINK;
			break;
		case "i":
			color = Color.SANDYBROWN;
			break;
		}
		this.a.setFill(color);
		this.b.setFill(color);
		this.c.setFill(color);
		this.d.setFill(color);
	}
	
	public void changeTile()
	{
		if (form != 4)
			form++;
		else
			form = 1;
	}
}
