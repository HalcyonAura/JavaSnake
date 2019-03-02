import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;

import javax.imageio.ImageIO;

//Works with loading game images and putting them into an array
public class menuGUI {
	menuGUI() throws IOException{
		imgs[0] = ImageIO.read(new File(resourceConvert("/resources/Snek_Menu1.png")));
		imgs[1] = ImageIO.read(new File(resourceConvert("/resources/Snek_Menu2.png")));
		imgs[2] = ImageIO.read(new File(resourceConvert("/resources/Snek_Menu3.png")));
		imgs[3] = ImageIO.read(new File(resourceConvert("/resources/Snek_Menu4.png")));
		imgs[4] = ImageIO.read(new File(resourceConvert("/resources/Snek_Menu5.png")));
		imgs[5] = ImageIO.read(new File(resourceConvert("/resources/Snek_HowToPlay.png")));
		imgs[6] = ImageIO.read(new File(resourceConvert("/resources/Snek_GameOver.png")));
	}
	public String resourceConvert(String path){
		URL imageURL = getClass().getResource(path);
		if (System.getProperty("os.name").contains("indos")){
			return imageURL.getPath().substring(1);
		}
		return imageURL.getPath();
	}
	private Image [] imgs = new Image[7]; 
	//Keeps track of where in the array program is
	public static int count = 0;
	
	//Return nonspecific or specific images
	public Image getImg(){
		return imgs[count];
	}
	public Image getImg(int i){
		return imgs[i];
	}
	
}
