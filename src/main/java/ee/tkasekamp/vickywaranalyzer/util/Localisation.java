package ee.tkasekamp.vickywaranalyzer.util;

import ee.tkasekamp.vickywaranalyzer.core.Country;
import ee.tkasekamp.vickywaranalyzer.gui.GuiController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static ee.tkasekamp.vickywaranalyzer.util.Reference.countryList;

/** Handles the localisation. I wanted it to only read from files which I knew had country tags, but 
 * that proved a bit difficult to implement. So now it reads all the .csv files in the localisation folder. 
 * It is not as cleaned up as the other ones.
 *
 */
public class Localisation {
	// Creating a Map so that the number of countries and therefore the number of checks decreases as more countries get names
	private static Map<String, String> countryMap = new HashMap<String, String>(); // TAG and officialname. Like EST and Estonia
	
	private GuiController controller;
	public Localisation(GuiController controller) {
		this.controller = controller;
	}
	/** Main method of this class. Manages the reading from csv */
	public void readLocalisation() {
		// Emptying the countryMap so all a new savegame is read only with that file's countries in map
		countryMap.clear();
		// Adding the countries to map
		for (Country country: countryList) { 
			countryMap.put(country.getTag(), ""); 
		}

//		countryMapTemp = new HashMap<countryMap;
		/* Checking if checkbutton is selected. if it is, read localisation */

		if (controller.getLocalisationCheck().isSelected()) {
			try {
				List<String> loclist = ListFiles(Reference.INSTALLPATH);
//				CSVReader(Reference.INSTALLPATH + "/localisation/text.csv");
//				CSVReader(Reference.INSTALLPATH + "/localisation/1.2.csv");
//				CSVReader(Reference.INSTALLPATH + "/localisation/beta2.csv");
//				CSVReader(Reference.INSTALLPATH + "/localisation/darkness_3_01.csv");
//				CSVReader(Reference.INSTALLPATH + "/localisation/housedivided.csv");
//				CSVReader(Reference.INSTALLPATH + "/localisation/newtext.csv");
				for (String string : loclist) {
					CSVReader(Reference.INSTALLPATH + "/localisation/" + string);
				}
				
			} catch (NullPointerException | IOException e) {
				controller.getErrorLabel().setText(controller.getErrorLabel().getText() + " Some or all the of the localisation files could not be found. ");
			}   
		}
		
		// If some countries have not been found an official name, setting the tag as official name
		for (Country country: countryList) { 
			if (country.getOfficialName().equals("")) {
				country.setOfficialName(country.getTag());
			}
		}	
		
	}
	/** Reads the given file and for any given line checks if the tag is equal to the
	 * one in countrylist. 
	 * @param filename
	 * @throws IOException
	 */
	private static void CSVReader(String filename) throws IOException {
		/* The same reader as in SaveGameReader */
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "ISO8859_1"); // This encoding seems to work for ö and ü
		BufferedReader scanner = new BufferedReader(reader);

		String line;
		while ((line = scanner.readLine()) != null) {
			String[] dataArray = line.split(";"); // Splitting the line
			
			// Checking for every country in map if the first part of line is equal to key
			// If it is, set it as value
			// Every time a match is found, the matching item is removed from the map so next time less checks are required
		    Iterator<Entry<String, String>> it = countryMap.entrySet().iterator();
		    while (it.hasNext()) { 
		        Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
				if (entry.getKey().equals(dataArray[0])) {
					entry.setValue(dataArray[1]);
					mapCleaner(entry);
					it.remove();

				}
			}

		}
		// Close the file once all data has been read.
		scanner.close();


	}
	
	/** This method gives the entry value to one of the countries in the countyList.
	 * After this the entry can be removed from map. I think doing it like this is most efficient 
	 * in terms of "if" checks required. */
	synchronized private static void mapCleaner(Map.Entry<String, String> entry) {
		for (Country country: countryList) { 
			if (country.getTag().equals(entry.getKey())) {
				country.setOfficialName(entry.getValue());
			}
		}
	}
	/** Installpath in, list of csv string names out. Half finished, will probably not be completed. */
	public static List<String> ListFiles(String path) throws NullPointerException{
	 
		// Directory path here
		//	  String path = "."; 
		String [] unwanted = {"darkness.csv", "1.1.csv", "1.3.csv", "1.4.csv", "beta1.csv", "beta3.csv", "darkness_3_02.csv",
				"darkness_3_03.csv",
				"event_news.csv",
				"event_news_3_01.csv",
				"housedivided2_1.csv",
				"housedivided2_2.csv",
				"housedivided2_3.csv",
				"newspaper_text.csv",
				"newstext_3_01.csv" };
		List<String>  locList = new ArrayList<String>();

		String files;
		File folder = new File(path + "/localisation");
		File[] listOfFiles = folder.listFiles(); 

		for (int i = 0; i < listOfFiles.length; i++) 
		{

			if (listOfFiles[i].isFile()) 
			{
				files = listOfFiles[i].getName();
				if (files.endsWith(".csv") || files.endsWith(".CSV"))
				{
//					for (String un : unwanted) {
						
//						if (!files.equals(un)) {
							locList.add(files);
//							System.out.println(files);
//						}
							
//					}

				}
			}
		}
		
		/* Removing files which I know have no country tags in them */

//		for (String un: unwanted) {
//			for (String loc: locList) {
//				if (loc.equals(un)) {
//					System.out.println(loc);
//					locList.remove(un);
//				}
//			}
//		}

		return locList; 
		
		

	}
}
