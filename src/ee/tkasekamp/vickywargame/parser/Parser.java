package ee.tkasekamp.vickywargame.parser;

import ee.tkasekamp.vickywargame.core.Battle;
import ee.tkasekamp.vickywargame.core.JoinedCountry;
import ee.tkasekamp.vickywargame.core.Unit;
import ee.tkasekamp.vickywargame.core.War;
import ee.tkasekamp.vickywargame.core.WarGoal;
import ee.tkasekamp.vickywargame.core.Battle.Result;
import ee.tkasekamp.vickywargame.gui.GuiController;
import ee.tkasekamp.vickywargame.util.Reference;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/** Readers in order of activation in a typical save game:
 *   saveGameReader
 *   	referenceReader 
 *   saveGameReader
 *   ...
 *   	warReader
 *   		warGoalReader
 *  		battleReader
 *  		...			
 *  		battleReader
 *  	warReader
 *  		originalWarGoalReader
 *  		warGoalReader
 *  	warReader
 *  saveGameReader
 *  	warReader
 *  ...
 *
 */
public class Parser  {
	/*Everything to read in wars */
	private ArrayList<War> warList= new ArrayList<War>(); // Stores all wars in the save game
	private boolean warProcessing = false; // True when previous or active war has been found
	private int WAR_COUNTER = 0; // Used to count the list in allWars
	/* War details */
	private String dateBuffer = ""; // Stores the last date for JoinedCountry or Battle dates
	private ArrayList<JoinedCountry> countryList = new ArrayList<JoinedCountry>();  // Stores temporarily to give to war
	private ArrayList<Battle> battleList = new ArrayList<Battle>();  // Stores temporarily to give to war
	private ArrayList<WarGoal> warGoalList = new ArrayList<WarGoal>();
	/* Battle details */
	private ArrayList<Unit> unitList = new ArrayList<Unit>();  // Stores temporarily to give to battle. Used by both attacker and defender. 
	private boolean battleProcessing; // True so all new lines will be read into battleReader
	private int BATTLE_COUNTER = 0; // Used to change the current battle in battleList
	private boolean attackerDefender; // True is attacker, false is defender. Changed while reading a battle as the first units are attackers, the rest defenders
	/* WarGoal details */
	private boolean warGoalProcessing; // True so all new lines will be read into warGoalReader
	private int WARGOAL_COUNTER = 0;
	private boolean originalWarGoalProcessing; // New for Hod. Has the same function as warGoalProcessing
	/* Various */
	private int bracketCounter = 0; // bracketCounter is uses to check if all data from the war has been read in
	static public Reference saveGameData = new Reference(); // public so it can be used by all methods
	
