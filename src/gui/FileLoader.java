package gui;

import static main.Country.findOfficalName;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Battle;
import main.Country;
import main.JoinedCountry;
import main.Reference;
import main.SaveGameReader;
import main.War;

/**
 * This class starts the whole process. First the war is read from the save
 * file, then localisation and finally it fills the savegame tab.
 * 
 */
public class FileLoader {
	static private ArrayList<War> warList = new ArrayList<War>();

	private GuiController controller;

	public FileLoader(GuiController controller) {
		this.controller = controller;
	}

	/**
	 * This method reads the file paths from Reference
	 */
	public void readLauncher() {
		try {
			/* Reading save game file */
			warList = controller.getReader().read(Reference.saveGameFile);

			/* Generating a list of countries from warList */
			createUniqueCountryList();

			/* Localisation */
			controller.getLoc().readLocalisation();
			/* Finding official names for every country and battle */
			for (War war : warList) {
				war.setOfficialNames();
				for (Battle battle : war.getBattleList()) {
					battle.setOfficialNames();
				}
			}
			/* Setting the basic info panel */
			controller.getPlayerLabel().setText(
					findOfficalName(SaveGameReader.saveGameData.getPlayer()));
			controller.getCurrentDateLabel().setText(
					SaveGameReader.saveGameData.getDate());
			controller.getStartDateLabel().setText(
					SaveGameReader.saveGameData.getStart_date());

			/* Filling the warTable */

			warTablePopulateAll();
			populateCountryList();
			controller.getErrorLabel().setText(
					controller.getErrorLabel().getText()
							+ " Loading successful.");
		} catch (IOException e) {
			controller.getErrorLabel().setText(
					controller.getErrorLabel().getText() + e.toString());
		}
		// catch (NullPointerException ex) {
		// getErrorLabel().setText("Select save file to read");
		// }

	}

	/**
	 * Displays all wars in the warTable
	 */
	public void warTablePopulateAll() {
		controller.getWarTableContent().clear(); // Empty the warTable before
													// adding new items

		for (War item : warList) {
			controller.getWarTableContent().add(item);
		}
		controller.getWarTable().setItems(controller.getWarTableContent()); // Table
																			// and
																			// content
																			// are
																			// now
																			// connected
	}

	public void warTableShowActive() {
		controller.getWarTableContent().clear(); // Clearing content before
													// adding new items
		for (War item : warList) {
			if (item.isActive()) {
				controller.getWarTableContent().add(item);
			}

		}
	}

	public void warTableShowPrevious() {
		controller.getWarTableContent().clear(); // Clearing content before
													// adding new items
		for (War item : warList) {
			if (!(item.isActive())) {
				controller.getWarTableContent().add(item);
			}

		}
	}

	public void warTableShowMyWars() {
		controller.getWarTableContent().clear(); // Clearing content before
													// adding new items
		for (War item : warList) {
			for (JoinedCountry country : item.getCountryList()) {
				if (country.getTag().equals(
						SaveGameReader.saveGameData.getPlayer())) {
					controller.getWarTableContent().add(item);
				}
			}

		}
	}

	/**
	 * Shows wars of this country. Tag may be a tag or an official name. That's
	 * why two if checks are reguired.
	 * */
	public void warTableShowCountry(String tag) {
		controller.getWarTableContent().clear(); // Clearing content before
													// adding new items
		for (War item : warList) {
			for (JoinedCountry country : item.getCountryList()) {
				if (findOfficalName(country.getTag()).equals(tag)) {
					controller.getWarTableContent().add(item);
				} else if (country.getTag().equals(tag)) {
					controller.getWarTableContent().add(item);
				}
			}

		}
	}

