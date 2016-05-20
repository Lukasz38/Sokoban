import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/** Klasa odpowiadaj¹ca za t³o menu g³ównego */
public class ContentPane extends JPanel {
	
		ConfigReader c;
	/** obraz t³a */
	private Image backgroundImage;
	/** nazwa obrazu t³a */
	private String backgroundFileName;
	/** stan wczytania */
	private boolean loaded = false;
	//-------------------------------------------------------------------------------------------------------
	/** Konstruktor klasy */
	ContentPane()
	{
		c = new ConfigReader();
		try
		{
		c.readConfiguration(); 
		}
		catch(IOException e){}
		backgroundFileName = c.getMainPanel();
		loadImage(backgroundFileName);
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda rysuj¹ca */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(backgroundImage != null && loaded)
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		else
			g.drawString("Brak obrazka", 0, 0);
		
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda ³aduj¹ca obrazek */
	private void loadImage(String imageFileName)
	{
		if(! new File(imageFileName).exists())
		{
			throw new IllegalArgumentException("Plik " + imageFileName + " nie istnieje.");
		}
		backgroundImage = Toolkit.getDefaultToolkit().getImage(imageFileName);
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(backgroundImage, 1);
		try{
			mt.waitForID(1);
		}catch(InterruptedException ie){
		}
		int width = backgroundImage.getWidth(this);
	    int height = backgroundImage.getHeight(this); 
	    if (width != -1 && width != 0 && height != -1 && height != 0)
	    {
	    	loaded = true;
	    	setPreferredSize(new Dimension(width, height));
	    }else
	    {
	    	System.out.print("Blad oczytu obrazka lub obrazek ma wymiary 0,0");
	    	setPreferredSize(new Dimension(200, 200));
	    	setBackground(Color.GREEN);
	    }
	}
}
