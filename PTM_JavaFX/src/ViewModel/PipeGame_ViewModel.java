

/*    PIPE GAME - VIEWMODEL    */


package ViewModel;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Model.PipeGame_Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;

public class PipeGame_ViewModel implements ViewModel_Interface {

	PipeGame_Model m;
	public ListProperty<char[]> game;
	public IntegerProperty stepsPlayed;
	public IntegerProperty timePlayed;

	public PipeGame_ViewModel(PipeGame_Model m) {
		this.m = m;
		this.game = new SimpleListProperty<>();
		this.game.bind(m.game);
		stepsPlayed = new SimpleIntegerProperty();
		stepsPlayed.bind(m.stepsPlayed);
		timePlayed = new SimpleIntegerProperty();
		timePlayed.bind(m.timePlayed);
	}

	@Override
	public boolean loadGame(File gameFile) throws IOException {
		return this.m.loadGame(gameFile);
	}

	@Override
	public boolean saveGame() throws IOException {
		return this.m.saveGame();
	}

	@Override
	public ArrayList<String> solve() throws UnknownHostException, IOException {
		return this.m.solve();
	}

	public void rotatePipe(int j, int i){
		this.m.rotatePipe(j,i);
	}

	public void startTimer(){
		this.m.startTimer();
	}

	public boolean checkWin() throws UnknownHostException, IOException{
		return this.m.checkWin();
	}

}
