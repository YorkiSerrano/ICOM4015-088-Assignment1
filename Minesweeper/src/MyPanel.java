import java.awt.Color;
//import java.awt.Font;
import java.awt.Graphics;

import java.awt.Insets;
import java.util.Random;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
//----------------------------------------------------------------------------------------------------------------------------------
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9; 
	public static final int MINE = 1;
	
	public int x = -1;
	public int y = -1;
	public int totalMines = 10;
	
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	
	public int[][] mineArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	
	public int[][] numberArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];

	public MyPanel() {   //This is the constructor... this code runs first to initialize
		
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
//-----------------------------------------------------------------------------------------------------------------------------------		
//Paint all grids gray initially
		for (int x =0; x < TOTAL_COLUMNS; x++) {   
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.LIGHT_GRAY;
			}
		}
//-----------------------------------------------------------------------------------------------------------------------------------
//Assign 0 to all grids
		for(int x = 0; x < TOTAL_COLUMNS; x++){
			for(int y = 0; y < TOTAL_ROWS; y++){
				mineArray[x][y] = 0;
			}
		}
//-----------------------------------------------------------------------------------------------------------------------------------
//Mine Setter
		
		Random randomGrid = new Random();
			
		while(totalMines != 0){

			int xRandom = randomGrid.nextInt(9);
			int yRandom = randomGrid.nextInt(9);

			if(!(mineArray[xRandom][yRandom] == MINE && totalMines != 0)){
				mineArray[xRandom][yRandom] = MINE;
				System.out.println("Coordinate: (" + xRandom + "," + yRandom + ")\n"); //Debugging Purposes
				totalMines--;
			}
		}
		}
	
//-----------------------------------------------------------------------------------------------------------------------------------
//Painting
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
        
        
		//Compute interior coordinates
		
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width =x2 - x1;
		int height = y2 - y1;
		
//-----------------------------------------------------------------------------------------------------------------------------------
//Paint the background
		
		Color backColor = new Color(0xA1CAF1);//Baby Blue Eyes https://en.wikipedia.org/wiki/List_of_colors:_A%E2%80%93F
		g.setColor(backColor);
		g.fillRect(x1, y1, width + 1, height + 1);

//-----------------------------------------------------------------------------------------------------------------------------------
//Draw 9 X 9 Grid (TOTAL_COLUMNS X TOTAL_ROWS)
		
		g.setColor(Color.BLACK);
		
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}
//-----------------------------------------------------------------------------------------------------------------------------------
//Paint cell colors
		
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				
				
				//if(x != TOTAL_COLUMNS-1 || y!= TOTAL_ROWS-1){
				if ((x == 0) || (y != TOTAL_ROWS)) {
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
					
//					if(c == Color.WHITE && mineArray[x][y] != 0){
//						g.setColor(Color.YELLOW);
//						System.out.println(mineArray[x][y]);

//						g.setFont(new Font("TimesRoman", Font.BOLD, 20));
//						g.drawString(String.valueOf(mineArray[x][y]), getGridX(x, y), getGridY(x, y));
				//	}
					
				}
			}
		}
//-----------------------------------------------------------------------------------------------------------------------------------
	}
//-----------------------------------------------------------------------------------------------------------------------------------
//Get X Grid Coordinate
	
	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
//----------------------------------------------------------------------------------------------------------------------------------
//Get Y Grid Coordinate
	
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		
		if (x < 0 || x> TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
//----------------------------------------------------------------------------------------------------------------------------------
//Open Grids
	public void unhideGrids(int x, int y){
		int totalMines = 0;
		int xLeft;
		int xRight;
		int yTop;
		int yBottom;
		
		if(x == 0){
			xLeft = 0;
		}else{
			xLeft = x - 1;
		}
		if(x >= 8){
			xRight = 8;
		}else{
			xRight = x + 1;
		}
		if(y <= 0){
			yTop = 0;
		}else{
			yTop = y - 1;
		}
		if(y >= 8){
			yBottom = 8;
		}else{
			yBottom = y + 1;
		}
//----------------------------------------------------------------------------------------------------------------------------------
		if (mineArray[x][y] == MINE){
			Game minesweeper = new Game();
			minesweeper.gameLost();
		}else{
			for(int a = xLeft; a <= xRight; a++){
				for(int b = yTop; b <= yBottom; b++){
					if (mineArray[a][b] == MINE){
						totalMines++;
					}
				}
			}			
			if (totalMines != 0){
				colorArray[x][y] = Color.WHITE;
				mineArray[x][y] = totalMines;
			}
			else{
				
			}
			System.out.println(totalMines);
			System.out.println(mineArray[x][y]);
		}
	}

}



						
						
						
						
						
						
						
						
						
						