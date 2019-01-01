package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Themes.BlackTheme;
import Themes.BlueTheme;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

public class MainWindowController implements Initializable{

	char[][] pipeGameData={
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
	};

	@FXML
	PipeGameDisplayer pipeGameDisplayer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pipeGameDisplayer.setPipeGameData(pipeGameData,new BlueTheme());
		pipeGameDisplayer.ti.playMusic();

		pipeGameDisplayer.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//W and H are the length of the whole java window
				double W = pipeGameDisplayer.getWidth();
				double H = pipeGameDisplayer.getHeight();
				//w and h are the proportional Canvas size
				int w = (int)(W/pipeGameData[0].length);
				int h = (int)(H/pipeGameData.length);
				//Getting the X's and Y's of the Mouse Click in the whole java whole
				int j = (int)event.getX();   //columns
				int i = (int)event.getY();   //rows
				//Now we calculate the i and j of the pipeGameData matrix
				int matrix_j = j/w;
				int matrix_i = i/h;
				System.out.println(H+" "+W);
				System.out.println(h+" "+w);
				System.out.println(i+" "+j);
				System.out.println(matrix_i+" "+matrix_j);
				pipeGameDisplayer.changePipePosition(matrix_i, matrix_j,1);
				}
		});
	}

	public void start(){
		System.out.println("Start");
	}

	public void openFile(){
		FileChooser fc=new FileChooser();
    	fc.setTitle("open pipe game file");
    	fc.setInitialDirectory(new File("./Resources"));
    	File chosen = fc.showOpenDialog(null);
    	if (chosen!=null){
    		System.out.println(chosen.getName());
    	}
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
}
