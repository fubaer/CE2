package textbuddy;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextBuddyPlusTest {

	@Test
	public void test() {

		// Test adding hello
		assertEquals(TextBuddyPlus.executeCommand("add hellow"),"added to text: \"hellow\"");

		// Test adding blank
		assertEquals(TextBuddyPlus.executeCommand("add"),"Invalid Command issued!");
		
		// Test adding number
		assertEquals(TextBuddyPlus.executeCommand("add 123"),"added to text: \"123\"");
		
	}

}
