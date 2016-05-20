
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.awt.*;

import javax.swing.JFrame;
//---------------------------------------------------------------
/** Klasa okna g³ównego  */
public class MainWindow extends JFrame {
	/** Obiekt klasy ContentPane */
	private ContentPane cp;
	/** Obiekt klasy Board */
	private Board board;
	
	/** Konstruktor klasy */
	public MainWindow()
	{
		super("Sokoban");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(400,400));
		setMinimumSize(new Dimension(400, 400));
		setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		setLocation(500, 200);
		pack();
		
		cp = new ContentPane();
		setContentPane(cp);
		
		ConfigReader r = new ConfigReader();
		try
		{
		r.readConfiguration();
		}
		catch(IOException e){}
		board = new Board(0, r);
		
		MainWindowMenu menu = new MainWindowMenu();
		menu.addComponentListener(compLis);
		getContentPane().add(menu);
		
	}
	/** Obiekt klasy ComponentListener, nadzoruje zdarzenia */
	private ComponentListener compLis = new ComponentListener() {
		
		@Override
		public void componentShown(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void componentResized(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub
			board.setPreferredSize(new Dimension(board.whatWidth()*board.nOfLine(),board.whatHeight()*board.nOfColumns()));
			add(board);
			board.requestFocus();
			board.setVisible(true);

		}
	};

	
}