	/**
	 * Fills the sidebar list with unique countries. Also fills in
	 * Reference.countryList to get all unique countries
	 */
	public void populateCountryList() throws FileNotFoundException {
		/*
		 * Throws nullpointerexception when a country has been selected and a
		 * new file is read in the same session
		 */
		try {
			controller.getSelectCountryIssue().getItems().clear(); // Clearing
																	// list of
																	// displayed
																	// countries
																	// in
																	// savegametab
		} catch (NullPointerException e) {
		}
		// /* Adding all countries to a set to get only unique ones */
		// Set <String> set = new HashSet<String>();
		// for (War item : warList) {
		// for (JoinedCountry country : item.getCountryList()) {
		// set.add(country.getTag());
		// }
		// }
		// /* This list is used to arrange the countries alphabetically */
		// List<String> tempcountrylist= new ArrayList<String>();
		// /* From set to list */
		// for (String strin : set) {
		// tempcountrylist.add(strin);
		// }
		// Collections.sort(tempcountrylist);
		// /* Adding unique countries to Reference.countryList
		// * and creating labels with flags as images
		// */
		for (Country country : Reference.countryList) {
			flagLoader(country);
			ImageView iv2 = new ImageView(country.getFlag());

			iv2.setFitWidth(32); // 30 to 35 look good
			iv2.setPreserveRatio(true);
			iv2.setSmooth(true);
			iv2.setCache(true);

			Label label = new Label(country.getOfficialName(), iv2);
			label.setContentDisplay(ContentDisplay.LEFT);
			controller.getSelectCountryIssue().getItems().add(label);
		}
	}

	/** Loads flag for this country */
	private void flagLoader(Country country) {
		// Country temp = new Country(tag);

		try {
			// temp.setFlag(new Image((InputStream) new FileInputStream(new
			// File(Reference.FLAGPATH + tag + ".png")))); // Previous attempt
			// System.out.println(FileLoader.class.);
			country.setFlag(new Image(FileLoader.class
					.getResourceAsStream(Reference.FLAGPATH + country.getTag()
							+ ".png")));
		} catch (NullPointerException e) {
			controller.getErrorLabel().setText(
					controller.getErrorLabel().getText() + " "
							+ country.getTag() + "  flag not found.");
		}
		/* Adding to list so the images are not loaded again */
		// Reference.countryList.add(temp);
		// return temp;
	}

	/**
	 * Geneates an alphabetical Reference.countryList of all tags with each
	 * country in it only once. Basically a set
	 */
	private void createUniqueCountryList() {
		Reference.countryList = new ArrayList<Country>(); // Resetting
															// countrylist
		/* Adding all countries to a set to get only unique ones */
		Set<String> set = new HashSet<String>();
		for (War item : warList) {
			for (JoinedCountry country : item.getCountryList()) {
				set.add(country.getTag());
			}
		}
		/* This list is used to arrange the countries alphabetically */
		List<String> tempcountrylist = new ArrayList<String>();
		/* From set to list */
		for (String strin : set) {
			tempcountrylist.add(strin);
		}
		Collections.sort(tempcountrylist); // Critical for countryList side tab

		// Adding to countryList
		for (String tag : tempcountrylist) {
			Reference.countryList.add(new Country(tag));
		}

	}

	public void setWarTableColumnValues() {
		/* Connecting the War fields with warTable columns */
		controller.getColNameWar().setCellValueFactory(
				new PropertyValueFactory<War, String>("name"));
		controller.getColAttackerWar().setCellValueFactory(
				new PropertyValueFactory<War, String>(
						"originalAttackerOfficial"));
		controller.getColDefenderWar().setCellValueFactory(
				new PropertyValueFactory<War, String>(
						"originalDefenderOfficial"));
		controller.getColCasusBelliWar().setCellValueFactory(
				new PropertyValueFactory<War, String>("casus_belli"));
		controller.getColStartDateWar().setCellValueFactory(
				new PropertyValueFactory<War, String>("startDate"));
		controller.getColEndDateWar().setCellValueFactory(
				new PropertyValueFactory<War, String>("endDate"));
	}

	/**
	 * Determines if the game version is HoD by looking if any of the wars have
	 * originalattacker and if they have, returns true
	 */
	public static boolean checkVersion() {
		for (War war : warList) {
			if (!(war.getOriginalWarGoal().getCasus_belli().equals(""))) {
				return true;
			}
		}
		return false;

	}

}
