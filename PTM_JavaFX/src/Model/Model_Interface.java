

/*    MODEL - INTERFACE    */


package Model;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public interface Model_Interface {

	boolean loadGame(File gameFile) throws IOException;
	boolean saveGame() throws IOException;
	ArrayList<String> solve() throws UnknownHostException, IOException;

}
