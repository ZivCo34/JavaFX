

/*    VIEW    */


package view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import Themes.BlackTheme;
import Themes.BlueTheme;
import ViewModel.PipeGame_ViewModel;
import javafx.beans.property.ListProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class MainWindowController implements Initializable{

	public char[][] pipeGameData={
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

	public ListProperty<char[]> game;

	@FXML
	PipeGameDisplayer pipeGameDisplayer;
	public PipeGame_ViewModel vm;

	@FXML
	Label timePlay;
	@FXML
	Label stepsPlay;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		timePlay.setText(sdf.format(cal.getTime()));*/
		this.vm=new PipeGame_ViewModel();
		this.setListCharProperty();
		this.game.bind(vm.game);
		timePlay.setText("0");
		stepsPlay.setText("0");
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

	public void setViewModel(PipeGame_ViewModel vm){
		this.vm=vm;
	}

	public void setListCharProperty(){
		int rows = this.pipeGameData[0].length;
		for(int i=0;i<rows;i++){
			this.game.add(pipeGameData[i]);
		}
	}


}
