
/*    MODEL    */

package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Observable;

import javafx.beans.property.ListProperty;

public class PipeGame_Model extends Observable implements Model_Interface {

	public int port;
	public String myIP;
	public Socket server;
	public int x;
	public int y;
	public List<String> listOfSteps;
	public ListProperty<char[]> game;

	public PipeGame_Model() {
		this.port = 6400;
		this.myIP = "localhost";
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
	public void solve() throws UnknownHostException, IOException {
		/*התקשורת בין הלקוח לשרת
		 * צריך ליצור פה בעצם את העברת הנתונים לשרת
		 * וקבלת רשימה של פקודות הוזזה
		 * מורכב ממיקום
		 * (X,Y,TimeToRotate)*/
		server = new Socket(myIP, port);
		if(server!=null){
			System.out.println("Successfully Creating Server Socket, Connected To Server");
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(this.server.getInputStream()));
			PrintWriter toServer = new PrintWriter(this.server.getOutputStream());
			System.out.println("Successfully Creating BufferedReader And PrintWriter");
			System.out.println("Now Passing The Game To Server To Solve");
			int i=0;
			while(!(game.get(i).toString().equals("done"))){
				toServer.append(game.get(i).toString()+"\n");
				i++;
			}
			toServer.flush();
			System.out.println("Now Getting Steps To Solution From Server");
			String line = fromServer.readLine();
			while(!(line.equals("done"))){
				listOfSteps.add(line);
				line = fromServer.readLine();
			}
			System.out.println("Now We Have A List Of Steps To Solution");
		}
	}

}
