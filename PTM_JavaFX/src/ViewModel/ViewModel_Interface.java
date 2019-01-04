

/*    VIEW MODEL - INTERFACE    */


package ViewModel;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public interface ViewModel_Interface {

	boolean loadGame(File gameFile) throws IOException;
	boolean saveGame() throws IOException;
	ArrayList<String> solve() throws UnknownHostException, IOException;

}
