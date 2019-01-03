
/*    MODEL    */

package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class PipeGame_Model implements Model_Interface {

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
		// Create an observable list property
		this.game = new SimpleListProperty<>(FXCollections.observableArrayList());
		this.initialGame();
	}

	@Override
	public void changePosition(int i, int j, int numOfRotations) {
		// TODO Auto-generated method stub
	}

	@Override
	public void loadGame(File gameFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(gameFile));
		String s = br.readLine();
		if(!(s.equals(null))){
			//Clear the game board so we can fill it with a new one
			game.clear();
			while (!(s.equals("done"))){
				game.add(s.toCharArray());
			}
		}
		br.close();
	}

	@Override
	public void saveGame() throws IOException {
		String fileTimeCreate = new SimpleDateFormat("dd-MM-yy HH-mm-ss").format(new Date());
		File file = new File("./Resources/Games/SavedGames/"+fileTimeCreate+".txt");
		/*int index = 1;
		File f = new File("./Resources/Games/SavedGames/"+"Game "+index+".txt");
		while(f.exists()){
			index++;
			f = new File("./Resources/Games/SavedGames/"+"Game "+index+".txt");
		}*/
		System.out.println("Created New File To Save To");
		PrintWriter pr = new PrintWriter(file);
		String s;
		for(int i=0;i<this.game.getSize()-1;i++){
			s = new String(this.game.get(i));
			pr.println(s);
		}
		/*int i = 0;
		String s = game.get(i).toString();
		while(!(s.equals("done"))){
			pr.println(s);
		}*/
		pr.close();
		System.out.println("Done Saveing The Game To The File");
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

	public void initialGame(){
		this.game.add("s||L".toCharArray());
		this.game.add("--Fg".toCharArray());
		this.game.add("done".toCharArray());
	}

}
