package Themes;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class ThemeInterpeter{

	public interface ImageMethod
	{
		String getImg();
	}

	private IDisplayTheme m_theme;

	Map<Character, ImageMethod> imageFuncs = new HashMap<>();
	Map<Character, Image> images = new HashMap<>();

	public ThemeInterpeter(IDisplayTheme a_theme)
	{
		m_theme = a_theme;

		imageFuncs.put('-', () -> m_theme.getHorizontal_Pipe());
		imageFuncs.put('|', () -> m_theme.getVertical_Pipe());
		imageFuncs.put('7', () -> m_theme.getPipe_7());
		imageFuncs.put('J', () -> m_theme.getPipe_J());
		imageFuncs.put('L', () -> m_theme.getPipe_L());
		imageFuncs.put('F', () -> m_theme.getPipe_F());
	}


	public Image getImage(char type)
	{
		if(!images.containsKey(type))
		{
			String path = imageFuncs.get(type).getImg();
			images.put(type, new Image(path));
		}
		return images.get(type);
	}

}
