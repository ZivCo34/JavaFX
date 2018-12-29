package Themes;

public class BlueTheme implements IDisplayTheme	{

	@Override
	public String getHorizontal_Pipe() {
		return "./Resources/BlueTheme/Horizontal_Pipe.jpg";
	}

	@Override
	public String getVertical_Pipe() {
		return "./Resources/BlueTheme/Vertical_Pipe.jpg";
	}

	@Override
	public String getPipe_7() {
		return "./Resources/BlueTheme/Pipe_7.jpg";
	}

	@Override
	public String getPipe_J() {
		return "./Resources/BlueTheme/Pipe_J.jpg";
	}

	@Override
	public String getPipe_L() {
		return "./Resources/BlueTheme/Pipe_L.jpg";
	}

	@Override
	public String getPipe_F() {
		return "./Resources/BlueTheme/Pipe_F.jpg";
	}

	@Override
	public String getStart() {
		return "./Resources/BlueTheme/Start.jpg";
	}

	@Override
	public String getGoal() {
		return "./Resources/BlueTheme/End.png";
	}
	@Override
	public String getBackground() {
		return "./Resources/BlueTheme/Background.jpg";
	}
	@Override
	public String getMusic() {
		return "./Resources/BlueTheme/HarryPotter.mp4";
	}
	
	@Override
	public String getName() {
		return "Blue";
	}

}
