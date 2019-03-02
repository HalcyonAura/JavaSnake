import java.util.Random;
import java.awt.Graphics2D;
import java.awt.Color;

public class Food{
	//Initialize location
	private int row, col;
	//Actually I don't know why this works it just does and I don't question it
	//I think it is because of paint and to add it the window
	private SnakeGUI game;
	//Color the food
	public static int red = 80, blue = 80, green = 80;
	//Keep track for the color of the snake
	public static int oldRed = red, oldBlue = blue, oldGreen = green;
	//Initialized as true to make sure the game loop starts and food is generated
	private boolean isEaten = true;

	//Initialize random for new color
	Random rand;

	//Works, don't question it, also OOP
	public Food(SnakeGUI game){
		this.game = game;
	}
	//Used when first initializing food
	public Food(int row, int col){
		this.row = row;
		this.col = col;
	}

	//Setters used to change food location when snake collides with it
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

	public boolean getIsEaten(){
		return isEaten;
	}
	public void setIsEaten(boolean eat){
		isEaten = eat;
	}

	/*If isEaten is true, the old colors are updated (to affect the snake),
	 *and new colors are generated for the new location of food
	 *isEaten is also reverted to false until next collision with snake
	*/
	public void paint(Graphics2D g){
		if (isEaten){
			rand = new Random();
			oldRed = red;
			oldGreen = green;
			oldBlue = blue;
			red = rand.nextInt(225)+30;
			green = rand.nextInt(225)+30;
			blue = rand.nextInt(225)+30;
			isEaten = false;
		}
		//Draw food
		g.setColor(new Color(red,green,blue));
		g.fillOval(row, col, 20, 20);
	}
}