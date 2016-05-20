import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

/** Klasa tworz¹ca menu g³ówne */
public class MainWindowMenu extends JPanel {
	private static final String CENTER_ALIGMENT = null;
	/** przyciski menu */
	private JButton startBut, playerBut, htpBut, hsBut, lvlBut;
	//-------------------------------------------------------------------------------------------------------
	/** Konstruktor klasy */
	MainWindowMenu()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(300, 300));
		setOpaque(false);
		initiateButtons (200, 200);
		addToPanel();
		add(Box.createGlue());
		
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda tworz¹ca uk³ad przycisków */
	private void initiateButtons(int x, int y)
	{
		createButtons();
		setButtonsMinimumSize(x, y);
		setButtonsMaximumSize(x, y);
		setButtonsAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(CENTER_ALIGNMENT);
		setButtonsActions();
		addButtonsListeners();
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda tworz¹ca konkretne przyciski menu */
	private void createButtons()
	{
		startBut = new JButton ("Start");
		playerBut = new JButton ("Select player");
		lvlBut = new JButton ("Choose level");
		hsBut = new JButton ("Higest Scores");
		htpBut = new JButton ("How to play");
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda ustalaj¹ca minimalny rozmiar wszystkich przycisków */
	private void setButtonsMinimumSize(int x, int y)
	{
		startBut.setMinimumSize(new Dimension(x, y));
		playerBut.setMinimumSize(new Dimension(x, y));
		lvlBut.setMinimumSize(new Dimension(x, y));
		hsBut.setMinimumSize(new Dimension(x, y));
		htpBut.setMinimumSize(new Dimension(x, y));
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda ustalaj¹ca maksymalny rozmiar wszystkich przycisków */
	private void setButtonsMaximumSize(int x, int y)
	{
		startBut.setMaximumSize(new Dimension(x, y));
		playerBut.setMaximumSize(new Dimension(x, y));
		lvlBut.setMaximumSize(new Dimension(x, y));
		hsBut.setMaximumSize(new Dimension(x, y));
		htpBut.setMaximumSize(new Dimension(x, y));
		//startBut.setMaximumSize(new Dimension(Integer.MAX_VALUE, startBut.getMinimumSize().height));
	}
	//-------------------------------------------------------------------------------------------------------
/** Metoda scalaj¹ca przyciski, tworz¹ca w oknie ca³y komponent menu */
	private void addToPanel()
	{
		add(Box.createGlue());
		add(startBut);
		add(Box.createGlue());
		add(playerBut);
		add(Box.createGlue());
		add(lvlBut);
		add(Box.createGlue());
		add(hsBut);
		add(Box.createGlue());
		add(htpBut);
		add(Box.createGlue());
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda ustalaj¹ca pozycje przycisków menu */
	private void setButtonsAlignmentX(float alignmentX)
	{
		startBut.setAlignmentX(CENTER_ALIGNMENT);
		playerBut.setAlignmentX(CENTER_ALIGNMENT);
		lvlBut.setAlignmentX(CENTER_ALIGNMENT);
		hsBut.setAlignmentX(CENTER_ALIGNMENT);
		htpBut.setAlignmentX(CENTER_ALIGNMENT);
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda ustalaj¹ca moment dzia³ania przycisku */
	private void setButtonsActions()
	{
		startBut.setMnemonic(KeyEvent.VK_D);
		startBut.setActionCommand("start");
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda oczekuj¹ca na rozpoczêcie akcji niczym widz w kinie Bollywood */
	private void addButtonsListeners()
	{
		startBut.addActionListener(al);
	}
	//-------------------------------------------------------------------------------------------------------
	/** Obiekt klasy ActionListener  */
	private ActionListener al = new ActionListener()
	{
		/** Funkcja opisuj¹ca zdarzenia po klikniêciu w poszczególne przyciski menu */
		public void actionPerformed(ActionEvent event)
		{
			if ("start".equals(event.getActionCommand()))
			{
	            MainWindowMenu.this.setVisible(false);
	            
			}
		}
	};
}
	//-------------------------------------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------------------------------------


