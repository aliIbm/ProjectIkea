package watcherProject;
import java.io.File;
import java.io.FileInputStream;
import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.Node;
import javax.xml.stream.XMLInputFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import utility.ApplicationUtility;

import java.sql.Time;

/**
 * 
 * @author Alir
 *	This class is responsible for direct int to human language translation
 */
public class ClockTranslator {

	private Time MORNING_TIME = new Time(1, 0, 0);
	private Time NOON_TIME = new Time(12,0,0);
	private Time AFTERNOON_TIME = new Time(13,0,0);
	private Time EVENING_TIME = new Time(18,0,0);
	private Time NIGHT_TIME = new Time(20,31,0);
	private Time PRE_MIDNIGHT_TIME = new Time(23,59,59);
	private Time MIDNIGHT_TIME = new Time(0,0,0);
	
	private NodeList dictionary;
	private final String DICT_PRP_KEY="dictionarySource";
	
	public ClockTranslator(){
		ApplicationUtility.LogIt("ClockTranslator() intialized");
		dictionary = getDictionary();
	}
	/**
	 * 
	 * @param userTime
	 * @return
	 */
	public String getTimeOfDay(Time userTime) {
		ApplicationUtility.LogIt("getTimeOfDay(Time userTime) invoked");
		if(userTime.after(MORNING_TIME) && userTime.before(NOON_TIME)){
			return "in the morning";// morning
		}else if(userTime.after(MORNING_TIME) && userTime.before(AFTERNOON_TIME)){
			return "at noon";// noon
		}else if(userTime.after(NOON_TIME) && userTime.before(EVENING_TIME)){
			return "in the after noon";// evening
		}else if(userTime.after(AFTERNOON_TIME) && userTime.before(NIGHT_TIME)){
			return "in the evening";// evening
		}else if(userTime.after(EVENING_TIME) && userTime.before(PRE_MIDNIGHT_TIME)){
			if(!(userTime.getHours()==23 && userTime.getMinutes() == 45))
			{
				return "at night";// night
			}else{
				return "";
			}
		}else if(userTime.after(MIDNIGHT_TIME) && userTime.before(MORNING_TIME)){
			return "";// midnight
		}else if(userTime.toString().equalsIgnoreCase(MIDNIGHT_TIME.toString())){
			return "";// midnight
		}else{
			return "Unrecognized";
		}
	}
	/**
	 * Translates the hour value
	 * @param HH
	 * @return
	 */
	public String getHourTranslation(int hh, int mm){
		ApplicationUtility.LogIt("getHourTranslation(int hh, int mm) invoked");
		// Add an hour to switch phrase for "quarter to HH"
		if(mm == 45 || mm > 54){
			hh++;
		}
		if(hh==00 || hh == 24){
			return "midnight";
		}else{
			if(hh>12){
				return lookupDictionary(hh-12);
			}else{
				return lookupDictionary(hh);
			}
		}
	}
	/**
	 * Translates the minute value
	 * @param mm
	 * @return
	 */
	public String getMinuteTranslation (int mm){
		ApplicationUtility.LogIt("getMinuteTranslation (int mm) invoked");
		if(mm<5 && mm > 0 ){
			return "just past";
		}else if (mm>54){
			return "just before";
		}
		switch (mm) {
		case 00:
			return "exactly";
		case 15:
			return "quarter past";
		case 30:
			return "half past";
		case 45:
			return "quarter to";
		default:
			return lookupDictionary(mm) + " past";
		}
	}
	/**
	 * Takes the hour minute and period value and return a compiled sentence
	 * @param translatedHour
	 * @param translatedMins
	 * @param translatedPeriod
	 * @param graph
	 * @return
	 */
	public String compileMessage(String translatedHour,
			String translatedMins, String translatedPeriod) {
		String completeTime = "The time is " + translatedMins + " " + translatedHour + " " + translatedPeriod;
		return completeTime;
	}
	
	/**
	 * Looks up the dictionary for a valid mapping of lookup value
	 * @param lookup
	 * @return
	 */
	private String lookupDictionary(int lookup){
		return ApplicationUtility.nodeListLookUp(dictionary, "IntValue", "Translation", String.valueOf(lookup));
	}
	/**
	 * Gets the node list from xml file. This is only done once per ClockTranslator instance
	 * @return
	 */
	private NodeList getDictionary() {
		return ApplicationUtility.xmlToNodeList(DICT_PRP_KEY, "TimeValue");
	}
	
}
