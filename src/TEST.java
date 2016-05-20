import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.*;

public class TEST extends JPanel{

	private Image img;
	private String imageFileName;
	private static int cnt = 0;
	private int n;
	private static int N = 0;
	
	TEST(String imageFileName)
	{
		this.imageFileName = imageFileName;
		n = ++cnt;
		
		if(! new File(imageFileName).exists())
		{
			throw new IllegalArgumentException("Plik " + imageFileName + " nie istnieje.");
		}
	}
	public void addNotify()
	{
		super.addNotify();
		img = Toolkit.getDefaultToolkit().getImage(imageFileName);
		//System.out.println(img.getClass().getName());
			//System.out.println("addNotify, w = " + img.getWidth(this));
				
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("paint ! (n=" + n + ") (imageFileName=" + imageFileName + ") N=" + N++);
		try {
			g.drawImage(img, 0, 0, this);
		} catch (NullPointerException npe) {
			System.out.println("null w paint ! (n=" + n + ")");
		}
		setSize(img.getWidth(this), img.getHeight(this));
		System.out.println("paint, w = " + img.getWidth(this));
	}
}
