

/*    PIPE GAME DISPLAYER    */


package view;

import java.util.List;

import Themes.IDisplayTheme;
import Themes.ThemeInterpeter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class PipeGameDisplayer extends Canvas {

	List<char[]> pipeGameData;
	ThemeInterpeter ti;
	double w;
	double h;



	public void setPipeGame(IDisplayTheme newDT, List<char[]> pipeGameData){
		setPipeGameTheme(newDT);
		setPipeGameData(pipeGameData);
		this.ti.playMusic();
	}

	public void setPipeGameTheme(IDisplayTheme newDT){
		this.ti = new ThemeInterpeter(newDT);
	}

	public void setPipeGameData(List<char[]> pipeGameData) {
		this.pipeGameData = pipeGameData;
		redraw();
	}

	public void redraw() {
		if (pipeGameData != null) {

			double W = getWidth();
			double H = getHeight();
			w = W / pipeGameData.get(0).length;
			h = H / pipeGameData.size();

			GraphicsContext gc = getGraphicsContext2D();

			gc.clearRect(0, 0, W, H);
			for (int i = 0; i < pipeGameData.size(); i++)
				for (int j = 0; j < pipeGameData.get(i).length; j++) {
					if (pipeGameData.get(i)[j] != ' ')
						gc.drawImage(ti.getImage(pipeGameData.get(i)[j]), j * w, i * h, w, h);
				}
		}
	}

	public void changeTheme(IDisplayTheme newDT) {
		this.ti = new ThemeInterpeter(newDT);
		redraw();
	}

	// this is the part which makes the canvas adaptable, so it's size is
	// dynamic.
	@Override
	public void resize(double width, double height) {
		super.setWidth(width);
		super.setHeight(height);
		redraw();
	}

	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double minHeight(double width) {
		return 100;
	}

	@Override
	public double maxHeight(double width) {
		return 1200;
	}

	@Override
	public double prefHeight(double width) {
		return minHeight(width);
	}

	@Override
	public double prefWidth(double height){
		return minWidth(height);
	}

	@Override
	public double minWidth(double height) {
		return 0;
	}

	@Override
	public double maxWidth(double height) {
		return 10000;
	}

	public double getW(){
		return this.w;
	}

	public double getH(){
		return this.h;
	}

}
