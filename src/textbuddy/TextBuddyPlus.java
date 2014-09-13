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

	private static String filePath = "";
	private static Scanner scanner = new Scanner(System.in);

	// This vector will be use to store the text lines
	private static Vector<String> buffer = new Vector<String>();


	public static void main(String[] args) {
		verifyTextfile(args);
		printWelcomeMessge(filePath);
		openFile(filePath);
		while (true) {
			System.out.print("command: ");
			String command = scanner.nextLine();
			executeCommand(command);
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

	private static void executeCommand(String command) {
		// TODO Auto-generated method stub

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
				filePath = scanner.nextLine();
			}
		}

	}



}