	private GuiController controller;
	public Parser(GuiController controller) {
		this.controller = controller;
	}
	
	
	/** This is the main reader. It gets a path and reads line by line from it.
	 * If it find a line with a specific keyword, it passes it on to the other readers in this class.
	 * When reading a war, battle or wargoal, the Processing is set true and all lines are passed to that specific reader until
	 * the Processing is set false. War reading ends when the bracketCounter is 0 again.
	 * @param saveGamePath
	 * @return ArrayList<War>
	 * @throws IOException
	 */
	public ArrayList<War> read(String saveGamePath) throws IOException {
		/* Resetting values for when user loads multiple files during a session 
		 * Might not be necessary but doing it just in case. */
		saveGameData = new Reference();
		warList = new ArrayList<War>();
		WAR_COUNTER = 0;
		BATTLE_COUNTER = 0;
		WARGOAL_COUNTER = 0;
		bracketCounter = 0;

		InputStreamReader reader = new InputStreamReader(new FileInputStream(saveGamePath), "ISO8859_1"); // This encoding seems to work for ö
		BufferedReader scanner = new BufferedReader(reader);

		String line;
		while ((line = scanner.readLine()) != null) {
		    line = line.replaceAll("\t", "");
		    /* Data about the game: date, player and start_date 
		     * The reading is done in referenceReader*/
		    if (line.startsWith("date=") || line.startsWith("player=") || line.startsWith("start_date=")) {
		    	referenceReader(line); 
		    }
		    if (line.startsWith("previous_war=") || line.equals("active_war=")) {
		    	warProcessing = true;
		    	/* Further  check if war is active */
		    	if (line.equals("previous_war=")) {
			    	warList.add(new War(false));
		    	}
		    	else {
			    	warList.add(new War(true));
		    	}

		    }

		    
		    if (warProcessing) {
		    	 bracketCounterChange(line);
		    	 /* Checking if the line needs to be passed on to other readers */
		    	 if (line.startsWith("battle") || battleProcessing) {
		    		 battleProcessing = true;
		    		 battleReader(line);
		    	 }
		    	 else if (line.startsWith("war_goal") || warGoalProcessing) {
		    		 warGoalProcessing = true;
			    		warGoalReader(line);

		    	 }
		    	 else if (line.startsWith("original_wargoal") || originalWarGoalProcessing) {
		    		 originalWarGoalProcessing = true;
		    		 originalWGoalReader(line);

		    	 }
		    	 else {
		    		 warReader(line);
		    	 }
		    }
		    /* 
		     * bracketCounter will only reach 0 when the whole war has been processed. Name check is required because
		     * on the first cycle bracketcounter will still be 0.
		     */
		    if (bracketCounter == 0  && warProcessing && !(warList.get(WAR_COUNTER).getName().equals(""))) {
		    	/* General housekeeping
		    	 * country and battle lists to proper types
		    	 *  */
		    	JoinedCountry[] countryTempList = new JoinedCountry[countryList.size()];
		    	JoinedCountry[] countryTempList2 = countryList.toArray(countryTempList);
		    	warList.get(WAR_COUNTER).setCountryList(countryTempList2);

		    	Battle[] battleTempList = new Battle[battleList.size()];
		    	Battle[] battleTempList2 = battleList.toArray(battleTempList);
		    	warList.get(WAR_COUNTER).setBattleList(battleTempList2);

		    	WarGoal[] warGoalTempList = new WarGoal[warGoalList.size()];
		    	WarGoal[] warGoalTempList2 = warGoalList.toArray(warGoalTempList);
		    	warList.get(WAR_COUNTER).setWarGoalList(warGoalTempList2);
		    	warGoalList.clear();

		    	warGoalProcessing = false;
		    	WARGOAL_COUNTER = 0;
		    	BATTLE_COUNTER = 0;
		    	countryList.clear();
		    	battleList.clear();
		    	WAR_COUNTER++;
		    	warProcessing = false;

		     }

		}
		scanner.close();
		/* Setting the start date and casus belli */
		for (War war : warList) {
			war.setCasusBelliAndStartDate();
		}
		return warList;

	}

