package textbuddy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

/**
* TextBuddy is a command-line program that edits  text in a specified
* file. The file will be created if it does not exist. If file already exists,
* additional text will be added to the end of the file.
* 
* Current TextBuddy available command
* are: add, delete, display, clear, search, sort, exit.
* 
* Example run of the program: 
* 
* c:>java TextBuddy mytextfile.txt 
* Welcome to TextBuddy. mytextfile.txt is ready for use 
* command: add little brown fox
* added to mytextfile.txt: “little brown fox” 
* command: display 
* 1. little brown fox 
* command: add jumped over the moon 
* added to mytextfile.txt: “jumped over the moon” 
* command: display 
* 1. little brown fox 
* 2. jumped over the moon
* command: delete 2 
* deleted from mytextfile.txt: “jumped over the moon”
* command: display 
* 1. little brown fox
* command: clear all content deleted from mytextfile.txt 
* command: display 
* mytextfile.txt is empty 
* command: exit 
* c:>
* 
* @author Tean Zheng Yang
*/

public class TextBuddyPlus {

	private static String filePath = "";
	private static Scanner scanner = new Scanner(System.in);
	private static Vector<String> buffer = new Vector<String>();

	//Possible Commands
	enum CommandType {
		ADD_LINE, DELETE_LINE, DISPLAY,CLEAR, EXIT, INVALID, SORT, SEARCH
	};

	public static void main(String[] args) {
		verifyTextfile(args);
		printWelcomeMessge(filePath);
		openFile(filePath);
		textBuddyManager();

	}
	
	/**
	 * TextBuddyManager
	 * handle the the TextBuddy till exit code is recieved
	 */
	private static void textBuddyManager() {
		while (true) {
			System.out.print("command: ");
			printToUser(executeCommand(scanner.nextLine()));
			saveFile(filePath);
		}
	}
	
	/**
	 * This operation is open file, creates a new one if one does not exist
	 */
	private static void openFile(String filePath) {
		try { 
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

	/**
	 * This operation is used save any changes made to the file
	 */
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

	/**
	 * This operation is used interprete the user input
	 */
	static String executeCommand(String userInput) {
		switch (getCommandType(userInput)) {
		case ADD_LINE:
			return addLine(getCommandContent(userInput));
		case DELETE_LINE:
			return deleteLine(getCommandContent(userInput));
		case DISPLAY:
			return display();
		case CLEAR:
			return clearAll();
		case SORT:
			return sort();
		case SEARCH:
			return search(getCommandContent(userInput));
		case EXIT:
			System.exit(0);
			break;
		case INVALID:
			return "Invalid Command issued!";
		default:
			return "Invalid Command issued!";
		}
		return "";
	}

	/**
	 * This operation is used search if the string is within the textbuddy
	 */
	static String search(String commandContent) {
		boolean isFound = false;
		for(int i=0; i< buffer.size(); i++){
			if (buffer.get(i).toLowerCase().indexOf(commandContent.toLowerCase())>-1){
				printToUser(commandContent + " has been found in: " +String.valueOf(i+1)+ ". "+ buffer.get(i));
				isFound = true;
			}
		}
		if (isFound){
			return "";
		}
		return (commandContent + " is not found within " + filePath);
	}

	/**
	 * This operation is used to sort the text within textbuddy
	 */
	static String sort() {
		Collections.sort(buffer);
		return "TextBuddy has been sorted!";
	}

	/**
	 * This operation is used to delete specific line in the text buddy
	 */
	private static String deleteLine(String commandContent) {

		int lineNumber=Integer.parseInt(commandContent);
		if (lineNumber<1||lineNumber>buffer.size()){
			return "Trying to delete invalid line";
		}
		else{
			String stringDeleted="";
			stringDeleted=buffer.get(lineNumber-1);
			buffer.remove(lineNumber-1);
			return ("deleted from " + filePath + ": " + "\"" + stringDeleted + "\"");
		}
	}

	/**
	 * This operation is used to delete everything within textbuddy
	 */
	private static String clearAll() {
		buffer.clear();
		return ("all content deleted from "+ filePath);

	}

	/**
	 * This operation is used to show all text within text buddy
	 */
	private static String display() {
		for(int i=0; i< buffer.size(); i++){
			String lineToAdd="";
			lineToAdd+=String.valueOf(i+1);
			lineToAdd+=". ";
			lineToAdd+=buffer.get(i);
			System.out.println(lineToAdd);
		}

		if (getLineCount()==0){
			return (filePath + " is empty");
		}
		else{
			return "";
		}	
	}

	/**
	 * This operation is used count the number of lines within the buffer
	 */
	static int getLineCount(){
		return buffer.size();
	}
	
	/**
	 * This operation is used to extract the content of the input
	 */
	static String addLine(String commandContent) {
		buffer.add(commandContent.trim());
		String confirmation = "added to " + filePath + ": \"" + commandContent + "\"";
		return confirmation;
	}

	/**
	 * This operation is used to extract the command of the input
	 */
	private static String getCommandContent(String userInput) {
		return userInput.substring(userInput.indexOf(' ')+1);
	}

	/**
	 * This operation is used to run the intepreted user input
	 */
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

	/**
	 * This operation is used print the greetings for the user
	 */
	private static void printWelcomeMessge(String filePath) {
		System.out.println("Welcome to TextBuddy. " + filePath + " is ready for use");
	}

	/**
	 * This operation is used print string to user
	 */
	private static void printToUser(String output){
		if (!output.equals("")){
			System.out.println(output);
		}
	}

	/**
	 * This operation is used confirm if the file name input in valid
	 */
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
