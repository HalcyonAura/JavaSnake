import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.*;

public class Circle{
	//Initialize
	private int row, col;
	private SnakeGUI game;
	private Food food;
	
	public Circle(SnakeGUI game){
		this.game = game;
	}
	//When Circle is called, it will put a circle object at that location
	public Circle(int row, int col){
		this.row = row;
		this.col = col;
	}
	//Used with every movement
	public void setRow(int inputRow){
		row = inputRow;
	}
	public void setCol(int inputCol){
		col = inputCol;
	}
	public int getRow(){
		return row;
	}
	public int getCol(){
		return col;
	}

	//Paint snake body according to color of food it just 'ate'
	public void paint(Graphics2D g){
		g.setColor(new Color(food.oldRed, food.oldGreen, food.oldBlue));
		g.fillOval(row, col, 20, 20);
	}
}