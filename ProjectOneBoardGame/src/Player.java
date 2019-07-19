import java.text.DecimalFormat;
import java.util.ArrayList;

public class Player {
	
	public int currentScore;
	public int position;
	public int currentMoves;
	public String symbol;
	public int[] gamesWon;
	public ArrayList<Integer> moves1; // the moves for this player when there is 1 player in the game
	public ArrayList<Integer> moves2; // the moves for this player when there are 2 players in the game
	public ArrayList<Integer> moves3; // for 3 players in the game
	public ArrayList<Integer> moves4; // for 4 players in the game
	
	public Player(String character) { // constructor
		symbol = character;
		currentScore = 0;
		position = 0;
		currentMoves = 0;
		gamesWon = new int[4]; // each index represents the number of players in the game
		moves1 = new ArrayList<Integer>();
		moves2 = new ArrayList<Integer>();
		moves3 = new ArrayList<Integer>();
		moves4 = new ArrayList<Integer>();
	}
	
	public void addMoves(int numPlayers, int moves) {
		if (numPlayers == 1) {
			moves1.add(moves);
		} else if (numPlayers == 2) {
			moves2.add(moves);
		} else if (numPlayers == 3) {
			moves3.add(moves);
		} else {
			moves4.add(moves);
		}
	}
	
	public String getAverageMoves(int numPlayers) {
		double result = 0.0;
		if (numPlayers == 1) {
			double totalMoves = getTotalMoves(moves1);
			double games = gamesWon[numPlayers-1];
			result = totalMoves/games;
		} else if (numPlayers == 2) {
			double totalMoves = getTotalMoves(moves2);
			double games = gamesWon[numPlayers-1];
			result = totalMoves/games;
		} else if (numPlayers == 3) {
			double totalMoves = getTotalMoves(moves3);
			double games = gamesWon[numPlayers-1];
			result = totalMoves/games;
		} else {
			double totalMoves = getTotalMoves(moves4);
			double games = gamesWon[numPlayers-1];
			result = totalMoves/games;
		}
		return String.format("%.2f", result);
	}
	
	public int getTotalMoves(ArrayList<Integer> list) {
		int total = 0;
		for (int i = 0; i < list.size(); i++) {
			total += list.get(i);
		}
		return total;
	}
	
	public String getPercentWon(int numPlayers) {
		double numWon = gamesWon[numPlayers-1];
		double totalGames = 1000.00;
		return String.format("%.2f", (numWon/totalGames) * 100);
	}
	
	

}
