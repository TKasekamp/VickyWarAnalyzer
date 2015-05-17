package ee.tkasekamp.vickywaranalyzer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.image.Image;
import ee.tkasekamp.vickywaranalyzer.core.Battle;
import ee.tkasekamp.vickywaranalyzer.core.Country;
import ee.tkasekamp.vickywaranalyzer.core.JoinedCountry;
import ee.tkasekamp.vickywaranalyzer.core.War;
import ee.tkasekamp.vickywaranalyzer.gui.FileLoader;
import ee.tkasekamp.vickywaranalyzer.parser.Parser;

public class ModelServiceImpl implements ModelService {
	private String date = "";
	private String player = "";
	private String startDate = "";
	private ArrayList<Country> countryList;
	private ArrayList<War> warList;

	private UtilService utilServ;
	private Parser parser;

	public ModelServiceImpl(UtilService utilServ) {
		this.utilServ = utilServ;
		parser = new Parser(this);
		countryList = new ArrayList<Country>();
	}

	@Override
	public String createModel(String saveGamePath, boolean useLocalisation) {
		System.out.println("Save game path " + saveGamePath);

		try {
			warList = parser.read(saveGamePath);
		} catch (IOException e1) {
			return "Couldn't read save game";
		}
		/* Generating a list of countries from warList */
		createUniqueCountryList();

		/* Localisation */
		// TODO multithreading
		// controller.getLoc().readLocalisation();
		/* Finding official names for every country and battle */
		// TODO try to optimise
		for (War war : warList) {
			setWarOfficialNames(war);
			for (Battle battle : war.getBattleList()) {
				setBattleOfficialNames(battle);
			}
		}
		// TODO multithreading
		try {
			utilServ.writePathsToFile();
		} catch (IOException e) {
			return "Couldn't write to file";
		}
		// TODO multithreading
		for (Country country : countryList) {
			country.setFlag(utilServ.loadFlag(country.getTag()));
		}
		return "Everything is OK";
	}

	@Override
	public void reset() {
		date = "";
		player = "";
		startDate = "";
		countryList.clear();
	}

	@Override
	public String findOfficialName(String tag) {
		for (Country country : countryList) {
			if (country.getTag().equals(tag)) {
				return country.getOfficialName();
			}
		}
		return tag;

	}

	private void setWarOfficialNames(War war) {
		war.setOfficialNames(findOfficialName(war.getOriginalAttacker()),
				findOfficialName(war.getOriginalDefender()));
	}

	private void setBattleOfficialNames(Battle battle) {
		battle.setOfficialNames(findOfficialName(battle.getAttacker()),
				findOfficialName(battle.getDefender()));
	}

	/**
	 * Creating a list that will contain every unique country.
	 */
	private void createUniqueCountryList() {
		// TODO try to optimise
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
		Collections.sort(tempcountrylist);

		// Adding to countryList
		for (String tag : tempcountrylist) {
			countryList.add(new Country(tag));
		}

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
		return findOfficialName(player);
	}

	@Override
	public ArrayList<Country> getCountries() {
		return countryList;
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

}
