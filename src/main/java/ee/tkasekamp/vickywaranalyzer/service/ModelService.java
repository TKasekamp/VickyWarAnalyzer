package ee.tkasekamp.vickywaranalyzer.service;

import java.io.IOException;

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

	public String getDate();

	public String getPlayer();

	public String getStartDate();

	public void reset();
}
