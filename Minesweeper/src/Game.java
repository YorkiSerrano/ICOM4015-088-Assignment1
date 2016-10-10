import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JOptionPane;

public class Game extends MyPanel {
	private static final long serialVersionUID = 1L;

	public boolean isHidden(int x, int y){
	
		return (colorArray[x][y] == Color.LIGHT_GRAY);
		
	}
	
	public boolean isFlagged(int x, int y){
		
		return (colorArray[x][y] == Color.RED);
		
	}
	
	public boolean isMine(int x, int y){
		return(mineArray[x][y] == MINE);
	}
	
	public boolean isPressed(int x, int y){
		
		
		
		return false;
	}	
}