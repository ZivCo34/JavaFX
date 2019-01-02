

/*    VIEWMODEL    */


package ViewModel;

import java.util.Observable;

import Model.PipeGame_Model;
import javafx.beans.property.ListProperty;

public class PipeGame_ViewModel extends Observable implements ViewModel_Interface{

	public PipeGame_Model m;
	public ListProperty<char[]> game;


	public PipeGame_ViewModel() {
		this.m = new PipeGame_Model();
		this.game.bind(m.game);
	}

	@Override
	public void changePosition(int i, int j, int numOfRotations) {
		// TODO Auto-generated method stub
	}

	@Override
	public void load(String path) {
		// TODO Auto-generated method stub
	}

	@Override
	public void save(String path) {
		// TODO Auto-generated method stub
	}

	@Override
	public void solve() {
		// TODO Auto-generated method stub
	}

}
