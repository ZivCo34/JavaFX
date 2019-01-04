
/*    PIPE GAME - MODEL    */

package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class PipeGame_Model implements Model_Interface {

	public int port;
	public String myIP;
	public Socket server;
	ArrayList<String> listOfSteps;
	public ListProperty<char[]> game;
	public IntegerProperty stepsPlayed;
	public IntegerProperty timePlayed;
	public ScheduledExecutorService timer;



	public PipeGame_Model() {
		// Create an observable list property
		game = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
		initialGame();
		port = 6400;
		myIP = "localhost";
		stepsPlayed = new SimpleIntegerProperty();
		timePlayed = new SimpleIntegerProperty();
		startTimer();
	}

	@Override
	public boolean loadGame(File gameFile) throws IOException {
		shutdownTimer();
		Scanner scan=new Scanner(gameFile);
		if(!scan.equals(null)) {
			this.game.clear();
		}
		while (scan.hasNext()){
			char[] line=null;
			String str = scan.nextLine();
			line = str.toCharArray();
			System.out.println(line);
			this.game.add(line);
		}
		//If we load the whole game we get inside, return true
		if(scan.hasNext()==false){
			scan.close();
			startTimer();
			return true;
		}
		//If we still have parts of the game that we didn't load, return false
		else{
			scan.close();
			return false;
		}
	}

	@Override
	public boolean saveGame() throws IOException {
		String fileTimeCreate = new SimpleDateFormat("dd-MM-yy HH-mm-ss").format(new Date());
		File file = new File("./Resources/Games/SavedGames/"+"Game Date "+fileTimeCreate+".txt");
		//If the file we want to write to is free we get in and save the level in this file
		//And we return true that the file is saved
		if(!file.exists()){
			System.out.println("Created New File To Save To");
			PrintWriter pr = new PrintWriter(file);
			String s;
			for(int i=0;i<this.game.getSize();i++){
				s = new String(this.game.get(i));
				pr.println(s);
			}
			pr.println("Time:"+timePlayed.get());
			pr.println("Steps:"+stepsPlayed.get());

			pr.close();
			System.out.println("Done Saveing The Game To The File");
			return true;
		}
		return false;

	}

	@Override
	public ArrayList<String> solve() throws UnknownHostException, IOException {
		/*
		 * התקשורת בין הלקוח לשרת צריך ליצור פה בעצם את העברת הנתונים לשרת וקבלת
		 * רשימה של פקודות הוזזה מורכב ממיקום (X,Y,TimeToRotate)
		 */
		listOfSteps = new ArrayList<>();
		server = new Socket(myIP, port);
		String str = new String();
		System.out.println("Successfully Creating Server Socket, Connected To Server");
		BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		PrintWriter out = new PrintWriter(server.getOutputStream());
		System.out.println("Successfully Creating BufferedReader And PrintWriter");

		System.out.println("Now Passing The Game To Server To Solve");
		System.out.println("game size: "+game.get(0).length);
		for (int i = 0; i < game.size(); i++) {
			str = new String(game.getValue().get(i));
			out.println(str);
			System.out.println(str);
			System.out.println("line size: "+str.length());
			out.flush();
		}
		out.println("done");
		out.flush();

		System.out.println("Now Getting Steps To Solution From Server");
		while (!(str = in.readLine()).equals("done")) {
			listOfSteps.add(str);
			System.out.println(str);
		}
		System.out.println("Now We Have A List Of Steps To Solution");
		in.close();
		out.close();
		server.close();
		return listOfSteps;
	}

	public void initialGame(){
		this.game.add("s||L".toCharArray());
		this.game.add(" -Fg".toCharArray());
	}

	public void rotatePipe(int j, int i) {
		switch (this.game.get(j)[i]) {
		case '-':
			this.game.get(j)[i] = '|';
			stepsPlayed.set(stepsPlayed.get()+1);
			break;
		case '|':
			this.game.get(j)[i] = '-';
			stepsPlayed.set(stepsPlayed.get()+1);
			break;
		case '7':
			this.game.get(j)[i] = 'J';
			stepsPlayed.set(stepsPlayed.get()+1);
			break;
		case 'J':
			this.game.get(j)[i] = 'L';
			stepsPlayed.set(stepsPlayed.get()+1);
			break;
		case 'L':
			this.game.get(j)[i] = 'F';
			stepsPlayed.set(stepsPlayed.get()+1);
			break;
		case 'F':
			this.game.get(j)[i] = '7';
			stepsPlayed.set(stepsPlayed.get()+1);
			break;
		}
	}

	public void startTimer(){
		timer = Executors.newScheduledThreadPool(1);
		timer.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(()->timePlayed.setValue(timePlayed.get()+1));
			}
		}, 0, 1, TimeUnit.SECONDS);
	}

	public void shutdownTimer(){
		if(timer!=null){
			timer.shutdown();
		timer=null;
		}
	}

	public boolean checkWin() throws UnknownHostException, IOException{
		System.out.println("We Want To Know If We Win The Game Or Not");
		System.out.println("If We Win We Get: Done\nIf We Lose We Get Someting Else\n");
		listOfSteps = new ArrayList<>();
		server = new Socket(myIP, port);
		String str = new String();
		BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		PrintWriter out = new PrintWriter(server.getOutputStream());
		for (int i = 0; i < game.size(); i++) {
			str = new String(game.getValue().get(i));
			out.println(str);
			out.flush();
		}
		out.println("done");
		out.flush();

		System.out.println("\nNow Getting Answer From The Server");
		if ((str = in.readLine()).equals("done")) {
			System.out.println("We Won!");
			return true;
		}
		System.out.println("We Lose");
		return false;
	}

}
