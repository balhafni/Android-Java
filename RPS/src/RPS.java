/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Rijen
 */

public class RPS {

	static double nog = 0;
	static int counter = 0;
	static double wins = 0;
	static int loss = 0;
	static int ties = 0;
	final int ROCK = 0;
	final int PAPER = 1;
	final int SCISSOR = 2;

	public static void main(String[] args) {
		display();
		int computerMove = 0;
		Random random = new Random();
		computerMove = random.nextInt(3);
		Game(computerMove);
	}

	public static void Game(int number) {
		Random rand = new Random();
		Scanner input = new Scanner(System.in);
		System.out.println("Choose a move (0-2): ");
		int player = input.nextInt();
		if (player == number) {
			System.out.println("You Chose:" + player);
			System.out.println("The Computer Chose:" + number);
			System.out.println("It's a tie");
			number = rand.nextInt(3);
			counter++;
			ties++;
			nog++;
		} else if (player == 2 && number == 1 || player == 1 && number == 0 || player == 0 && number == 2) {
			System.out.println("You chose: " + player);
			System.out.println("The Computer chose " + number);
			System.out.println("You Won");
			number = rand.nextInt(3);
			counter++;
			wins++;
			nog++;
		} else if (player == 0 && number == 1 || player == 1 && number == 2 || player == 2 && number == 0) {
			System.out.println("You chose: " + player);
			System.out.println("Computer chose " + number);
			System.out.println("you lose.");
			number = rand.nextInt(3);
			counter++;
			loss++;
			nog++;
		}
		if (counter == 5 || wins == 3 || loss == 3) {
			double percentageWon;
			percentageWon = ((wins / nog) * 100);
			System.out.println("Thanks for playing...");
			System.out.println("Game statistics");
			System.out.println("Number of WINS: " + wins + "\n" + "Number of LOSSES: " + loss + "\n" + "Number of TIES:"
					+ ties + "\n" + "Number of games:" + nog + "\n" + "PERCENTAGE WON: " + percentageWon + "%");
			System.out.println();
			System.out.println("Do you want to play again?????");
			System.out.println("(1) Yes");
			System.out.println("(2) No");
			int num1 = input.nextInt();
			if (num1 == 1) {
				counter = 0;
				Game(number);
			} else {
				System.out.println("Game Over!!!");
				System.out.println("Thanks for playing...");
				System.exit(0);
			}
		} else {
			Game(number);
		}
	}

	public static void display() {
		System.out.println("Welcome to a Game of Rock-Paper scissors.");
		System.out.println("(0) for  Rock.");
		System.out.println("(1) for Paper.");
		System.out.println("(2) for Scissors.");
	}
}
