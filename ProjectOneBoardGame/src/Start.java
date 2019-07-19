import java.util.Random;

public class Start {

	static String[] spaces = {"START", "5", "10", "8", "10", "7", "5", "9", "10", "6", "7", "10", "6", "5", "8", "9", "5", "10", "5", "9", "6", "8", "7", "10", "6", "8", "END"};

	static int[] index = new int[spaces.length];

	///////////////////// MAIN START /////////////////////

	public static void main(String[] args) {

		for (int i = 0; i < index.length; i++) { // position index for each space
			index[i] = i;
		}

		// set up game board
		DoublyLinkedList gameBoard = makeGameBoard();		

		// instantiate players
		Player a = new Player("A");
		Player b = new Player("B");
		Player c = new Player("C");
		Player d = new Player("D");

		Player[] playerIndex = {a, b, c, d};

		// loop for number of players
		for (int i = 1; i <= 4; i++) { // i = the number of players

			// loop for 1000 games
			for (int j = 1; j <= 1000; j++) {
				// System.out.println("GAME NUMBER " + j);

				// reset all game variables
				boolean gameHasEnded = false;
				int playerTurn = 0;

				a.position = 0;
				b.position = 0;
				c.position = 0;
				d.position = 0;

				a.currentScore = 0;
				b.currentScore = 0;
				c.currentScore = 0;
				d.currentScore = 0;
				
				a.currentMoves = 0;
				b.currentMoves = 0;
				c.currentMoves = 0;
				d.currentMoves = 0;

				while (!gameHasEnded) {

					Player currentPlayer = playerIndex[playerTurn];

					int movement = rollDie();

					currentPlayer.position += movement;

					// if the player has reached the end
					if (currentPlayer.position >= 26) {

						currentPlayer.position = 26; // just in case they went further than the end
						
						currentPlayer.currentMoves++;
						
						// if the player has enough points to win
						if (currentPlayer.currentScore >= 44) {
							
							currentPlayer.addMoves(i, currentPlayer.currentMoves); // store how many moves were used to win
							currentPlayer.gamesWon[i-1]++; // add 1 to the number of games won
							gameHasEnded = true; // end the game loop

						} else { // if they don't have enough points
							
							currentPlayer.position = 0;
						
						}
						
					} else {
						
						boolean occupiedSpace = false;
						int tempPlayerCounter = 0;
						
						for (int l = 0; l < i; l++) { // loop through the number of players

							// if it's not the current player
							if (!(playerIndex[tempPlayerCounter].equals(currentPlayer))) {

								// if the position of the other player matches the position of the current player
								if ((playerIndex[tempPlayerCounter].position == currentPlayer.position)) {

									// space is occupied
									occupiedSpace = true;
									playerIndex[tempPlayerCounter].position -= 7;

									// if they go back past the start
									if (playerIndex[tempPlayerCounter].position < 0) {
										playerIndex[tempPlayerCounter].position = 0;
									}

									// get points on the space the current player landed
									currentPlayer.currentScore += getPoints(currentPlayer.position);
									
									currentPlayer.currentMoves++;


								}
							}

							tempPlayerCounter++;
						}

						// space is unoccupied
						if (occupiedSpace == false) {

							currentPlayer.currentScore += getPoints(currentPlayer.position);
							currentPlayer.currentMoves++;

						}




					}









					// change whose turn it is
					if (i == 2) {

						if (playerTurn == 1) {
							playerTurn = 0;
						} else {
							playerTurn++;
						}

					} else if (i == 3) {

						if (playerTurn == 2) {
							playerTurn = 0;
						} else {
							playerTurn++;
						}

					} else if (i == 4) {

						if (playerTurn == 3) {
							playerTurn = 0;
						} else {
							playerTurn++;
						}

					}

				}

				// print out game board every 100 games
				if (j == 1) {
					System.out.println("GAME NUMBER " + j + " WITH " + i + " PLAYERS");
					printGameBoard(gameBoard, i, playerIndex);
				} else if ((j + "").length() == 3) {
					if (((j + "").substring(1,3)).equals("01")) {
						System.out.println("GAME NUMBER " + j + " WITH " + i + " PLAYERS");
						printGameBoard(gameBoard, i, playerIndex);
					}
				}



			}


		}
		
		printResults(playerIndex);
	}

	///////////////////// MAIN END /////////////////////








	public static int getPoints(int p) {

		DoublyLinkedList board = makeGameBoard();

		for (int i = 0; i < index.length; i++) {
			if (p == index[i]) {
				
				return Integer.parseInt(board.first().toString());
				
			} else {
				
				board.removeFirst();
				
			}
		}

		return 0;

	}

	public static int rollDie() {
		Random die = new Random();
		return die.nextInt(6) + 1;
	}

