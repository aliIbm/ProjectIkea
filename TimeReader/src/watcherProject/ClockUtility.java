package watcherProject;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import utility.ApplicationUtility;

/***
 * 
 * @author Alir
 * 	A utility class for the clock
 *
 */
public class ClockUtility {
	/***
	 * Convert the string format from user into java Time format for processing
	 * @param userTimeString
	 * @return
	 */
	public static Time convertStringToDate(String userTimeString){
		
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		Time time;
		try {
			time = new Time(formatter.parse(userTimeString).getTime());
			ApplicationUtility.LogIt("Converting string to time");
		} catch (ParseException e) {
			ApplicationUtility.LogIt("failed at convertStringToDate ::"+ e.getMessage());
			return null;
		}
		ApplicationUtility.LogIt("Successfully converted");
		return time;
	
	}
	

}
