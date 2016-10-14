import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Polygon;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//---------------------------------------------------------------------------------------------------------------------------------------
public class MyPanel extends JPanel {

	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9; 
	public static final int MINE = -1;

	public int flagCounter =10;
	public int x = -1;
	public int y = -1;

	public int totalMines = 10;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public boolean winGameStatus = false;

	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];			//Holds each grid's color
	public boolean[][] hiddenArray = new boolean[TOTAL_COLUMNS][TOTAL_ROWS];	//Holds grid's status: hidden or pressed
	public int[][] numberArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];			//Holds number of mines around a grid
	public int[][] mineArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];				//Holds mine positions

	//---------------------------------------------------------------------------------------------------------------------------------------
	//CONSTRUCTOR
	public MyPanel() { 

		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {		//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}

		setGame();	//Method that initializes game.
	}

	//---------------------------------------------------------------------------------------------------------------------------------------
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

				Color c = colorArray[x][y];
				g.setColor(c);
				g.fillRect(GRID_X + x1 + ((INNER_CELL_SIZE + 1) * x) + 1, y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * y) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);


				Color colorNumber = setNumberColor(numberArray[x][y]);
				g.setColor(colorNumber);
				//-----------------------------------------------------------------------------------------------------------------------------------
				//Draw/Paint numbers on grids that have mines around

				Font gridFont = new Font("Times New Roman",Font.BOLD,24);

				g.setFont(gridFont);
				if(numberArray[x][y]>0 && colorArray[x][y] == Color.WHITE && hiddenArray[x][y] == false){
					g.drawString("  "+String.valueOf(numberArray[x][y]),x1+GRID_X+((INNER_CELL_SIZE+1)*x),y1+(2*GRID_Y)+((INNER_CELL_SIZE+1)*y));
					//-----------------------------------------------------------------------------------------------------------------------------------								
					//Draw 'You win!' Message

					if(winGameStatus){
						g.setColor(Color.BLACK);
						gridFont = new Font("Times New Roman", Font.BOLD, 24);
						g.setFont(gridFont);
						g.drawString("You win!", getWidth()-100, getHeight()-22);
					}
				}
				//-----------------------------------------------------------------------------------------------------------------------------------								
				//Draw Mines, Flags and Counter

				//Draw Mines
				if(mineArray[x][y]==MINE && colorArray[x][y] == Color.WHITE){

					g.setColor(Color.BLACK);
					g.drawLine(x1+(GRID_X+3)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+3)+((INNER_CELL_SIZE+1)*y),x1+(GRID_X+28)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+28)+((INNER_CELL_SIZE+1)*y));
					g.drawLine(x1+(GRID_X+28)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+3)+((INNER_CELL_SIZE+1)*y),x1+(GRID_X+3)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+28)+((INNER_CELL_SIZE+1)*y));
					g.drawLine(x1+(GRID_X+2)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+15)+((INNER_CELL_SIZE+1)*y),x1+(GRID_X+28)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+15)+((INNER_CELL_SIZE+1)*y));
					g.drawLine(x1+(GRID_X+15)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+2)+((INNER_CELL_SIZE+1)*y),x1+(GRID_X+15)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+28)+((INNER_CELL_SIZE+1)*y));
					g.fillOval(x1+(GRID_X+4)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+4)+((INNER_CELL_SIZE+1)*y), 22, 22);
				}
				//Draw Flag
				if(colorArray[x][y]==Color.YELLOW){
					//flag
					Polygon flag = new Polygon();
					flag.addPoint(x1+(GRID_X+8)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+8)+((INNER_CELL_SIZE+1)*y));
					flag.addPoint(x1+(GRID_X+23)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+13)+((INNER_CELL_SIZE+1)*y));
					flag.addPoint(x1+(GRID_X+8)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+18)+((INNER_CELL_SIZE+1)*y));
					g.setColor(Color.RED);

					//Flag Pole
					g.fillPolygon(flag);
					g.setColor(Color.BLACK);
					g.fillRect(x1+(GRID_X+7)+((INNER_CELL_SIZE+1)*x),y1+(GRID_Y+7)+((INNER_CELL_SIZE+1)*y), 2, 20);
				}
			}
			//Flag Counter
			Font flagCounterFont = new Font("Times New Roman",Font.BOLD,12);
			g.setFont(flagCounterFont);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(getWidth()/12, getHeight()-40, 100, 30);
			g.setColor(Color.BLACK);
			g.drawString("Flags Left:"+String.valueOf(flagCounter), getWidth()/6, getHeight()-20);

			Polygon flag = new Polygon();
			flag.addPoint(getWidth()/11,getHeight()-35);
			flag.addPoint(getWidth()/7,getHeight()-30);
			flag.addPoint(getWidth()/11,getHeight()-25);
			g.setColor(Color.RED);
			g.fillPolygon(flag);
			g.setColor(Color.BLACK);
			g.fillRect(getWidth()/11,getHeight()-35, 2, 20);
		}
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

		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS-1 ) {   //Outside the rest of the grid
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

		if (x < 0 || x> TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS-1 ) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	//----------------------------------------------------------------------------------------------------------------------------------
	//Method counts and returns how many mines are around a grid

	public int getTotalMinesAround(int x, int y){

		int totalMines = 0;
		int xLeft;
		int xRight;
		int yTop;
		int yBottom;

		if(x == 0){xLeft = 0;}else{xLeft = x - 1;}
		if(x >= 8){xRight = 8;}else{xRight = x + 1;}
		if(y == 0){yTop = 0;}else{yTop = y - 1;}
		if(y >= 8){yBottom = 8;}else{yBottom = y + 1;}

		for (int a = xLeft; a <= xRight; a++){
			for(int b = yTop; b <= yBottom; b++){
				if((a == x && b == y) || (mineArray[x][y] == MINE)){
					continue;
				}else{
					if(mineArray[a][b] == MINE){
						totalMines++;
					}
				}
			}
		}
		return totalMines;
	}
	//----------------------------------------------------------------------------------------------------------------------------------
	//Method that runs when the game is won or lost, to show all mine locations. 
	public void showMineLocations(){
		for (int x = 0; x < 9; x++){
			for(int y = 0; y < 9; y++){
				if (mineArray[x][y] == MINE){
					colorArray[x][y] = Color.WHITE;		
				}
			}
		}
	}
	//----------------------------------------------------------------------------------------------------------------------------------
	//Win Game Status Check: Method returns TRUE if only 10 grids are still hidden.

	public boolean getWinGameStatus(){
		int hiddenGrids = 0;
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(hiddenArray[i][j]){  
					hiddenGrids++;
				}
			}
		}
		return (hiddenGrids == 10);
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	//Method sets minesweeper board to its original status  

	public void setGame(){	

		resetGame();
		setMines();

		for (int x = 0; x < 9; x++){
			for (int y = 0; y < 9; y++){

				numberArray[x][y] = getTotalMinesAround(x, y);	
			}
		}
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	//Clear grid and all arrays are restarted. 

	public void resetGame(){

		//Paint all grids gray initially
		for (int x =0; x < TOTAL_COLUMNS; x++) {   
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.LIGHT_GRAY;
			}
		}
		//Assign 0 to all number grids
		for(int x = 0; x < TOTAL_COLUMNS; x++){
			for(int y = 0; y < TOTAL_ROWS; y++){
				numberArray[x][y] = 0;
			}
		}
		//Assign 0 to all mine grids
		for(int x = 0; x < TOTAL_COLUMNS; x++){
			for(int y = 0; y < TOTAL_ROWS; y++){
				mineArray[x][y] = 0;
			}
		}
		//Assign false to all grids
		for(int x = 0; x < TOTAL_COLUMNS; x++){
			for(int y = 0; y < TOTAL_ROWS; y++){
				hiddenArray[x][y] = true;
			}
		}
		totalMines=10;
		winGameStatus = false;
		flagCounter=10;
	}
	//----------------------------------------------------------------------------------------------------------------------------------
	//Method sets mines on 10 random positions

	public void setMines(){
		Random randomGrid = new Random();

		while(totalMines != 0){

			int xRandom = randomGrid.nextInt(9);
			int yRandom = randomGrid.nextInt(9);

			if(!(mineArray[xRandom][yRandom] == MINE && totalMines != 0)){
				mineArray[xRandom][yRandom] = MINE;
				totalMines--;
			}
		}
	}
	//----------------------------------------------------------------------------------------------------------------------------------
	//Method that runs when a mouse is pressed on a grid with a left click. 

	public void selectGrid(int x, int y){

		if((hiddenArray[x][y] && colorArray[x][y] != Color.YELLOW)){ //Grid is hidden and isn't a flag
			if(mineArray[x][y] == MINE){
				gameLost();
			}else{
				if(numberArray[x][y] > 0){
					colorArray[x][y] = Color.WHITE;
					hiddenArray[x][y] = false;
					if(getWinGameStatus()){
						winGameStatus = true;
						gameWon();
					}
				}else if(numberArray[x][y] == 0){
					openSurroundings(x, y);
				}
			}
		}
	}
	//----------------------------------------------------------------------------------------------------------------------------------
	// Method that runs when a grid is pressed with a left click and doesn't have mines around.  

	public void openSurroundings(int x, int y){

		if( x < 0 || x > 8 || y < 0 || y > 8) return;

		if (numberArray[x][y] == 0 && hiddenArray[x][y] && colorArray[x][y] != Color.YELLOW){
			hiddenArray[x][y] = false;
			colorArray[x][y] = Color.WHITE;
			if(getWinGameStatus()){
				winGameStatus = true;
				gameWon();
			}
			openSurroundings(x + 1, y);
			openSurroundings(x - 1, y );
			openSurroundings(x, y - 1);
			openSurroundings(x, y + 1);
			openSurroundings(x + 1, y + 1);
			openSurroundings(x + 1, y - 1);
			openSurroundings(x - 1, y - 1);
			openSurroundings(x - 1, y + 1);

		}else{
			if (numberArray[x][y] > 0 && hiddenArray[x][y] && mineArray[x][y] == 0){
				selectGrid(x, y);
			}
			return;
		}
	}
	//----------------------------------------------------------------------------------------------------------------------------------	
	//Game Lost
	public void gameLost(){

		showMineLocations();		
		repaint();

		int confirmation = JOptionPane.showConfirmDialog(null, "You lost! Want to play again?", null, JOptionPane.YES_NO_OPTION);
		if(confirmation == JOptionPane.YES_OPTION){
			JOptionPane.showMessageDialog(null, "Let's get ready!");
			resetGame();
			setGame();
		}
		else{
			JOptionPane.showMessageDialog(null, "Thank You for playing!");
			System.exit(0);
		}
	}

	//----------------------------------------------------------------------------------------------------------------------------------		
	//Game Won Operation
	public void gameWon(){

		showMineLocations();
		repaint();

		int buttonPressed = JOptionPane.showConfirmDialog(null, "You win! Want to play again?", null, JOptionPane.YES_NO_OPTION);
		if(buttonPressed == JOptionPane.YES_OPTION){
			JOptionPane.showMessageDialog(null, "Let's get started");
			resetGame();
			setGame();
		}
		else{
			JOptionPane.showMessageDialog(null, "Thank You for playing!");
			System.exit(0);
		}
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	//Method that assigns and returns colors to all number possibilities. 

	public Color setNumberColor(int number){
		Color numberColor = null;

		switch(number){

		case 1	: numberColor = Color.BLUE;
		break;
		case 2	: numberColor = Color.GREEN.darker();
		break;
		case 3	: numberColor = Color.MAGENTA;
		break;
		case 4	: numberColor = Color.ORANGE;
		break;
		case 5	: numberColor = Color.YELLOW;
		break;
		case 6	: numberColor = Color.RED;
		break;
		case 7	: numberColor = Color.PINK;
		break;
		case 8	: numberColor = Color.DARK_GRAY;
		break;		
		}
		return numberColor;	
	}

}