	public static void printGameBoard(DoublyLinkedList list, int numPlayers, Player[] playerIndex) {

		int tileNum = 0;
		
		DoublyLinkedList tempGameBoard = makeGameBoard(); // make new gameBoard that I can modify
		
		String dashes = "";
		String firstRow = "";
		String secondRow = "";
		String thirdRow = "";
		
		for(int i = 0; i < 10; i++){ // first row of numbers

			firstRow += "|";

			String toBePrinted = tempGameBoard.first().toString();
			if (toBePrinted.length() == 1) {
				toBePrinted = "0" + toBePrinted;
			}
			for (int j = 0; j < numPlayers; j++) {
				if (playerIndex[j].position == tileNum) {
					toBePrinted += "[" + playerIndex[j].symbol + "]";
				}
			}
			toBePrinted = " " + toBePrinted + " ";
			firstRow += toBePrinted;
			tempGameBoard.removeFirst();

			if (i == 9) {
				firstRow += "|"; //  the last pipe symbol of the row
			}

			tileNum++;
		}

		for(int i = 0; i < 7; i++) { // second row of numbers

			secondRow += "|";

			String toBePrinted = tempGameBoard.first().toString();
			if (toBePrinted.length() == 1) {
				toBePrinted = "0" + toBePrinted;
			}
			for (int j = 0; j < numPlayers; j++) {
				if (playerIndex[j].position == tileNum) {
					toBePrinted += "[" + playerIndex[j].symbol + "]";
				}
			}
			toBePrinted = " " + toBePrinted + " ";
			secondRow += toBePrinted;
			tempGameBoard.removeFirst();

			if (i == 6) {
				secondRow += "|"; //  the last pipe symbol of the row
			}

			tileNum++;
		}

		for(int i = 0; i < 10; i++){

			thirdRow += "|";

			String toBePrinted = tempGameBoard.first().toString();
			if (toBePrinted.length() == 1) {
				toBePrinted = "0" + toBePrinted;
			}
			for (int j = 0; j < numPlayers; j++) {
				if (playerIndex[j].position == tileNum) {
					toBePrinted += "[" + playerIndex[j].symbol + "]";
				}
			}
			toBePrinted = " " + toBePrinted + " ";
			thirdRow += toBePrinted;
			tempGameBoard.removeFirst();

			if (i == 9) {
				thirdRow += "|"; //  the last pipe symbol of the row
			}

			tileNum++;
		}
		
		// time to format dashes and print everything out
		
		for (int i = 0; i < firstRow.length(); i++) {
			dashes += "-";
		}
		System.out.println(dashes);
		System.out.println(firstRow);
		System.out.println(dashes);
		System.out.println(secondRow);
		
		dashes = "";
		for (int i = 0; i < thirdRow.length(); i++) {
			dashes += "-";
		}
		
		System.out.println(dashes);
		System.out.println(thirdRow);
		System.out.println(dashes);

	}
	
	public static void printResults(Player[] playerIndex) { // print the results table (inefficient, could be made smaller)
		System.out.println();
		System.out.println("RESULTS:"); // title
		
		// first row
		String firstLine = "| Players In Game | Player A Avg. Moves/% Won | Player B Avg. Moves/% Won | Player C Avg. Moves/% Won | Player D Avg. Moves/% Won |";
		for (int i = 0; i < firstLine.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
		System.out.println(firstLine);
		for (int i = 0; i < firstLine.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
		
		// second row - 1 player
		System.out.print("|        A        |       " + playerIndex[0].getAverageMoves(1) + " / " + playerIndex[0].getPercentWon(1) + "       |");
		System.out.println(" X X X X X X X X X X X X X | X X X X X X X X X X X X X | X X X X X X X X X X X X X |");
		
		for (int i = 0; i < firstLine.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
		
		// third row - 2 players
		System.out.print("|       A,B       |");
		System.out.print("       " + playerIndex[0].getAverageMoves(2) + " / " + playerIndex[0].getPercentWon(2) + "        |");
		System.out.print("       " + playerIndex[1].getAverageMoves(2) + " / " + playerIndex[1].getPercentWon(2) + "        |");
		System.out.println(" X X X X X X X X X X X X X | X X X X X X X X X X X X X |");
		
		for (int i = 0; i < firstLine.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
		
		// fourth row - 3 players
		System.out.print("|      A,B,C      |");
		System.out.print("       " + playerIndex[0].getAverageMoves(3) + " / " + playerIndex[0].getPercentWon(3) + "        |");
		System.out.print("       " + playerIndex[1].getAverageMoves(3) + " / " + playerIndex[1].getPercentWon(3) + "        |");
		System.out.print("       " + playerIndex[2].getAverageMoves(3) + " / " + playerIndex[2].getPercentWon(3) + "        |");
		System.out.println(" X X X X X X X X X X X X X |");
		
		for (int i = 0; i < firstLine.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
		
		// fifth row - 4 players
		System.out.print("|     A,B,C,D     |");
		System.out.print("       " + playerIndex[0].getAverageMoves(4) + " / " + playerIndex[0].getPercentWon(4) + "        |");
		System.out.print("       " + playerIndex[1].getAverageMoves(4) + " / " + playerIndex[1].getPercentWon(4) + "        |");
		System.out.print("       " + playerIndex[2].getAverageMoves(4) + " / " + playerIndex[2].getPercentWon(4) + "        |");
		System.out.print("       " + playerIndex[3].getAverageMoves(4) + " / " + playerIndex[3].getPercentWon(4) + "        |");
		System.out.println();
		
		for (int i = 0; i < firstLine.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
		
	}

	public static DoublyLinkedList makeGameBoard() {

		DoublyLinkedList gameBoard = new DoublyLinkedList();

		for (int i = 0; i < spaces.length; i++) {
			gameBoard.addLast(spaces[i]);
		}

		return gameBoard;

	}

}
