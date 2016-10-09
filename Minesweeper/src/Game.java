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
	
	public void gameLost(){
		System.out.println("You lose!");
		for (int x = 0; x < 9; x++){
			for(int y = 0; y < 9; y++){
				if (isMine(x, y)){
					colorArray[x][y] = Color.BLACK;
				}
			}
		}
		
	}

}


