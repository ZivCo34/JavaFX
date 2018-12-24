package view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class MainWindowController implements Initializable{

	char[][] pipeGameData={
			{'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'},
			{'S','-','-','-','-','7','0','0','0','0','0','0','0','0','0'},
			{'0','0','0','0','0','|','0','0','0','0','0','0','0','0','0'},
			{'0','0','0','0','0','|','0','0','0','0','0','0','0','0','0'},
			{'0','0','0','0','0','|','0','F','-','-','7','0','0','0','0'},
			{'0','0','0','0','0','L','-','J','0','0','|','0','0','0','0'},
			{'0','0','0','0','0','0','0','0','0','0','|','0','0','0','0'},
			{'0','0','0','0','0','0','0','0','0','0','L','-','-','7','0'},
			{'0','0','0','0','0','0','0','0','0','0','0','F','-','J','0'},
			{'0','0','0','0','0','0','0','0','0','0','0','|','0','0','0'},
			{'0','0','0','0','0','0','0','0','0','0','0','L','-','-','G'},
	};

	@FXML
	PipeGameDisplayer pipeGameDisplayer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pipeGameDisplayer.setPipeGameData(pipeGameData);

		pipeGameDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->pipeGameDisplayer.requestFocus());

		pipeGameDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				int r=pipeGameDisplayer.getcRow();
				int c=pipeGameDisplayer.getcCol();

				if(event.getCode() == KeyCode.UP){
					pipeGameDisplayer.setCharacterPosition(r-1, c);
				}
				if(event.getCode() == KeyCode.DOWN){
					pipeGameDisplayer.setCharacterPosition(r+1, c);
				}
				if(event.getCode() == KeyCode.RIGHT){
					pipeGameDisplayer.setCharacterPosition(r, c+1);
				}
				if(event.getCode() == KeyCode.LEFT){
					pipeGameDisplayer.setCharacterPosition(r, c-1);
				}
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

}
