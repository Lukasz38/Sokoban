
public class Configuration {

	private String mainbackground;
	private String box, destination, floor, wall, man, bonus;
	private String[] names;  //tablicy imion wszystkich graczy
	private int nOfPlayers;//ile graczy
	private int[] maxUnlockedLevel;
	private int[] scores1; //pkt za 1 poziom
	private int[] scores2; //pkt za 2 poziom
	public LevelsReader lr = new LevelsReader("levels.txt");
}
