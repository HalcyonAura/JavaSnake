import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.*;

public class Head{
	//Location initialization
	private int row, col;
	//Initialize instance of SnakeGUI
	private SnakeGUI game;
	//Need to initialize food to get old colors
	private Food food;

	//True depending on the direction it is heading (initially no movement (at beginning of game, or pause))
	private boolean left = false, right = false, up = false, down = false;
	
	public static boolean menu = true;
	
	//Constructor that uses Snake object)
	public Head(SnakeGUI game){
		this.game = game;
	}
	//Called when Head is intialized
	public Head(int row, int col){
		this.row = row;
		this.col = col;
	}

	//Accessed for nearest circle to it and to change location when collides with food
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

	//Used in SnakeGUI
	public boolean getLeft(){
		return left;
	}
	public boolean getRight(){
		return right;
	}
	public boolean getUp(){
		return up;
	}
	public boolean getDown(){
		return down;
	}

	/*Key events affecting snake movement and menus. Because of the issues and the time 
	 *frame this is unforunately sloppy and was the only way to produce a functional game.
	 *We didn't want to do this.
	*/
	public void keyPressed(KeyEvent e){
		//If menu isn't true, key events pertaining to the game implemented (for up and down)
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			if (!menu){
				if (!right && (game.getDifficulty() != 50)){
					left = true;
					right = false;
					up = false;
					down = false;
				}
				//snake easier to eat self if on hard (for all arrow key events)
				else{
					left = true;
					right = false;
					up = false;
					down = false;
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(!menu){
				if (!left && (game.getDifficulty() != 50)){
					left = false;
					right = true;;
					up = false;
					down = false;
				}
				else{
					left = false;
					right = true;;
					up = false;
					down = false;
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP){
			if (!menu){
				if (!down && (game.getDifficulty() != 50)){
					left = false;
					right = false;
					up = true;
					down = false;
				}
				else {
					left = false;
					right = false;
					up = true;
					down = false;
				}
			}
			else if (menu){
				//Cycles through game menu
				if (menuGUI.count > 0)
					--menuGUI.count;
			}
		}	
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
			if (!menu){
				if (!up && (game.getDifficulty() != 50)){
					left = false;
					right = false;
					up = false;
					down = true;
				}
				else {
					left = false;
					right = false;
					up = false;
					down = true;
				}
			}
			//Cycles through game menu
			else if (menu){
				if (menuGUI.count < 4)
					++menuGUI.count;
			}
		}
		//Can't pause if on hard
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			if (!menu && game.getDifficulty() !=50){
				left = false;
				right = false;
				up = false;
				down = false;
				game.setCheater(true);
			}
		}
		//Ability to select menu items
		if (e.getKeyCode() == KeyEvent.VK_ENTER){
			if (menu){
				switch (menuGUI.count){
				case (0):
					menu = false;
					game.setDifficulty(100);
					break;
				case(1):
					menu = false;
					game.setDifficulty(75);
					break;
				case(2):
					menu = false;
					game.setDifficulty(50);
					break;
				case(3):
					menuGUI.count = 5;
					break;
				case(4):
					System.exit(0);
					break;
				case(5):
					menuGUI.count = 0;
					break;
				}
			}
		}
	}

	//For overriding purposes but otherwise not explicitly used
	public void keyReleased(KeyEvent e){}

	//Paint snake head and body according to color of food it just 'ate'
	public void paint(Graphics2D g){
		g.setColor(new Color(food.oldRed, food.oldGreen, food.oldBlue));
		g.fillOval(row, col, 20, 20);
	}
	//Each time moveSnake called, while the boolean is true it will move in only that direction by 20 pixels
	public void moveSnake() {
		if (left)
			row-=20;
		if (right)
			row+=20;
		if (up)
			col-=20;
		if (down)
			col+=20;
	}

	public void setMenu(boolean menu){
		this.menu = menu;
	}
	public boolean getMenu(){
		return menu;
	}
}