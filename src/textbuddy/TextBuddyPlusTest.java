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
		assertEquals(TextBuddyPlus.executeCommand("display"),"text.txt is empty");
		
		
	}

}
