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

public class PipeGameDisplayer extends Canvas{

	char[][] pipeGameData;
	ThemeInterpeter ti;

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
		redraw();
	}

	public void changeTheme(IDisplayTheme newDT){
		this.ti=new ThemeInterpeter(newDT);
		redraw();
	}

}
