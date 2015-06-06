package ee.tkasekamp.vickywaranalyzer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import ee.tkasekamp.vickywaranalyzer.core.Country;
import ee.tkasekamp.vickywaranalyzer.core.JoinedCountry;
import ee.tkasekamp.vickywaranalyzer.core.War;
import ee.tkasekamp.vickywaranalyzer.parser.Parser;
import ee.tkasekamp.vickywaranalyzer.util.Localisation;
import javafx.scene.image.Image;

public class ModelServiceImpl implements ModelService {

	private String date = "";
	private String player = "";
	private String startDate = "";
	private TreeMap<String, Country> countryTreeMap;
	private ArrayList<War> warList;

	private UtilService utilServ;
	private Parser parser;

	public ModelServiceImpl(UtilService utilServ) {
		this.utilServ = utilServ;
		parser = new Parser(this);
		countryTreeMap = new TreeMap();
	}

	@Override
	public String createModel(String saveGamePath, boolean useLocalisation) {

		try {
			warList = parser.read(saveGamePath);
		} catch (IOException e1) {
			return "Couldn't read save game";
		}
		/* Generating a list of countries from warList */
		createUniqueCountryList();

		/* Localisation */
		// TODO multithreading
		if (useLocalisation) {
			Localisation.readLocalisation(utilServ.getInstallFolder(), countryTreeMap);
		}
		// TODO multithreading
		try {
			utilServ.writePathsToFile();
		} catch (IOException e) {
			return "Couldn't write to file";
		}
		// TODO multithreading
		countryTreeMap.forEach((tag, country) -> country.setFlag(utilServ.loadFlag(tag)));

		return "Everything is OK";
	}

	@Override
	public void reset() {
		date = "";
		player = "";
		startDate = "";
		countryTreeMap.clear();
	}

	@Override
	public String getOfficialName(String tag) {
		try {
			return countryTreeMap.get(tag).getOfficialName();
		} catch (NullPointerException e) {
			return tag;
		}

	}

	/**
	 * Creating a list that will contain every unique country.
	 */
	private void createUniqueCountryList() {
		Set<String> set = new HashSet<>();
		for (War item : warList) {
			for (JoinedCountry country : item.getCountryList()) {
				set.add(country.getTag());
			}
		}
		set.forEach(x -> countryTreeMap.put(x, new Country(x)));

	}

	@Override
	public void setDate(String line) {
		this.date = line;

	}

	@Override
	public void setPlayer(String line) {
		this.player = line;

	}

	@Override
	public void setStartDate(String line) {
		this.startDate = line;

	}

	@Override
	public String getDate() {
		return date;
	}

	@Override
	public String getPlayer() {
		return player;
	}

	@Override
	public String getStartDate() {
		return startDate;
	}

	@Override
	public String getPlayerOfficial() {
		return getOfficialName(player);
	}

	@Override
	public TreeMap<String, Country> getCountries() {
		return countryTreeMap;
	}

	@Override
	public ArrayList<War> getWars() {
		return warList;
	}

	@Override
	public boolean isHOD() {
		for (War war : warList) {
			if (!(war.getOriginalWarGoal().getCasus_belli().equals(""))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Image getFlag(String tag) {
		return countryTreeMap.get(tag).getFlag();
	}

}
