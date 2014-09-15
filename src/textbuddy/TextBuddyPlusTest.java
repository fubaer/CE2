package textbuddy;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextBuddyPlusTest {

	@Test
	public void test() {

		// Test adding hello
		assertEquals(TextBuddyPlus.executeCommand("add hellow"),"added to text.txt: \"hellow\"");

		// Test adding blank
		assertEquals(TextBuddyPlus.executeCommand("add"),"Invalid Command issued!");

		// Test adding number
		assertEquals(TextBuddyPlus.executeCommand("add 123"),"added to text.txt: \"123\"");

		// Test display after clear
		TextBuddyPlus.executeCommand("clear");
		assertEquals(0, TextBuddyPlus.getLineCount());
		assertEquals(TextBuddyPlus.executeCommand("display"),"text.txt is empty");

		// Test delete line
		TextBuddyPlus.executeCommand("add what does");
		TextBuddyPlus.executeCommand("add the");
		TextBuddyPlus.executeCommand("add fox");
		TextBuddyPlus.executeCommand("add say");
		assertEquals(TextBuddyPlus.executeCommand("delete 3"),"deleted from text.txt: \"fox\"");

		// Test search valid text
		TextBuddyPlus.executeCommand("add what does the fox say");
		TextBuddyPlus.executeCommand("add The secret of the fox");
		TextBuddyPlus.executeCommand("add Ancient mystery");
		TextBuddyPlus.executeCommand("add Somewhere deep in the woods");
		assertEquals(TextBuddyPlus.search("fox"),"");
		
		//reset for the next test
		TextBuddyPlus.executeCommand("clear");
		
		// Test search invalid text
		TextBuddyPlus.executeCommand("add what does the dog say");
		TextBuddyPlus.executeCommand("add The secret of the dog");
		TextBuddyPlus.executeCommand("add Ancient mystery");
		TextBuddyPlus.executeCommand("add Somewhere deep in the woods");
		assertEquals(TextBuddyPlus.search("fox"),"fox is not found within text.txt");
		
		// Test Sort
		assertEquals(TextBuddyPlus.sort(),"TextBuddy has been sorted!");

	}

}
