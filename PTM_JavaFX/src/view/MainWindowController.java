
/*    PIPE GAME - VIEW    */

package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.PipeGame_Model;
import Themes.BlackTheme;
import Themes.BlueTheme;
import Themes.IDisplayTheme;
import ViewModel.PipeGame_ViewModel;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class MainWindowController implements Initializable {

	public ListProperty<char[]> game;
	public PipeGame_ViewModel vm;
	@FXML
	PipeGameDisplayer pipeGameDisplayer;
	@FXML
	Label timePlayed;
	@FXML
	Label stepsPlayed;

	public MainWindowController() {
		PipeGame_Model m = new PipeGame_Model();
		this.vm = new PipeGame_ViewModel(m);
		this.game = new SimpleListProperty<>();
		this.game.bind(vm.game);
		vm.stepsPlayed.addListener((observable, oldValue, newValue) -> stepsPlayed.setText(Integer.toString(vm.stepsPlayed.get())));
		vm.timePlayed.addListener((observable, oldValue, newValue) -> timePlayed.setText(Integer.toString(vm.timePlayed.get())));
	}

	public void addMouseHandler() {
		if (pipeGameDisplayer == null)
			return;
		pipeGameDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			pipeGameDisplayer.requestFocus();
		});
		pipeGameDisplayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double w = pipeGameDisplayer.getW();
				double h = pipeGameDisplayer.getH();
				int i = (int) (event.getX() / w);
				int j = (int) (event.getY() / h);
				vm.rotatePipe(j, i);
				pipeGameDisplayer.setPipeGameData(vm.game);
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		timePlayed.setText("0");
		stepsPlayed.setText("0");
		this.setPipeGame(new BlueTheme(), this.vm.game);
		this.addMouseHandler();
	}

	public void start() {
		System.out.println("Start");
		this.vm.startTimer();
	}

	public void openFile() throws IOException {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open Pipe Game File");
		fc.setInitialDirectory(new File("./Resources/Games/NewGames"));
		File chosen = fc.showOpenDialog(null);
		if (chosen != null) {
			System.out.println(chosen.getName());
			if (this.vm.loadGame(chosen) == true) {
				alertLoadedSuccessfully();
			} else {
				alertLoadFailed();
			}
		} else {
			alertFileError();
		}
	}

	public void saveGame() throws IOException {
		if (this.vm.saveGame() == true) {
			alertSavedSuccessfully();
		} else {
			alertSavedSuccessfully();
		}
	}

	public void changeTheme() {
		// theme changer. as for now - it is not generic enough.
		if (pipeGameDisplayer.ti.getThemeName() == "Blue") {
			pipeGameDisplayer.ti.stopMusic();
			pipeGameDisplayer.changeTheme(new BlackTheme());
			pipeGameDisplayer.ti.playMusic();
		} else {
			pipeGameDisplayer.ti.stopMusic();
			pipeGameDisplayer.changeTheme(new BlueTheme());
			pipeGameDisplayer.ti.playMusic();
		}
	}

	public void setViewModel(PipeGame_ViewModel vm) {
		this.vm = vm;
	}

	public void setPipeGame(IDisplayTheme newDT, ListProperty<char[]> gameData) {
		this.pipeGameDisplayer.setPipeGame(newDT, gameData);
	}

	public void solve() throws UnknownHostException, IOException {
		ArrayList<String> solution = this.vm.solve();
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				int i = 0;
				while (i < solution.size()) {
					String[] s = solution.get(i).split(",");
					int y = Integer.parseInt(s[0]);
					int x = Integer.parseInt(s[1]);
					int rotate = Integer.parseInt(s[2]);
					for (int k = 0; k < rotate; k++) {
						// runnable responsible for the pipe rotation delay
						Platform.runLater(() -> {
							vm.rotatePipe(y, x);
							pipeGameDisplayer.setPipeGameData(vm.game);
						});
						i++;
						Thread.sleep(200);
					}
				}
				return null;
			}
		};
		new Thread(task).start();
		System.out.println("We Won!");
	}

	public void checkWin() throws UnknownHostException, IOException {
		boolean winLose = this.vm.checkWin();
		if(winLose==true){
			alertWonMessage();
		}
		else{
			alertLoseMessage();
		}
	}

	//About
	public void about(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText(null);
		alert.setContentText("Creators: Eilon Vainboim and Ziv Cohen");
		alert.showAndWait();
	}


	//File Error
	public void alertFileError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("There was a problem with the file");
		alert.showAndWait();
	}

	//Saved Successfully
	public void alertSavedSuccessfully() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Saved");
		alert.setHeaderText(null);
		alert.setContentText("Game saved");
		alert.showAndWait();
	}

	//Problem With Save
	public void alertSaveFailed() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Save failed");
		alert.showAndWait();
	}

	//Loaded Successfully
	public void alertLoadedSuccessfully() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Loaded");
		alert.setHeaderText(null);
		alert.setContentText("Game loaded");
		alert.showAndWait();
	}

	//Problem With Load
	public void alertLoadFailed() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Load failed");
		alert.showAndWait();
	}

	//Win Alert
	public void alertWonMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Win");
		alert.setHeaderText(null);
		alert.setContentText("You Won The Game!");
		alert.showAndWait();
	}

	//Lose Error
	public void alertLoseMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Lose");
		alert.setHeaderText(null);
		alert.setContentText("You didn't reach the goal");
		alert.showAndWait();
	}

}
