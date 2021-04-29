package Assignment2;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Game {
	private static Scanner input;

	// game constants
	private static final int SIZE = 7;
	private static int[] list = null;
	private static int l = 0, r = SIZE - 1; // pointers to edges of the list
	private static boolean running = true;
	private static int[][] bestMoves = null;
	
	// game variables
	private static int playerSum = 0, computerSum = 0;
	
	public static void main(String[] args) {
		input = new Scanner(System.in);
		char c = ' ';
		initList();
		bestMoves = new int[SIZE][SIZE];
		calculateBestMoves();
		while (running) {
			boolean shouldStop = false;
			
			while (!shouldStop) {
				showList();
				shouldStop = turn();
				System.out.println("l: " + l + " r: " + r);
				System.out.println("---------------");
			}
			checkWinner();
			restart();
			
		}

		input.close();
	}
	
	public static void initList() {
		// method to initialize list and fill in random numbers
		list = new int[SIZE];
		for (int i = 0; i < list.length; i++) {
			list[i] = (int)(Math.random()* 3) + 1;
		}
	}
	
	public static void showList() {
		System.out.println("Current list: ");
		for (int i = l; i <= r; i++) {
			System.out.print(list[i] + " ");
		}
		System.out.println();
	}

	public static boolean turn() {
		// player's turn
		if (l >= r) {
			// last item
			System.out.printf("Last item %d chosen automatically", list[l]);
			playerSum += list[l];
			return true;
		}
		System.out.printf("Your options are %d (type 'l') or %d (type 'r')", list[l], list[r]);
		System.out.printf("\nBest choice is: %s \n", bestMoves[l][r] == l ? "l" : "r"); // pull best move from table
		System.out.print("You choose: ");
		boolean success = false;
		char choice = ' ';
		while (!success) {
			try {
				choice = input.next().toLowerCase().charAt(0);
				success = true;
			} catch (InputMismatchException e) {
				System.out.println("Please enter 'l' for left or 'r' for right");
			}
		}
		if (choice == 'r')
			playerSum += list[r--]; // add to playerSum and decrement r
		else
			playerSum += list[l++]; // add to playerSum and increment l
		
		// show list between turns
		showList();
		
		// computer's turn
		int compChoice = bestMoves[l][r];
		System.out.printf("Computer chooses: %d\n" , compChoice);
		
		computerSum += list[compChoice]; // add to computer sum
		
		if (compChoice == l)
			l++;
		else
			r--;
		
		return false;
	}
	
	public static void compTurn() {
		// method to compute the computer's turn
			
		int choice = bestMoves[l][r];
		System.out.printf("Computer chooses: %d\n" , choice);
		
		computerSum += list[choice]; // add to computer sum
		
		if (choice == l)
			l++;
		else
			r--;
	}
	
	public static void playerTurn() {
		if (l >= r) {
			// last item
			System.out.printf("Last item %d chosen automatically", list[l]);
			playerSum += list[l];
			return;
		}
		System.out.printf("Your options are %d (type 'l') or %d (type 'r')", list[l], list[r]);
		System.out.printf("\nBest choice is: %s \n", bestMoves[l][r] == l ? "l" : "r"); // pull best move from table
		System.out.print("You choose: ");
		boolean success = false;
		char choice = ' ';
		while (!success) {
			try {
				choice = input.next().toLowerCase().charAt(0);
				success = true;
			} catch (InputMismatchException e) {
				System.out.println("Please enter 'l' for left or 'r' for right");
			}
		}
		if (choice == 'r')
			playerSum += list[r--]; // add to playerSum and decrement r
		else
			playerSum += list[l++]; // add to playerSum and increment l
	}
	
	public static void calculateBestMoves() {
		// function to compute best moves for a given game
		int x,y,z;
		
		// compute turn by : max(list[i] + min(x,y), list[j] + min(y,z))
		// we expect opponent to make the best move also
		for (int k = 0; k < SIZE; ++k) {
			for (int i = 0, j = k; j < SIZE; ++i, ++j) {
				// get values for x, y, z
				if ((i + 2) <= j)
					x = bestMoves[i+2][j];
				else
					x = 0;
				
				if ((i + 1) <= (j - 1))
					y = bestMoves[i + 1][j - 1];
				else
					y = 0;
				
				if (i <= j - 2)
					z = bestMoves[i][j - 2];
				else
					z = 0;
				
				if (list[i] + min(x,y) > list[j] + min(y,z))
					bestMoves[i][j] = i; // choose left
				else
					bestMoves[i][j] = j; // choose right
				
			}
		}
		
	}
	
	public static void checkWinner() {
		System.out.printf("You scored %d\n", playerSum);
		System.out.printf("The computer scored %d\n", computerSum);
		if (playerSum == computerSum) {
			System.out.println("TIE :|");
			return;
		}
		if (playerSum > computerSum)
			System.out.println("You win :)");
		else
			System.out.println("You lose :(");
	}
	
	public static int min (int a, int b) {
		return Math.min(a, b);
	}
	
	public static int max (int a, int b) {
		return Math.max(a, b);
	}
	
	public static void restart() {
		System.out.println("Would you like to play again?");
		System.out.println("y/n");
		char c = input.next().toLowerCase().charAt(0);
		if (c != 'y') {
			running = false;
			System.out.println("Okay, cya :)");
			return;
		}
		// generate a new list
		initList();
		// restart bestMoves
		bestMoves = new int[SIZE][SIZE];
		// calculate new best moves
		calculateBestMoves();
		// reset l, r
		l = 0;
		r = SIZE - 1;
		// reset sums
		playerSum = 0;
		computerSum = 0;
	}
}
