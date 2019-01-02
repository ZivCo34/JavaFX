package Model;

import java.io.IOException;
import java.net.UnknownHostException;

public interface Model_Interface {

	void changePosition(int i, int j, int numOfRotations);
	void load(String path);
	void save(String path);
	void solve() throws UnknownHostException, IOException;

}
