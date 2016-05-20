import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;

public class Wall extends JPanel {
	private Image img;
	Wall()
	{
		
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 100, 50, null);
	}
	public void addNotify()
	{
		super.addNotify();
		img = Toolkit.getDefaultToolkit().getImage("wall.png");
	}
	
}
