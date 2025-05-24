package TestUtilities;

public class EntryUtils {
	public static void countEntries(int totalEntries, int initial, int entCounter, int successCounter, int failCounter) {
		int tmpTotalEntries = (totalEntries - initial + 1);
		int column1Width = 30;
		int column2Width = 30;

//		Print Counting table with proper formatting
		System.out.println();
		System.out.println(
				"----------------------------------------------------------------------------------------------------------");
		System.out.println(String.format("| %-" + column1Width + "s | %-" + column2Width + "s |",
				"No. of Entries Done:    " + entCounter, "Out of:                 " + tmpTotalEntries));
		System.out.println(String.format("| %-" + column1Width + "s | %-" + column2Width + "s |",
				"No. of Entries Success: " + successCounter, "No. of Entries Failure: " + failCounter));
		System.out.println(
				"----------------------------------------------------------------------------------------------------------");
		System.out.println();
	}
}
