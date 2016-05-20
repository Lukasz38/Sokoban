
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Demonstracja wyœwietlania obrazków.
 * @author Krystian Ignasiak
 */
@SuppressWarnings("serial")
public class GetImage extends Frame {
	/**
	 * Obrazek.
	 */
	private Image img;

	/**
	 * Nazwa pliku z obrazkiem.
	 */
	private String imageFileName;

	/**
	 * Licznik pokazanych obrazków - licznik okien.
	 */
	private static int cnt = 0;
	
	/**
	 * Numer tego obrazka. 
	 */
	private int n;
	
	private static int N = 0;
	
	/**
	 * Utworzenie ramki z obrazkiem.
	 * @param title tytu³ okna.
	 * @param imageFileName nazwa pliku dyskowego.
	 */
	GetImage(String title, String imageFileName) {
		super(title);
		this.imageFileName = imageFileName;
		n = ++cnt;

		if (! new File(imageFileName).exists()) {
			throw new IllegalArgumentException("Plik " + imageFileName + " nie istnieje.");
		}
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				setVisible(false);
				if (--cnt == 0) {
					System.exit(0);
				}
			}
		});
		
		this.setMinimumSize(new Dimension(300, 300));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	}

	public void addNotify() {
		super.addNotify();
		img = Toolkit.getDefaultToolkit().getImage(imageFileName);
		//System.out.println(img.getClass().getName());
		System.out.println("addNotify, w = " + img.getWidth(this));
	}

	public void paint(Graphics g) {
		System.out.println("paint ! (n=" + n + ") (imageFileName=" + imageFileName + ") N=" + N++);
		try {
			g.drawImage(img, 0, 0, this);
		} catch (NullPointerException npe) {
			System.out.println("null w paint ! (n=" + n + ")");
		}
		setSize(img.getWidth(this), img.getHeight(this));
		System.out.println("paint, w = " + img.getWidth(this));
	}

	/**
	 * Test rysowania obrazków.
	 * @param args nieu¿ywane.
	 */
//	public static void main(String[] args) {
//		new GetImage("GetImage - GIF", "wall.png");
////		new GetImage("GetImage - JPG", "1.jpg");
////		new GetImage("GetImage - PNG", "1.png");
//		System.out.println(new File(".").getAbsolutePath());
//	}
}
