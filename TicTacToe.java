import java.io.BufferedReader;
import java.io.InputStreamReader;

//If your text editor supports code folding for code blocks, please use it!
//Especially for folding parts of code within a function (e.g. fold an if-else).
//I think code folding is a very important feature for reading code. :)

public class TicTacToe
{
	
	/**
	 * User input function. Reads a string input until a newline character.
	 * @param text String input. The prompt to show when asking for an input.
	 * @return The text that the user entered.
	 * */
	public static String readString(String text)
	{
		String line;
		String result = "";		
		try
		{
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader input = new BufferedReader(isr);
		
			boolean success = false;
			
			while(!success)
			{
				try
				{
					System.out.print(text);
					line = input.readLine();
					
					success = true;
					result = line;
				}
				catch(Exception ex)
				{
					System.out.println("Invalid Input!");
				}
			}
		}
		catch(Exception e)
		{
			System.err.println("Error in Console I/O: " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * User input function. Reads an integer number. Used for the user entering the position of the marker.
	 * @param text String input. The prompt to show when asking for an input.
	 * @return The integer value of the user's input
	 * */
	public static int readInt(String text)
	{
		String line;
		int result = 0;		
		try
		{
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader input = new BufferedReader(isr);
		
			boolean success = false;
			
			while(!success)
			{
				try
				{
					System.out.print(text);
					line = input.readLine();
					line.trim();
					result= Integer.parseInt(line);
					success = true;
				}
				catch(Exception ex)
				{
					System.out.println("Invalid Input!");
				}
			}
		}
		catch(Exception e)
		{
			System.err.println("Error in Console I/O: " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * Prints the current input of the board. The board is always a square.
	 * <br>The numbering assumes the users' perspective. i.e. the first box is index 1, not 0.
	 * @param board The character array.
	 * @param size the size (width) of the array.
	 * */
	public static void printBoard(char[] board, int size)
	{

		System.out.println();
		
		for(int y = 0; y < size; y++) //vertically
		{
			int x = 0;
			String divider = "";
			for(; x < size-1; x++) //horizontally. size -1 to cater for the last column...
			{
				if(board[y*size + x] != 0) //print the character
					System.out.printf("%2s |", board[y*size + x]);
				else //print the box number, from the user's perspective.
					System.out.printf("%2s |", (y*size + x)+1);
				
				divider += "----";
			}
			
			
			// last column
			if (board[y * size + x] != 0) // print the character
			{
				System.out.printf("%2s", board[y * size + x]);
			}
			else // print the box number, from the user's perspective.
			{
				System.out.printf("%2s", (y * size + x)+1);
			}

			divider += "---"; //for the bottom divider
			
			if (y < size - 1)
			{
				System.out.println("\n" + divider);
			}
			else
			{
				System.out.println();
			}
			
		}
		
		System.out.println();
	}
	
	/**
	 * To determine if the last entered position has created a 3 in a row.
	 * @param board The character array.
	 * @param size The size (width) of the board. The board is assumed to always be a square.
	 * @param lastInputPos The last position that was inserted into the board. Remember to -1 from the user's input if the first box is labeled 1.
	 * @return boolean value if the last move is a winning move.
	 * */
	public static boolean evaluateBoard(char[] board, int size, int lastInputPos)
	{
		//in java, the array has a .length method. But in C++, you need to pass the size of the array in the function...
		//int fullSize = size*size; 
		
		boolean winner = false;
		//char marker = board[lastInputPos];
		int moduluo = lastInputPos % size;
		
		/**
		 * There are 4 directions to check.: 
		 * 1. left/right diagonal ( \ ) 
		 * 2. vertical 
		 * 3. right/left diagonal ( / ) 
		 * 4. horizontal
		 **/
		
		
		/**
		The general formula to find the position of adjacent TTT cell is as follows:
		
		 lastInputPos (lip)
		
		 -2				 -1				 0				 +1				 +2				<- moduluo relative to input position
		lip-(size*2+2)	|				|lip-(size*2)	|				|lip-(size*2-2)
		------------------------------------------------------------------------------
						|lip-(size+1)	|lip-size		|lip-(size-1)	|
		------------------------------------------------------------------------------
		lip-2 			|lip-1 			|lip			|lip+1			|lip+2
		------------------------------------------------------------------------------
						|lip+(size-1)	|lip+size		|lip+(size+1)	|
		------------------------------------------------------------------------------
		lip+(size*2-2)	|				|lip+(size*2)	|				|lip+(size*2+2)
		
		Note that the last input position may be near an edge, which can cause an out-of-bounds error.
		
		Note the moduluo check: while array position may exist in a NxN grid, it may not reside in the
		intended row/column. The moduluo is to make sure that the array position is in the intended grid
		position.
		 */
		
		/**
		 * 1| | | | 
		 * ---------
		 *  |2| | | 
		 * ---------
		 *  | |3| | 
		 * ---------
		 *  | | |4| 
		 * ---------
		 *  | | | |5
		 *  
		 *  checks in this order: (1 2 3), (2 3 4), (3 4 5)
		 **/
		//check for 1. left/right diagonal ( \ ) starting from top left
		if(!winner)
		{
			/**
			 * first 'if' must check to see if the column is within range of the array, and to check if the index
			 * resides in the intended row/column.
			 * 
			 * second 'if' checks for the characters.
			 * 
			 * rinse and repeat
			 * */
			
			//check array boundary and row/column
			//System.out.println("left/right diagonal ( \\ ) ");
			if (lastInputPos - (size * 2 + 2) >= 0 
					&& lastInputPos - (size + 1) >= 0 
					&& (lastInputPos - (size * 2 + 2)) % size == (moduluo-2)
					&& (lastInputPos - (size + 1)) % size == (moduluo-1))
			{
				//all the debug console prints assume first box is index 0
				//System.out.printf("(1) Comparing positions: %d, %d, %d\n", lastInputPos - (size * 2 + 2),
				//		lastInputPos - (size + 1), lastInputPos);
				
				//second 'if' checks for the characters.
				if ((board[lastInputPos - (size * 2 + 2)] == board[lastInputPos - (size + 1)])
						&& (board[lastInputPos - (size + 1)] == board[lastInputPos]))
				{
					winner = true;
				}
			}

			if (lastInputPos - (size + 1) >= 0
					&& lastInputPos + (size + 1) < board.length 
					&& (lastInputPos - (size + 1)) % size == (moduluo - 1)
					&& (lastInputPos + (size + 1)) % size == (moduluo + 1) 
					&& !winner)
			{
				//System.out.printf("(2) Comparing positions: %d, %d, %d\n", lastInputPos - (size + 1), lastInputPos,
				//		lastInputPos + (size + 1));
				if ((board[lastInputPos - (size + 1)] == board[lastInputPos])
						&& (board[lastInputPos] == board[lastInputPos + (size + 1)]))
				{
					winner = true;
				}
			}
			
			if ((lastInputPos + (size + 1) < board.length) 
					&& lastInputPos + (size * 2 + 2) < board.length
					&& (lastInputPos + (size + 1)) % size == (moduluo + 1)
					&& (lastInputPos + (size * 2 + 2)) % size == (moduluo + 2)
					&& !winner)
			{
				//System.out.printf("(3) Comparing positions: %d, %d, %d\n", lastInputPos, lastInputPos + (size + 1),
				//		lastInputPos + (size * 2 + 2));
				if ((board[lastInputPos + (size + 1)] == board[lastInputPos])
						&& (board[lastInputPos + (size + 1)] == board[lastInputPos + (size * 2 + 2)]))
				{
					winner = true;
				}
			}

		}
		
		/**
		 *  | |1| | 
		 * ---------
		 *  | |2| | 
		 * ---------
		 *  | |3| | 
		 * ---------
		 *  | |4| | 
		 * ---------
		 *  | |5| | 
		 *  
		 *  checks in this order: (1 2 3), (2 3 4), (3 4 5)
		 **/
		//check for 2. vertical 
		if(!winner)
		{
			//working!
		
			//System.out.println("Checking Vertical Condition");
			if (lastInputPos - (size * 2) >= 0 
					&& (lastInputPos - size) >= 0
					&& (lastInputPos - (size * 2)) % size == moduluo
					&& (lastInputPos - size) % size == moduluo)
			{
				//System.out.printf("(1) Comparing positions: %d, %d, %d\n", lastInputPos - (size * 2),
				//		lastInputPos - size, lastInputPos);
				if ((board[lastInputPos - (size * 2)] == board[lastInputPos - (size)])
						&& (board[lastInputPos - (size)] == board[lastInputPos]))
				{
					winner = true;
				}
			}

			if (lastInputPos - (size) >= 0
					&& lastInputPos + (size) < board.length
					&& (lastInputPos - size) % size == moduluo 
					&& (lastInputPos + size) % size == moduluo
					&& !winner)
			{
				//System.out.printf("(2) Comparing positions: %d, %d, %d\n", lastInputPos - (size), lastInputPos,
				//		lastInputPos + (size));
				if ((board[lastInputPos - (size)] == board[lastInputPos])
						&& (board[lastInputPos] == board[lastInputPos + (size)]))
				{
					winner = true;
				}
			}

			if (lastInputPos + (size) < board.length 
					&& lastInputPos + (size * 2) < board.length
					&& (lastInputPos + size) % size == moduluo 
					&& (lastInputPos + (size * 2)) % size == moduluo 
					&& !winner)
			{
				//System.out.printf("(3) Comparing positions: %d, %d, %d\n", lastInputPos, lastInputPos + (size),
				//		lastInputPos + (size * 2));
				if ((board[lastInputPos + (size)] == board[lastInputPos])
						&& (board[lastInputPos + (size)] == board[lastInputPos + (size * 2)]))
				{
					winner = true;
				}
			}
			
		}
		
		/**
		 *  | | | |1
		 * ---------
		 *  | | |2| 
		 * ---------
		 *  | |3| | 
		 * ---------
		 *  |4| | | 
		 * ---------
		 * 5| | | | 
		 *  
		 *  checks in this order: (1 2 3), (2 3 4), (3 4 5)
		 **/
		//check for 3. right/left diagonal ( / ) 
		if(!winner)
		{

			//System.out.println("Checking for right/left diagonal ( / )");
			if (lastInputPos - (size * 2 - 2) >= 0 
					&& (lastInputPos - (size - 1)) >= 0
					&& (lastInputPos - (size * 2 - 2))% size == (moduluo + 2)
					&& (lastInputPos - (size - 1))% size == (moduluo + 1))
			{
				//System.out.printf("(1) Comparing positions: %d, %d, %d\n", lastInputPos - (size * 2 - 2),
				//		lastInputPos - (size - 1), lastInputPos);
				if ((board[lastInputPos - (size * 2 - 2)] == board[lastInputPos - (size - 1)])
						&& (board[lastInputPos - (size - 1)] == board[lastInputPos]))
				{
					winner = true;
				}
			}
			
			//System.out.printf("I should be checking: %d, %d, %d\n", lastInputPos - (size - 1), lastInputPos, lastInputPos + (size - 1));
			if ((lastInputPos - (size - 1)) >= 0 
					&& (lastInputPos + (size - 1)) < board.length 
					&& (lastInputPos - (size - 1))% size == (moduluo + 1) 
					&& (lastInputPos + (size - 1))% size == (moduluo - 1)
					&& !winner)
			{
				//System.out.printf("(2) Comparing positions: %d, %d, %d\n", (lastInputPos - (size - 1)),
				//		lastInputPos, lastInputPos + (size - 1));
				if ((board[lastInputPos - (size - 1)] == board[lastInputPos])
						&& (board[lastInputPos] == board[lastInputPos + (size - 1)]))
				{
					winner = true;
				}
			}
			
			if ((lastInputPos + (size - 1)) < board.length
					&& (lastInputPos + (size * 2 - 2)) < board.length
					&& (lastInputPos + (size * 2 - 2)) % size == (moduluo - 2)
					&& (lastInputPos + (size - 1)) % size == (moduluo - 1) 
					&& !winner)
			{
				//System.out.printf("(3) Comparing positions: %d, %d, %d\n", lastInputPos,
				//		lastInputPos + (size - 1), lastInputPos + (size * 2 - 2));
				if ((board[lastInputPos + (size - 1)] == board[lastInputPos])
						&& (board[lastInputPos + (size - 1)] == board[lastInputPos + (size * 2 - 2)]))
				{
					winner = true;
				}
			}

		}
		
		/**
		 *  | | | | 
		 * ---------
		 *  | | | | 
		 * ---------
		 * 1|2|3|4|5
		 * ---------
		 *  | | | | 
		 * ---------
		 *  | | | | 
		 *  
		 *  checks in this order: (1 2 3), (2 3 4), (3 4 5)
		 **/
		//check for 4. horizontal
		if(!winner)
		{
			//check from left to right
			//System.out.println("Checking for 4. horizontal (left -> right)");
			if (lastInputPos-2 >= 0
					&& lastInputPos-1 >= 0
					&& (lastInputPos-2)%size == (moduluo-2)
					&& (lastInputPos-1)%size == (moduluo-1))
			{
				//System.out.printf("(1) Comparing positions: %d, %d, %d\n", lastInputPos-2, lastInputPos-1, lastInputPos);
				if ((board[lastInputPos-2] == board[lastInputPos-1])
						&& (board[lastInputPos-1] == board[lastInputPos]))
				{
					winner = true;
				}
			}
			
			if (lastInputPos-1 >= 0
					&& lastInputPos+1 < board.length
					&& (lastInputPos-1)%size == (moduluo-1)
					&& (lastInputPos+1)%size == (moduluo+1)
					&& !winner)
			{
				//System.out.printf("(2) Comparing positions: %d, %d, %d\n",  lastInputPos-1, lastInputPos, lastInputPos+1);
				if ((board[lastInputPos-1] == board[lastInputPos])
						&& (board[lastInputPos] == board[lastInputPos+1]))
				{
					winner = true;
				}
			}
			
			if (lastInputPos+1 < board.length
					&& lastInputPos+2 < board.length
					&& (lastInputPos+1)%size == (moduluo+1)
					&& (lastInputPos+2)%size == (moduluo+2)
					&& !winner)
			{
				//System.out.printf("(3) Comparing positions: %d, %d, %d\n",  lastInputPos, lastInputPos+1, lastInputPos+2);
				if ((board[lastInputPos] == board[lastInputPos+1])
						&& (board[lastInputPos+1] == board[lastInputPos+2]))
				{
					winner = true;
				}
			}
		}
		
		return winner;
	}
	
	public static void main(String[] args)
	{
		/**The tic tac toe board. Remember to initialize*/
		char[] board;
		
		/**Name of player A*/
		String playerA;
		
		/**Name of player B*/
		String playerB;
		
		/**The size of the board*/
		int boardSize;
		
		/**Check the board for move number. Check for 3 in a row only on move 5 onwards.
		 * Also, if moveCounter is the same as the size of the board, the game might be a draw.*/
		int moveCounter = 0;
		
		/**To alternate between player A and player B. True for player A*/
		boolean playerMove = false;
		
		/**Mark as true if there is a winner.*/
		boolean winner = false;
		
		do
		{
			boardSize = readInt("Please enter the size of the board (size must be 3 or more, 0 to quit):\r\n>> ");
		}
		while(boardSize >= 1 && boardSize <=2);
		
		
		if(boardSize <= 0)
			return;
			
		board = new char[boardSize*boardSize];
		
		System.out.println("The size of the board is " + board.length);
		playerA = readString("Enter name for Player 1:\r\n >> ");
		playerB = readString("Enter name for Player 2:\r\n >> ");
		
		printBoard(board, boardSize);
		
		while(!winner && (moveCounter < (boardSize*boardSize)) )
		{
			//player's selected position
			int newMarker = 0;
			playerMove = !playerMove;
			
			while(true) 
			{
				if(playerMove) //true for player A
				{
					newMarker = readInt(playerA + ", choose a box to place an 'x' into:\r\n>> ");
				}
				else //false for player B
				{
					newMarker = readInt(playerB + ", choose a box to place an 'o' into:\r\n>> ");
				}
				
				if(newMarker <= 0 || newMarker > (boardSize*boardSize))
				{
					System.out.println("Invalid box number.");
				}
				else if(board[newMarker-1] != 0)
				{
					System.out.println("That box is already taken.");
				}
				else
				{
					//Remember that the user input is based on the user's perspective.
					//so -1 from the user input to account for the array's starting index
					//of 0.
					//use ternary to be concise
					//true for player A
					board[newMarker-1] = playerMove ? 'x' : 'o';

					break;
				}
			}

			printBoard(board, boardSize);
			moveCounter++;
			winner = evaluateBoard(board, boardSize, newMarker-1);
		}
		
		
		if(winner)
		{
			System.out.println("Congratulations " + (playerMove==true?playerA:playerB) + "! You have won!");
		}
		else
		{
			System.out.println("The game has drawn.");
		}

	}

}
