package Assignment2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
	// game constants
	private static final int SIZE = 7;
	private static int[] list = null;
	private static int l = 0, r = SIZE - 1; // pointers to edges of the list
	private static final char STOP = 'X';
	
	private static Scanner input;
	
	// game variables
	private static int playerSum, computerSum;
	public static void main(String[] args) {
		input = new Scanner(System.in);
		char c = ' ';
		initList();
		for (int i = 0; i < SIZE; i++) {
			showList();
			playerTurn();
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

	public static void compTurn() {
		// method to compute the computer's turn
	}
	
	public static void playerTurn() {
		System.out.printf("Your options are %d (type 'l') or %d (type 'r')", list[l], list[r]);
		System.out.printf("\nBest choice is: %d \n", 0); // add logic here
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
}
