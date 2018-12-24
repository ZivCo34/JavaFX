package view;

import Themes.BlackTheme;
import Themes.ThemeInterpeter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PipeGameDisplayer extends Canvas{

	char[][] pipeGameData;
	int cCol,cRow;

	public PipeGameDisplayer(){
		cCol=0;
		cRow=1;
	}

	public void setCharacterPosition(int row,int col){
		cRow=row;
		cCol=col;
		redraw();
	}

	public int getcCol() {
		return cCol;
	}

	public int getcRow() {
		return cRow;
	}

	public void setPipeGameData(char[][] pipeGameData){
		this.pipeGameData=pipeGameData;
		redraw();
	}

	public void redraw(){
		if(pipeGameData!=null){
			double W = getWidth();
			double H = getHeight();
			double w = W/pipeGameData[0].length;
			double h = H/pipeGameData.length;

			GraphicsContext gc=getGraphicsContext2D();

			ThemeInterpeter imInt = new ThemeInterpeter(new BlackTheme());

			gc.clearRect(0, 0, W, H);

			for(int i=0;i<pipeGameData.length;i++)
				for(int j=0;j<pipeGameData[i].length;j++){
					gc.drawImage(imInt.getImage(pipeGameData[i][j]), j*w, i*h, w, h);
				}

			gc.setFill(Color.RED);
			gc.fillOval(cCol*w, cRow*h, w, h);

		}
	}
}
