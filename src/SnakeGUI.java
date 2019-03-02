import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.*;

public class SnakeGUI extends JPanel{
	//Creates food and head of snake and connects it to SnakeGUI -> connects to main game implementation
	private Head head = new Head(this);
	//Access menu GUI (holds menu images)
	private static menuGUI mGUI;

	//Difficulty determined by speed of window updates
	//wait 100ms per loop - easy, 75ms per loop - medium, 50ms per loop - hard
	private int difficulty;

	//ArrayList of food (to be eaten), and circles (snake body)
	private ArrayList<Food> foodMult = new ArrayList<Food>();
	private ArrayList<Circle> body = new ArrayList<Circle>();

	private boolean gameOver = false, cheater = false;
	private String message = "Score: CHEATER!!!!!";
	private int score = 0;
	//Maximum width and length of JFrame (window)
	private final static int MAX_X = 520, MAX_Y = 520;
	//Enables KeyListener for Head
	static KeyListener listener;

	//Initializes random for food location generation
	Random rand;

	//Listens for key input
	public SnakeGUI() throws IOException{
		mGUI = new menuGUI();
			listener = new GameKeys();
			addKeyListener(listener);
			setFocusable(true);
	}

	//Initialize arraylists for each new game/preventing nulltype errors
	public void init(){
		if (body.size()==0){
			body.add(new Circle(head.getRow(), head.getCol()));
			//Hard = 1 food particle, Medium = 3 food particles, Easy = 5 food particles
			//Snake body length also increased by that many food particles
			foodMult.add(new Food(this));
			if (difficulty == 100){
				for (int x = 1; x < 5; x++){
					foodMult.add(new Food(this));
				}
				//Fixes score back to 0
				score-=500;
			}
			else if (difficulty == 75){
				for (int x = 1; x < 3; x++){
					foodMult.add(new Food(this));
				}
				//Fixes score back to 0
				score-=300;
			}
			
		}
	}

	public void moveSnake(){
	//Note: the snake as a whole moves from tail to head
		/*Cycles through each body part (excluding the head and segment next to the head)
		 *and moves them one by one to the place the segment ahead of it was
		*/
		for (int i = 0; i < body.size()-1; i++){
			Circle part1, part2;
			part1 = body.get(i);
			part2 = body.get(i+1);
			part1.setCol(part2.getCol());
			part1.setRow(part2.getRow());	
		}
		//Moves circle closest to head to current head position
		Circle follow;
		follow = body.get(body.size()-1);
		follow.setCol(head.getCol());
		follow.setRow(head.getRow());
		//moves head (done in Head class by moving it in the correct direction by 20 pixels)
		head.moveSnake();

		//See if snake collides with anything
		collisionWall();
		collisionSelf();
		collisionFood();
	}
	//Testing collisions
	public void collisionWall(){
		//If the snake head hits any one of the walls: game over
		if (head.getRow() >= MAX_X-20 || head.getCol() >= MAX_Y-20 || head.getRow() < -1 || head.getCol() < -1)
			gameOver = true;
	}

	public void collisionSelf(){
		//If snake head hits itself at any point and the game has started: game over
		for(Circle c: body){
			if (c.getRow() == head.getRow() && c.getCol() == head.getCol() && (head.getLeft() || head.getRight() || head.getUp() || head.getDown()))
				gameOver = true;
		}
	}

	public void collisionFood(){
		//If the head collides with a food particle (cycle through array list to check each food particle)
		for (Food f : foodMult){
			if (head.getRow() == f.getRow() && head.getCol() == f.getCol()){
				//Food is relocated to a randomized location
				rand = new Random();
				int row = rand.nextInt(24)*20;
				int col = rand.nextInt(23)*20;

				f.setRow(row/*460*/);
				f.setCol(col/*440*/);
				
				//Sets of changes of color in Food class
				f.setIsEaten(true);

				//New body segment is added at location of the head
				body.add(new Circle(head.getRow(), head.getCol()));
				//and head is shifted ahead of body depending on the direction it was moving
				if (head.getLeft())
					head.setRow(head.getRow()-20);
				else if (head.getRight())
					head.setRow(head.getRow()+20);
				else if (head.getUp())
					head.setCol(head.getCol()-20);
				else if (head.getDown())
					head.setCol(head.getCol()+20);

				//If pause unused, increment score by 100 for every food eaten
				if (!cheater)
					score+=100;
			}
		}
	}

	//Paint method overridden by respective classes
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//paint the menu as they are cycled through
		if (head.getMenu()){
			g2d.drawImage(mGUI.getImg(), 0,0, this);
		}
		//paint game upon selection of menu option (unless How To Play)
		else if (!head.getMenu()){
			head.setMenu(false);
			
			head.paint(g2d);
			//calls each instance of circle and food and paints them
			for (Circle part : body){
				part.paint(g2d);
			}
			for (Food f : foodMult){
					f.paint(g2d);
			}
			
			//paints the score (or message if pause used)
			if (cheater)
				g2d.drawString(message, 30, 30);

			else
				g2d.drawString("Score: " + Integer.toString(score), 30, 30);
		}
		//paints game over screen + score
		if (gameOver){
			g2d.drawImage(mGUI.getImg(6), 0,0, this);
			if (cheater)
				g2d.drawString("Score: CHEATER!!!!!", 225, 400);

			else
				g2d.drawString("Score: " + Integer.toString(score), 225, 400);
		}
	}

	//Reset head, food, body, directions (bc static variables), gameOver (to go through the game loop again), etc.
	public void reset(){
		head = new Head(this);
		foodMult = new ArrayList<Food>();
		body = new ArrayList<Circle>();
		gameOver = false;
		score = 0;
		cheater = false;
	}

	//Key listener method for game as an inner class
	public class GameKeys implements KeyListener{
		public void keyTyped(KeyEvent e){
		}
		//@Override
		public void keyPressed(KeyEvent e){
			head.keyPressed(e);
		}
		//@Override
		public void keyReleased(KeyEvent e){
			head.keyReleased(e);
		}
	}

	//Getters and setters
	public int getMax_X(){
		return MAX_X;
	}
	public int getMax_Y(){
		return MAX_Y;
	}

	public int getScore(){
		return score;
	}

	public void setCheater(boolean cheat){
		cheater = cheat;
	}
	public boolean getCheater(){
		return cheater;
	} 
	public boolean getGameOver(){
		return gameOver;
	}

	public int getDifficulty(){
		return difficulty;
	}
	public void setDifficulty(int diff){
		difficulty = diff;
	}
}