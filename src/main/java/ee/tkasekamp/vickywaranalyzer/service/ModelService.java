package ee.tkasekamp.vickywaranalyzer.service;

import ee.tkasekamp.vickywaranalyzer.core.Country;
import ee.tkasekamp.vickywaranalyzer.core.War;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;

public interface ModelService {
	/**
	 * The main method of the analyzer. Used to parse the save game, create the
	 * model and read in all the other files.
	 *
	 * @param saveGamePath
	 * @param useLocalisation
	 * @return How the creation was handled
	 * @throws IOException
	 */
	public String createModel(String saveGamePath, boolean useLocalisation);

	public void setDate(String line);

	public String getDate();

	public void setPlayer(String line);

	public String getPlayer();

	public String getPlayerOfficial();

	public void setStartDate(String line);

	public String getStartDate();

	public void reset();

	/**
	 * Returns an official name for the given tag. If not found, the same tag is
	 * returned (but this is unlikely)
	 */
	public String getOfficialName(String tag);

	public Image getFlag(String tag);

	public ArrayList<Country> getCountries();

	public ArrayList<War> getWars();

	/**
	 * Determines if the game version is HoD by looking if any of the wars have
	 * originalattacker and if they have, returns true
	 */
	public boolean isHOD();

}
