import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class LevelCreator extends Component {
	
	private BufferedImage wallImg, floorImg, boxImg, xImg;
	private String wallFilename = "wall.png";
	LevelCreator()
	{
		loadImage(wallImg, wallFilename);
		
		
	}
	private void loadImage(BufferedImage img, String imgFilename)
	{
		try
		{
			img = ImageIO.read(new File(imgFilename));
		}
		catch(IOException e)
		{
			System.out.print("IOException reading image");
		}
	}
	public void paint(Graphics g)
	{
		//super.paintComponent(g);
		g.drawImage(wallImg, 50, 50, null);
	}
	 public Dimension getPreferredSize(BufferedImage img) {
	        if (img == null) {
	             return new Dimension(100,100);
	        } else {
	           return new Dimension(img.getWidth(null), img.getHeight(null));
	       }
	    }
}
