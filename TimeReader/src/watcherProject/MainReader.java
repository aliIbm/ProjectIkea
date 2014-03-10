package watcherProject;
import java.util.Scanner;


public class MainReader {

	private static final String EXIT_COMMAND = "Quit";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner myScanner = new Scanner(System.in);
		Clock aClock = new Clock();
		String input;
		
		while(true){
			System.out.println("Enter time in HH:MM format to be translated : ");
			input = myScanner.next();
			System.out.println(aClock.TranslateTime(input));
			if(input.equals(EXIT_COMMAND)){
				myScanner.close();
				System.exit(0);
			}
		}
	}

}
