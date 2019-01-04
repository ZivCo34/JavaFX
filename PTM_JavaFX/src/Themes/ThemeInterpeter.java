

/*    THEME INTERPETER    */


package Themes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ThemeInterpeter {

	public class ImageURL{
		String pathURL;

		public ImageURL(String s){
			pathURL=s;
		}

		public String getPathURL(){
			return pathURL;
		}
	};

	IDisplayTheme my_theme;
	Map<Character,ImageURL> imagesPath = new HashMap<>();
	Map<Character,Image> images = new HashMap<>();
	MediaPlayer mp;

	public ThemeInterpeter(IDisplayTheme a_theme){
		this.my_theme=a_theme;

		imagesPath.put('m', new ImageURL(my_theme.getMusic()));
		imagesPath.put('-', new ImageURL(my_theme.getHorizontal_Pipe()));
		imagesPath.put('|', new ImageURL(my_theme.getVertical_Pipe()));
		imagesPath.put('7', new ImageURL(my_theme.getPipe_7()));
		imagesPath.put('J', new ImageURL(my_theme.getPipe_J()));
		imagesPath.put('L', new ImageURL(my_theme.getPipe_L()));
		imagesPath.put('F', new ImageURL(my_theme.getPipe_F()));
		imagesPath.put('s', new ImageURL(my_theme.getStart()));
		imagesPath.put('g', new ImageURL(my_theme.getGoal()));

		this.playMusic();
	}

	public Image getImage(char type){
		if(!images.containsKey(type)){
			String path = imagesPath.get(type).getPathURL();
			Image a= null;
			try {
				a = new Image(new FileInputStream(path));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			images.put(type, a);
		}
		return images.get(type);
	}

	public void playMusic(){
		if(mp!=null){
			mp.stop();
		}
		String musicFile = my_theme.getMusic();
		Media music = new Media(new File(musicFile).toURI().toString());
		mp = new MediaPlayer(music);
		mp.play();
	}

	public void stopMusic() {
		mp.stop();
	}
	public String getThemeName() {
		return my_theme.getName();
	}
}