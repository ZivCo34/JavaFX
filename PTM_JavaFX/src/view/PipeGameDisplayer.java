package view;

import Themes.IDisplayTheme;
import Themes.ThemeInterpeter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class PipeGameDisplayer extends Canvas{

	char[][] pipeGameData;
	ThemeInterpeter ti;
	static BorderPane root;
	public void setPipeGameData(char[][] pipeGameData, IDisplayTheme newDT){
		this.pipeGameData=pipeGameData;
		this.ti=new ThemeInterpeter(newDT);
		redraw();
	}

	public void redraw(){
		if(pipeGameData!=null){
			
			double W = getWidth();
			double H = getHeight();
			double w = W/pipeGameData[0].length;
			double h = H/pipeGameData.length;

			GraphicsContext gc=getGraphicsContext2D();

			gc.clearRect(0, 0, W, H);
			for(int i=0;i<pipeGameData.length;i++)
				for(int j=0;j<pipeGameData[i].length;j++){
					if(pipeGameData[i][j]!=' ')
						gc.drawImage(ti.getImage(pipeGameData[i][j]), j*w, i*h, w, h);
				}
		}
	}
	public void changePipePosition(int i, int j, int timeToSpin){
		for(int t=0;t<timeToSpin;t++){
			switch(pipeGameData[i][j]){
			case '-':
				pipeGameData[i][j]='|';
				break;
			case '|':
				pipeGameData[i][j]='-';
				break;
			case '7':
				pipeGameData[i][j]='J';
				break;
			case 'J':
				pipeGameData[i][j]='L';
				break;
			case 'L':
				pipeGameData[i][j]='F';
				break;
			case 'F':
				pipeGameData[i][j]='7';
				break;
			}
		}
		//this is where we should call the viewmodel (30/12)
		redraw();
	}

	public void changeTheme(IDisplayTheme newDT){
		this.ti=new ThemeInterpeter(newDT);
		redraw();
		}
	public static void setBackground(BorderPane root) {
		BackgroundSize bgSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		root.setBackground(new Background(new BackgroundImage(new Image("file:Resources/BlueTheme/Background.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize)));
	}
	//this is the part which makes the canvas adaptable, so it's size is dynamic.
	public void resize(double width, double height) {
    super.setWidth(width);
    super.setHeight(height);
    redraw();
	}
	public boolean isResizable() {
		return true;
	}
	public double minHeight(double width) {
		return 100;
	}

	public double maxHeight(double width) {
		return 1200;
	}


	public double prefHeight(double width) {
		return minHeight(width);
	}


	public double minWidth(double height) {
		return 0;
	}

	public double maxWidth(double height) {
		return 10000;
	}
}
