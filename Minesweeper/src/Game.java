import java.awt.Color;

public class Game extends MyPanel {
	
	/**
	 * 
	 */
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
}