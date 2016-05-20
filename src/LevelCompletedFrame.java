import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** Klasa reprezentujaca ramke wyswietlajaca komunikat po ukonczeniu poziomu */
public class LevelCompletedFrame extends JPanel {

	/**Konstruktor 
	 * 
	 * @param lvl numer poziomu
	 */
	LevelCompletedFrame(int lvl)
	{
		JInternalFrame internalFrame = new JInternalFrame(null, false, true, false);
		internalFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
		JOptionPane.showMessageDialog(internalFrame, "Level" + lvl + "completed");
	}
}
