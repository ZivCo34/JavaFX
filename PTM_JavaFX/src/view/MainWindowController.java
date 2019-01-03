

/*    PIPE GAME - VIEW    */


package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.PipeGame_Model;
import Themes.BlackTheme;
import Themes.BlueTheme;
import Themes.IDisplayTheme;
import ViewModel.PipeGame_ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class MainWindowController implements Initializable{

/*	public char[][] pipeGameData={
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{'s','-','-','-','-','7',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ','|',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ','|',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ','|',' ','F','-','-','7',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ','L','-','J',' ',' ','|',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','|',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','L','-','-','7',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','F','-','J',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','|',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','L','-','-','g'},
	};*/

	public ListProperty<char[]> game;

	public PipeGame_ViewModel vm;

	@FXML
	PipeGameDisplayer pipeGameDisplayer;
	@FXML
	Label timePlay;
	@FXML
	Label stepsPlay;



	public MainWindowController() {
		PipeGame_Model m =new PipeGame_Model();
		this.vm = new PipeGame_ViewModel(m);
		this.game = new SimpleListProperty<>();
		this.game.bind(vm.game);
	}


	public void addMouseHandler() {
		if(pipeGameDisplayer==null)
			return;
		pipeGameDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{pipeGameDisplayer.requestFocus();});
		pipeGameDisplayer.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				double w = pipeGameDisplayer.getW();
				double h = pipeGameDisplayer.getH();
				int i = (int) (event.getX()/w);
				int j = (int) (event.getY()/h);
				vm.rotatePipe(j, i, 1);
				pipeGameDisplayer.setPipeGameData(vm.game);
			}
		});
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		timePlay.setText("0");
		stepsPlay.setText("0");
		this.setPipeGame(new BlueTheme(), this.vm.game);
		this.addMouseHandler();
	}

	public void start(){
		System.out.println("Start");
	}

	public void openFile() throws IOException{
		FileChooser fc=new FileChooser();
    	fc.setTitle("Open Pipe Game File");
    	fc.setInitialDirectory(new File("./Resources/Games/NewGames"));
    	File chosen = fc.showOpenDialog(null);
    	if (chosen!=null){
    		System.out.println(chosen.getName());
    		this.vm.loadGame(chosen);
    	}
	}

	public void saveGame() throws IOException{
		this.vm.saveGame();
	}

	public void changeTheme(){
		//theme changer. as for now - it is not generic enough.
		if(pipeGameDisplayer.ti.getThemeName()=="Blue") {
			pipeGameDisplayer.ti.stopMusic();
			pipeGameDisplayer.changeTheme(new BlackTheme());
			pipeGameDisplayer.ti.playMusic();
		}
		else {
			pipeGameDisplayer.ti.stopMusic();
			pipeGameDisplayer.changeTheme(new BlueTheme());
			pipeGameDisplayer.ti.playMusic();
		}
	}

	public void setViewModel(PipeGame_ViewModel vm){
		this.vm=vm;
	}

	public void setPipeGame(IDisplayTheme newDT, ListProperty<char[]> gameData){
		this.pipeGameDisplayer.setPipeGame(newDT, gameData);
	}

}
