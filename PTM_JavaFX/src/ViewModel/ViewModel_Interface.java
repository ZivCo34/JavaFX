package ViewModel;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

public interface ViewModel_Interface {

	void changePosition(int i, int j, int numOfRotations);
	void loadGame(File gameFile) throws IOException;
	void saveGame() throws IOException;
	void solve() throws UnknownHostException, IOException;

}
