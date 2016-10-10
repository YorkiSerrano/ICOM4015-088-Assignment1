import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private int flag =10;
	
//----------------------------------------------------------------------------------------------------------------------------------
//Mouse Pressed
	public void mousePressed(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame) c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		myPanel.mouseDownGridX = myPanel.getGridX(x, y);
		myPanel.mouseDownGridY = myPanel.getGridY(x, y);
		myPanel.repaint();
		switch (e.getButton()) {
		case 1:		//Left mouse button

			break;
		case 3:		
			
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame)c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);
		switch (e.getButton()) {
		case 1:		//Left mouse button

			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Access only hidden grids
							
							if(myPanel.mineArray[myPanel.mouseDownGridX][ myPanel.mouseDownGridY]==1){
								myPanel.repaint();
								myPanel.lostGame();
							}else if(myPanel.numberArray[myPanel.mouseDownGridX][ myPanel.mouseDownGridY]>=0){
								myPanel.colorArray[myPanel.mouseDownGridX][ myPanel.mouseDownGridY]= setColor(myPanel.numberArray[myPanel.mouseDownGridX][ myPanel.mouseDownGridY]);
								myPanel.repaint();
							}
						}			
					}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			Color flagColor = Color.MAGENTA;//flag color
			if ((myPanel.mouseDownGridX >= 0 && gridX < 9) && (myPanel.mouseDownGridY >= 0 && gridY < 9)){
				
				if(myPanel.colorArray[myPanel.mouseDownGridX][ myPanel.mouseDownGridY]==flagColor){
					//remove flag
				myPanel.colorArray[myPanel.mouseDownGridX][ myPanel.mouseDownGridY]=Color.LIGHT_GRAY;
				myPanel.repaint();
				
				}else{
					//set flag
					myPanel.colorArray[myPanel.mouseDownGridX][ myPanel.mouseDownGridY]=flagColor;
					myPanel.repaint();
					
					
				}
			}
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
			}
		}
	public Color setColor(int x){
		//method for selecting the random color
		Color newColor = null;
		switch (x) {
		case 0:
			newColor = Color.WHITE;//
			break;
		case 1:
			newColor = Color.RED;//number one
			break;
		case 2:
			newColor = Color.BLUE;//number two
			break;
		case 3:
			newColor = Color.DARK_GRAY;//number three
			break;
		case 4:
			//number 4
			newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
			break;
		case 5:
			newColor = Color.GREEN;//number 5
		}
		return newColor;
	}
}