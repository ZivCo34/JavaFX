

/*    PIPE GAME - VIEWMODEL    */


package ViewModel;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import Model.PipeGame_Model;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

public class PipeGame_ViewModel implements ViewModel_Interface {

	PipeGame_Model m;
	public ListProperty<char[]> game;

	public PipeGame_ViewModel(PipeGame_Model m) {
		this.m = m;
		this.game = new SimpleListProperty<>();
		this.game.bind(m.game);
	}

	@Override
	public void changePosition(int i, int j, int numOfRotations) {
		// TODO Auto-generated method stub
	}

	@Override
	public void loadGame(File gameFile) throws IOException {
		this.m.loadGame(gameFile);
	}

	@Override
	public void saveGame() throws IOException {
		this.m.saveGame();
	}

	@Override
	public void solve() throws UnknownHostException, IOException {
		this.m.solve();
	}

	public void rotatePipe(int i, int j, int timesToRotate){
		this.m.rotatePipe(i,j,timesToRotate);
	}

}
