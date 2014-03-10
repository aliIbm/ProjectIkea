package watcherProject;
import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utility.ApplicationUtility;

/**
 * Digital time to human language translator 
 * @author Alir
 * 
 */
public class Clock {
	
	String clockExpression = "[0-2][0-9]:[0-9][0-9]";
	Pattern clockPattern = Pattern.compile(clockExpression);
	Matcher matcher;
	
	public Clock(){
		ApplicationUtility.LogIt("Clock() initialized");
	}
	/***
	 * Translates user input in format for HH:MM into human language
	 * @param userTimeString
	 * HH:mm format required
	 * @return
	 */
	public String TranslateTime(String userTimeString) {
		ApplicationUtility.LogIt("user inserted value " + userTimeString);
		ClockTranslator clockTrans = new ClockTranslator();
		matcher = clockPattern.matcher(userTimeString);
		if (matcher.matches()) {
			int hh = Integer.valueOf(userTimeString.split(":")[0]);
			int mm = Integer.valueOf(userTimeString.split(":")[1]);
			Time userTime = ClockUtility.convertStringToDate(userTimeString);
			String translatedHour = clockTrans.getHourTranslation(hh, mm);
			String translatedMins = clockTrans.getMinuteTranslation(mm);
			String translatedPeriod = clockTrans.getTimeOfDay(userTime);
			String compiled= clockTrans.compileMessage(translatedHour,
					translatedMins, translatedPeriod);
			ApplicationUtility.LogIt("Value returned " + compiled);
			return compiled;
		} else {
			ApplicationUtility.LogIt("Time entered not expected format");
			return "Please enter time in expected format";
		}
	}
}
