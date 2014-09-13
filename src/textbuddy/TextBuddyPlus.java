package textbuddy;

import java.util.Scanner;

public class TextBuddyPlus {
	
	private static String filePath = "";
	private static Scanner sc = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		verifyTextfile(args);
		printWelcomeMessge(filePath);
		
	}

	private static void printWelcomeMessge(String filePath) {
		System.out.println("Welcome to TextBuddy. " + filePath + " is ready for use");
	}

	private static void verifyTextfile(String[] args) {
		try{
			 filePath = args[0];
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Invalid Textfile name please enter a valid name!");
		}
		finally{
			while(filePath.isEmpty()){
				filePath = sc.nextLine();
			}
		}
		
	}

	

}
