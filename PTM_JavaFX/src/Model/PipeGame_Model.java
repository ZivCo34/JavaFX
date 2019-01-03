
/*    PIPE GAME - MODEL    */

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class PipeGame_Model implements Model_Interface {

	public int port;
	public String myIP;
	public Socket server;
	/*public int x;
	public int y;*/
	List<String> listOfSteps;
	public ListProperty<char[]> game;

	public PipeGame_Model() {
		// Create an observable list property
		this.game = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
		this.initialGame();
		this.port = 6400;
		this.myIP = "localhost";
	}

	@Override
	public void changePosition(int i, int j, int numOfRotations) {
		//
	}

	@Override
	public void loadGame(File gameFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(gameFile));
		System.out.println(gameFile);
		String line = br.readLine();
		String s = line.substring(0, line.length()-2);
		System.out.println(s);
		if(!(s.equals(null))){
			//Clear the game board so we can fill it with a new one
			game.clear();
			while (!(s.equals("done"))){
				System.out.println(s);
				game.add(s.toCharArray());
				line = br.readLine();
				s = line.substring(0, line.length()-2);
			}
		}
		br.close();
	}

	@Override
	public void saveGame() throws IOException {
		String fileTimeCreate = new SimpleDateFormat("dd-MM-yy HH-mm-ss").format(new Date());
		File file = new File("./Resources/Games/SavedGames/"+"Game Date "+fileTimeCreate+".txt");
		/*int index = 1;
		File f = new File("./Resources/Games/SavedGames/"+"Game "+index+".txt");
		while(f.exists()){
			index++;
			f = new File("./Resources/Games/SavedGames/"+"Game "+index+".txt");
		}*/
		System.out.println("Created New File To Save To");
		PrintWriter pr = new PrintWriter(file);
		String s;
		for(int i=0;i<this.game.getSize();i++){
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
			StringBuilder sb = new StringBuilder();
			int i=0;
			String s = game.get(i).toString();
			while(!s.contains("done")){

			}
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
		this.game.add(" -Fg".toCharArray());
	}

	public void rotatePipe(int i, int j, int timesToRotate){
		for (int t = 0; t < timesToRotate; t++) {
			switch (this.game.get(i)[j]) {
			case '-':
				this.game.get(i)[j] = '|';
				break;
			case '|':
				this.game.get(i)[j] = '-';
				break;
			case '7':
				this.game.get(i)[j] = 'J';
				break;
			case 'J':
				this.game.get(i)[j] = 'L';
				break;
			case 'L':
				this.game.get(i)[j] = 'F';
				break;
			case 'F':
				this.game.get(i)[j] = '7';
				break;
			}
		}
	}

}
