

/*    PIPE GAME - VIEWMODEL    */


package ViewModel;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import Model.PipeGame_Model;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

public class PipeGame_ViewModel implements ViewModel_Interface {

	public PipeGame_Model m;
	public ListProperty<char[]> game;

	public PipeGame_ViewModel() {
		this.m = new PipeGame_Model();
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

}