	/** This processes the war. It creates a new war class and assigns values to it by reading new lines. 
	 * War reading ends when the bracketCounter is 0 again. 
	 * 
	 * @param line
	 */
	public void warReader(String line) {
		if (line.startsWith("name") && (warList.get(WAR_COUNTER).getName().equals(""))) { // Name check required so it is not overwritten
			line = nameExtractor(line, 6, true);
			warList.get(WAR_COUNTER).setName(line);
		}		
		else if (line.startsWith("1")) {
			line = line.replaceAll("=", "");
			setDateBuffer(line);
			
		}				
		else if (line.startsWith("add_attacker=")) {
			line = nameExtractor(line, 14, true);
			JoinedCountry country = new JoinedCountry(line,true, dateBuffer);
			countryList.add(country);
		}
		else if (line.startsWith("add_defender=")) {
			line = nameExtractor(line, 14, true);
			JoinedCountry country = new JoinedCountry(line,false, dateBuffer);
			countryList.add(country);
		}
		else if (line.startsWith("rem_attacker=")) {
			line = nameExtractor(line, 14, true);
			for (JoinedCountry item : countryList) {
				if (item.getTag().equals(line)) {
					item.setEndDate(dateBuffer);
				}
			}
		}
		else if (line.startsWith("rem_defender=")) {
			line = nameExtractor(line, 14, true);
			for (JoinedCountry item : countryList) {
				if (item.getTag().equals(line)) {
					item.setEndDate(dateBuffer);
					if (!warList.get(WAR_COUNTER).isActive()) { // If the war is not active, the date the last defender is removed will also be the war's enddate
						warList.get(WAR_COUNTER).setEndDate(dateBuffer);
					}
				}
			}
		}
		else if (line.startsWith("attacker")) {		
			/* Checking if it's empty so only the first one is the attacker */
			if (warList.get(WAR_COUNTER).getAttacker().equals("")) {
				line = nameExtractor(line, 10, true);
				warList.get(WAR_COUNTER).setAttacker(line);
			}
		}
		else if (line.startsWith("defender")) {		
			/* Checking if it's empty so only the first one is the defender */
			if (warList.get(WAR_COUNTER).getDefender().equals("")) {
				line = nameExtractor(line, 10, true);
				warList.get(WAR_COUNTER).setDefender(line);
			}
		}
		else if (line.startsWith("original_attacker")) {
			line = nameExtractor(line, 19, true);
			/* Checking required for some older wars */
			if (line.equals("---")) {
				for (JoinedCountry country : countryList) {
					if (country.isJoinType()) {
						warList.get(WAR_COUNTER).setOriginalAttacker(country.getTag());
						break;
					}
				}			
			}
			else {
				warList.get(WAR_COUNTER).setOriginalAttacker(line);				
			}

		}
		else if (line.startsWith("original_defender")) {
			line = nameExtractor(line, 19, true);
			/* Checking required for some older wars */
			if (line.equals("---")) {
				for (JoinedCountry country : countryList) {
					if (!country.isJoinType()) {
						warList.get(WAR_COUNTER).setOriginalDefender(country.getTag());
						break;
					}
				}				
			}
			else {
				warList.get(WAR_COUNTER).setOriginalDefender(line);				
			}
			
		}
		else if (line.startsWith("action")) {
			line = nameExtractor(line, 8, true);
			warList.get(WAR_COUNTER).setAction(line);
			
		}
		

	}
	/** All data about the battle is sent here 
	 * Since the attacker is always first, it is only required to check if the attacker already has the data
	 * 
	 * */
	public void battleReader(String line) {
		if (line.startsWith("name")) {
			line = nameExtractor(line, 6, true);
			Battle b = new Battle(dateBuffer, line);
			battleList.add(b);
			attackerDefender = true; // From now on all unit data will be about the attacker
		}
		else if (line.startsWith("location")) {
			line = nameExtractor(line, 9, false);
			int location = Integer.parseInt(line);
			battleList.get(BATTLE_COUNTER).setLocation(location);
		}
		else if (line.startsWith("result")) {
			line = nameExtractor(line, 7, false);
			if (line.equals("yes")) {
				battleList.get(BATTLE_COUNTER).setRes(Result.YES);
			}
			else {
				battleList.get(BATTLE_COUNTER).setRes(Result.NO);
			}
		}
		else if (line.startsWith("country")) {
			line = nameExtractor(line, 9, true);
			if (attackerDefender) {
				battleList.get(BATTLE_COUNTER).setAttacker(line);
			}
			else {
				battleList.get(BATTLE_COUNTER).setDefender(line);
			}
		}
		else if (line.startsWith("leader")) {
			line = nameExtractor(line, 8, true);
			if (attackerDefender) {
				battleList.get(BATTLE_COUNTER).setLeaderAttacker(line);
			}
			else {
				battleList.get(BATTLE_COUNTER).setLeaderDefender(line);
			}
		}
		else if (line.startsWith("losses")) {
			line = nameExtractor(line, 7, false);
			int losses = Integer.parseInt(line);
			if (attackerDefender) {
				/* If this point is reached all data about the attackers units will have been processed. 
				 * Unit list added to battle and cleared
				 * Now the defenders units will be processed
				 */
				attackerDefender = false;
				battleList.get(BATTLE_COUNTER).setAttackerLosses(losses);

				/* Housekeeping */
		    	Unit[] unitTempList = new Unit[unitList.size()];
		    	Unit[] unitTempList2 = unitList.toArray(unitTempList);
		    	battleList.get(BATTLE_COUNTER).setAttackerUnits(unitTempList2);
		    	unitList.clear();
		    	/* Battle type */
		    	battleList.get(BATTLE_COUNTER).determineType();
		    	
			}
			else {
				/* If this point is reached all data about the defender units will have been processed. 
				 * Unit list added to battle and cleared
				 * Total losses calculated
				 */
				battleList.get(BATTLE_COUNTER).setDefenderLosses(losses);
				battleList.get(BATTLE_COUNTER).setTotalLosses(losses+battleList.get(BATTLE_COUNTER).getAttackerLosses()); 

		    	Unit[] unitTempList = new Unit[unitList.size()];
		    	Unit[] unitTempList2 = unitList.toArray(unitTempList);
		    	battleList.get(BATTLE_COUNTER).setDefenderUnits(unitTempList2);
		    	unitList.clear();
		    	
		    	
				battleProcessing = false;
				BATTLE_COUNTER++;
			}
			
		}
		else if (!(line.equals("attacker=")) && !(line.startsWith("defender=")) && !(line.equals("{")) 
				&& !(line.equals("}")) &&!(line.equals("battle="))){
			/* All units such as "infantry=9000" will come here
			 * 
			 */

			try {
				String [] pieces = line.split("=");
				int losses = Integer.parseInt(pieces[1]);
				unitList.add(new Unit(pieces[0], losses));
			} catch (NumberFormatException e) {
				// Mainly debug if the lines which come here aren't integers
				controller.getErrorLabel().setText(controller.getErrorLabel().getText() + "Problem with reading: " + line);
			}
			
		}
	}
	/** Very similar to battleReader in use
	 * If war_goal is read in, all data is sent here until the line receiver comes
	 */
	public void warGoalReader(String line) {
		/* Check required because the first line in war goal is not always the same */
		if (line.startsWith("war_goal")) {
			WarGoal w = new WarGoal();
			warGoalList.add(w);
		}

		else if (line.startsWith("state")) { // state_province_id
			line = nameExtractor(line, 18, false);
			int state = Integer.parseInt(line);
			warGoalList.get(WARGOAL_COUNTER).setState_province_id(state);
		}
		else if (line.startsWith("casus")) {
			line = nameExtractor(line, 13, true);
			warGoalList.get(WARGOAL_COUNTER).setCasus_belli(line);
		}
		else if (line.startsWith("country")) {
			line = nameExtractor(line, 9, true);
			warGoalList.get(WARGOAL_COUNTER).setCountry(line);
		}
		else if (line.startsWith("actor")) {
			line = nameExtractor(line, 7, true);
			warGoalList.get(WARGOAL_COUNTER).setActor(line);
		}
		else if (line.startsWith("receiver")) {
			line = nameExtractor(line, 10, true);
			warGoalList.get(WARGOAL_COUNTER).setReceiver(line);
//			WARGOAL_COUNTER++;
//			warGoalProcessing = false;

		}
		else if (line.startsWith("score")) {
			line = nameExtractor(line, 6, false);
			double score = Double.parseDouble(line);
			warGoalList.get(WARGOAL_COUNTER).setScore(score);
		}
		else if (line.startsWith("change")) {
			line = nameExtractor(line, 7, false);
			double c = Double.parseDouble(line);
			warGoalList.get(WARGOAL_COUNTER).setChange(c);
		}
		else if (line.startsWith("date")) {
			line = nameExtractor(line, 6, true);
			warGoalList.get(WARGOAL_COUNTER).setDate(line);
		}
		else if (line.startsWith("is_fulfilled")) {
			line = nameExtractor(line, 13, false);
			if (line.equals("yes")) {
				warGoalList.get(WARGOAL_COUNTER).setFulfilled(Result.YES);			
			}
		}
		else if (line.startsWith("}")) {
			/* This is always the last line in a war goal 
			 * Clearing the wargoal list and passing it on to war like in battleReader*/
//			line = nameExtractor(line, 10, true);
//			warGoalList.get(WARGOAL_COUNTER).setReceiver(line);
			WARGOAL_COUNTER++;
			warGoalProcessing = false;

		}
	}
	/** Similar to warGoalReader. Only used in HoD */
	public void originalWGoalReader(String line) {
		if (line.startsWith("state")) { // state_province_id
			line = nameExtractor(line, 18, false);
			int state = Integer.parseInt(line);
			warList.get(WAR_COUNTER).getOriginalWarGoal().setState_province_id(state);
		}
		else if (line.startsWith("casus")) {
			line = nameExtractor(line, 13, true);
			warList.get(WAR_COUNTER).getOriginalWarGoal().setCasus_belli(line);
		}
		else if (line.startsWith("country")) {
			line = nameExtractor(line, 9, true);
			warList.get(WAR_COUNTER).getOriginalWarGoal().setCountry(line);
		}
		else if (line.startsWith("actor")) {
			line = nameExtractor(line, 7, true);
			warList.get(WAR_COUNTER).getOriginalWarGoal().setActor(line);
		}
		else if (line.startsWith("receiver")) {
			/* This is always the last line in a war goal 
			 * Clearing the wargoal list and passing it on to war like in battleReader*/
			line = nameExtractor(line, 10, true);
			warList.get(WAR_COUNTER).getOriginalWarGoal().setReceiver(line);
		}
		else if (line.startsWith("score")) {
			line = nameExtractor(line, 6, false);
			double score = Double.parseDouble(line);
			warList.get(WAR_COUNTER).getOriginalWarGoal().setScore(score);
		}
		else if (line.startsWith("change")) {
			line = nameExtractor(line, 7, false);
			double c = Double.parseDouble(line);
			warList.get(WAR_COUNTER).getOriginalWarGoal().setChange(c);
		}
		else if (line.startsWith("date")) {
			line = nameExtractor(line, 6, true);
			warList.get(WAR_COUNTER).getOriginalWarGoal().setDate(line);
		}
		else if (line.startsWith("is_fulfilled")) {
			line = nameExtractor(line, 13, false);
			if (line.equals("yes")) {
				warList.get(WAR_COUNTER).getOriginalWarGoal().setFulfilled(Result.YES);			
			}
		}
		else if (line.startsWith("}")) {
			/* This means there is no more data to be added */
			originalWarGoalProcessing = false;

		}
	}
	public void referenceReader(String line) {
		/* Checking the line and if there is no date 
		 * Same with start_date*/
		if (line.startsWith("date=") && saveGameData.getDate().equals("")) {
			line = nameExtractor(line, 6, true);
			saveGameData.setDate(line);
		}
		/* Checking if it's empty is not needed as there is only one line with player= */
		else if (line.startsWith("player=")) {
			line = nameExtractor(line, 8, true);
			saveGameData.setPlayer(line);
		}
		else if (line.startsWith("start_date=") && saveGameData.getStart_date().equals("")) {
			line = nameExtractor(line, 12, true);
			saveGameData.setStart_date(line);		
		}
	}
	public void bracketCounterChange(String line) {
		// Increases or decreases the bracketCounter
		if (line.equals("{")) {
			bracketCounter++;
		}
		else if (line.equals("}")) {
			bracketCounter--;
		}
	}
	
	/** Extracts the valuable data from a line by deleting the unimportant characters.
	 * 
	 * @param line
	 * @param index Last character to be removed
	 * @param removeLast If true, removes the last character (used for ")
	 * @return The correct line
	 */
	public String nameExtractor(String line, int index, boolean removeLast) {
		StringBuilder sb = new StringBuilder(line);
		sb.delete(0,index);
		if (removeLast) {
			sb.setLength(sb.length() - 1); // Removes last "
		}
		return sb.toString();
	}
	public String getDateBuffer() {
		return dateBuffer;
	}
	public void setDateBuffer(String dateBuffer) {
		this.dateBuffer = dateBuffer;
	}

}
