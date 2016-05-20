import java.io.IOException;
import java.util.Vector;

public class Player {
	
	private int id;
	private String name;
	private int[] scores;
	private int totalScore;
	private int maxUnlocked; //level
	ConfigReader con;
	
	Player(int id) throws IOException
	{
		con = new ConfigReader();
		con.readConfiguration();
		name = con.getName(id-1);		
		maxUnlocked = con.getUnlockedLevel(id-1);
		scores = con.getScores(id);
		for(int o=0; o<scores.length; o++)
		{
			totalScore+= scores[o];
		}
		 
		
	}

//==testowanie====================================================
/*	public static void main(String[] args) throws IOException
	{
		Player gracz = new Player(1);
		Player gracz2 = new Player(2);
		System.out.println(gracz.name);
		System.out.println(gracz.scores[0]);
		System.out.println(gracz2.scores[1]);
		System.out.println(gracz.totalScore);
		//System.out.println("score2 dla gracz2:" + gracz2.scoreSecond);
		//System.out.println("total dla 1:" + gracz.totalScore);
		//System.out.println("ulocked da 2:" + gracz2.maxUnlocked);
		
	} */
}
