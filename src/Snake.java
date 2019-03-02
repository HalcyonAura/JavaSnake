import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

//Run game
public class Snake{
	public static void main(String[] args) throws InterruptedException, IOException{
		//Create window and panel for game

		//Initialize window with title
		JFrame frame = new JFrame("#Snek2015");
		//add SnakeGUI (as a panel) to the window & make background black, and set size of frame
		SnakeGUI gui = new SnakeGUI();
		frame.add(gui);
		gui.setBackground(Color.BLACK);
		frame.setSize(gui.getMax_X(), gui.getMax_Y());
		//Make window non-resizable
		frame.setResizable(false);
		//Make window open in the center of the screen
		frame.setLocationRelativeTo(null);
		//Make sure you can close the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		boolean play = true;
		//Full game loop (menu + snake game)
		while (play) {
			//Make window visible
			//Paint menu until selection chosen
			frame.setVisible(true);
			while (Head.menu){
				gui.repaint();
			}

			//initializes all variables in SnakeGUI
			gui.init();
			
			//game loop
			//"Snake" speed depends on the difficulty
			//Difficulty affects wait time for repaint (shorter wait time = faster repaint)
			while (!gui.getGameOver()) {
				gui.moveSnake();
				gui.repaint();
				Thread.sleep(gui.getDifficulty());
			}
			//paints game over screen
			gui.repaint();
			//waits 5s then restarts
			Thread.sleep(5000);
	
			//Return to menu screen upon game over + 5s
			Head.menu = true;
			//Reset snake
			gui.reset();
		}
		//As an extra precaution to exit the program if it ever leaves the play loop.
		System.exit(0);
	}
}