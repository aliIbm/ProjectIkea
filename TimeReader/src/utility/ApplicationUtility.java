/**
 * 
 */
package utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/**
 * General utility class
 * @author Ali
 * 
 */
public class ApplicationUtility {

	private static final String PROPERTY_SOURCE = "src/Resources/appProp.prop";
	private static BufferedWriter bw;
	private static FileWriter writer;

	/***
	 * Return property value from appProp.prop file.
	 * @param key
	 * @return
	 * value of key
	 */
	public static String getProperty(String key) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(PROPERTY_SOURCE));
			return prop.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/***
	 * Reads and xml document and return a nodelist of required tag
	 * @param xmlDirectory
	 * directory of xml
	 * @param tagName
	 * tag name desired
	 * @return
	 * node lis	t of tag
	 */
	public static NodeList xmlToNodeList(String xmlDirectory, String tagName) {
		String dictionaryDir = ApplicationUtility.getProperty(xmlDirectory);
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(dictionaryDir));
			doc.getDocumentElement().normalize();
			LogIt(tagName + " node list successfully retrieved from "
					+ xmlDirectory);
			return doc.getElementsByTagName(tagName);
		} catch (Exception e) {
			return null;
		}
	}
	/***
	 * Search a node list for a lookup value.
	 * @param nodeList
	 * node list being searched
	 * @param searchedTag
	 * tag the look up value will be compared to
	 * @param seekedTag
	 * tag the function will return
	 * @param lookupValue
	 * value being seeked
	 * @return
	 * value of search
	 */
	public static String nodeListLookUp(NodeList nodeList, String searchedTag,
			String seekedTag, String lookupValue) {
		Node aNode;
		String key;
		for (int i = 0; i < nodeList.getLength(); i++) {
			aNode = nodeList.item(i);
			if (aNode.getNodeType() == Node.ELEMENT_NODE) {
				Element anElement = (Element) aNode;
				key = anElement.getElementsByTagName(searchedTag).item(0)
						.getChildNodes().item(0).getNodeValue();

				if (key.equals(lookupValue)) {
					LogIt("NodeList search successful");
					return anElement.getElementsByTagName(seekedTag).item(0)
							.getChildNodes().item(0).getNodeValue();
				}
			}
		}
		return null;

	}
	/***
	 * Writes a traces into a log file. (Dir specified in prop file)
	 * NOTE: LOGGING MUST BE ENABLED IN PROPERTY FILE
	 * @param msg
	 */
	public static void LogIt(String msg) {

		try {
			if (getProperty("enableLogging").equals("1")) {
				if (bw == null) {
					writer = new FileWriter(getProperty("logDirectory"), true);
					bw = new BufferedWriter(writer);
				}
				bw.newLine();
				bw.append(msg + "\t :::::" + new Date(System.currentTimeMillis()));
				bw.newLine();
				bw.append("----------------------------------------------------------\n");
				bw.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
