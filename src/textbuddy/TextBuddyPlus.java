package textbuddy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class TextBuddyPlus {

	private static String filePath = "text.txt";
	private static Scanner scanner = new Scanner(System.in);
	private static Vector<String> buffer = new Vector<String>();

	enum CommandType {
		ADD_LINE, DELETE_LINE, DISPLAY,CLEAR, EXIT, INVALID, SORT, SEARCH
	};

	public static void main(String[] args) {
		verifyTextfile(args);
		printWelcomeMessge(filePath);
		openFile(filePath);
		textBuddyManager();

	}

	private static void textBuddyManager() {
		while (true) {
			System.out.print("command: ");
			printToUser(executeCommand(scanner.nextLine()));
			saveFile(filePath);
		}
	}

	private static void openFile(String filePath) {
		try { // check if file exist if not create a new file with that name
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				buffer.add(line);
			}
			fileReader.close();
		} catch (IOException e) {
			System.out.println("Exception encountered while initalising the textfile");
			System.exit(0);
		}
	}

	private static void saveFile(String filePath) {
		File file = new File(filePath);
		file.delete();
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i=0; i< buffer.size(); i++){
				bw.write(buffer.get(i));
				if (i<buffer.size()-1){
					bw.newLine(); 
				}
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("Exception encountered while saving the textfile");
			System.exit(0);
		}
	}

	static String executeCommand(String userInput) {
		switch (getCommandType(userInput)) {
		case ADD_LINE:
			return addLine(getCommandContent(userInput));
			//		case DELETE_LINE:
			//			deleteLine(getCommandContent(userInput));
			//			break;
		case DISPLAY:
			return display();
		case CLEAR:
			return clearAll();
			//		case SORT:
			//			sort(getCommandContent(userInput));
			//			break;
			//		case SEARCH:
			//			search(getCommandContent(userInput));
			//			break;
		case EXIT:
			System.exit(0);
			break;
		case INVALID:
			return "Invalid Command issued!";
		default:

		}
		return "";
	}

	private static String clearAll() {
		buffer.clear();
		return ("all content deleted from "+ filePath);
		
	}

	private static String display() {
		for(int i=0; i< buffer.size(); i++){
			String lineToAdd="";
			lineToAdd+=String.valueOf(i+1);
			lineToAdd+=". ";
			lineToAdd+=buffer.get(i);
			System.out.println(lineToAdd);
		}

		if (buffer.size()==0){
			return (filePath + " is empty");
		}
		else{
			return "";
		}	
	}

	static String addLine(String commandContent) {
		buffer.add(commandContent);
		String confirmation = "added to " + filePath + ": \"" + commandContent + "\"";
		return confirmation;
	}

	private static String getCommandContent(String userInput) {
		return userInput.substring(userInput.indexOf(' ')+1);
	}

	private static CommandType getCommandType(String userInput) {
		if (userInput.split(" ").length<1){
			return CommandType.INVALID;
		}
		else {
			String userInputSubstring;
			if (userInput.split(" ").length>1){
				userInputSubstring = userInput.substring(0,userInput.indexOf(' '));
			}
			else{
				userInputSubstring = userInput;
			}

			if (userInputSubstring.equalsIgnoreCase("add")) {
				if (userInput.split(" ").length<2){
					return CommandType.INVALID;
				}
				return CommandType.ADD_LINE;
			} else if (userInputSubstring.equalsIgnoreCase("delete")) {
				if (userInput.split(" ").length<2){
					return CommandType.INVALID;
				}
				return CommandType.DELETE_LINE;
			} else if (userInputSubstring.equalsIgnoreCase("display")) {
				return CommandType.DISPLAY;
			} else if (userInputSubstring.equalsIgnoreCase("clear")) {
				return CommandType.CLEAR;
			} else if (userInputSubstring.equalsIgnoreCase("sort")) {
				return CommandType.SORT;
			} else if (userInputSubstring.equalsIgnoreCase("search")) {
				if (userInput.split(" ").length<2){
					return CommandType.INVALID;
				}
				return CommandType.SEARCH;
			} else if (userInputSubstring.equalsIgnoreCase("exit")) {
				return CommandType.EXIT;
			} else {
				return CommandType.INVALID;
			}		
		}
	}

	private static void printWelcomeMessge(String filePath) {
		System.out.println("Welcome to TextBuddy. " + filePath + " is ready for use");
	}

	private static void printToUser(String output){
		if (!output.equals("")){
			System.out.println(output);
		}
	}

	private static void verifyTextfile(String[] args) {
		try{
			filePath = args[0];
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Invalid Textfile name ! Please enter a valid name:");
			System.out.println("Please enter a valid name: ");
		}
		finally{
			while(filePath.isEmpty()){
				filePath = scanner.nextLine();
			}
		}
	}
}
