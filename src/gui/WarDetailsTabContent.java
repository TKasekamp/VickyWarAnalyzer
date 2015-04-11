package gui;


import static gui.GuiController.*; 
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import main.Battle;
import main.Battle.Result;
import main.Battle.Type;
import main.Country;
import static main.Country.findOfficalName;
import main.Reference;
import main.War;
import main.WarGoal;



public class WarDetailsTabContent {
	static private War war;
	/** This is the master method which will fill all the tables and labels in war details tab */
	public static void warDetailsTabPopulate (War a) {
		war = a;
		/* Set the name of the tab */
		warDetailsTab.setText(war.getName());
		warDetailsTab.setDisable(false);
		/* Basic information about the war */
		warNameLabel.setText(war.getName());
		warStartDateLabel.setText(war.getStartDate());
		warEndDateLabel.setText(war.getEndDate());
		warActionLabel.setText(war.getAction());
		if (war.isActive()) {
			warHasEndedLabel.setText("No");
		}
		else {
			warHasEndedLabel.setText("Yes");
		}
		/* Filling the labels and tables */
		attackerPopulate();
		defenderPopulate();
		battleTablePopulate();
		lossesPopulate();
		warGoalTabPopulate();
		
		originalWarGoalPopulate();
	}
	/** Sets all labels at the attackers side except losses */
	private static void attackerPopulate() {
		/* If there is no attacker, the flag will be the original attacker's */
		if (war.getAttacker().equals("")) {
			for (Country country : Reference.countryList) {
				if (war.getOriginalAttacker().equals(country.getTag())) {
					attackerFlag.setImage(country.getFlag());
									getOriginalAttackerLabel().setText(country.getOfficialName());
				}
			}
			// Hiding the labels as there is no use for them
			warAttacker.setVisible(false);
			warAttackerHelper.setVisible(false);
		}
		else {
			for (Country country : Reference.countryList) {
				if (war.getAttacker().equals(country.getTag())) {
					attackerFlag.setImage(country.getFlag());
					
				}
			}
			// Showing labels and giving value
			warAttacker.setVisible(true);
			warAttackerHelper.setVisible(true);
			warAttacker.setText(war.getOriginalAttackerOfficial());
		}
		
//		originalAttackerLabel.setText(war.getOriginalAttacker()); // temporary solution
		
		/* Attacker table */
		attackerTableContent.clear(); // Clearing list
		
		/* Comparing war.countrylist items to reference.countrylist items
		 * Join type true for attackers */
		for (int i = 0; i < war.getCountryList().length; i++) {
			for (Country country : Reference.countryList) {
				if (country.getTag().equals(war.getCountryList()[i].getTag()) && war.getCountryList()[i].isJoinType()) {
					/* Creating a row with items from both classes */
					ObservableJoinedCountry temp = new ObservableJoinedCountry(country.getOfficialName(), country.getFlag(), war.getCountryList()[i].getStartDate(), 
							war.getCountryList()[i].getEndDate());
					attackerTableContent.add(temp);
					break;

				}
			}
		}
		
		/* Adding the countries to table */
		 attackerTable.setItems(attackerTableContent); 	

	}
	/** Sets all labels at the defenders side except losses */
	private static void defenderPopulate() {
		/* Finding the main flag */
		if (war.getDefender().equals("")) {
			for (Country country : Reference.countryList) {
				if (war.getOriginalDefender().equals(country.getTag())) {
					defenderFlag.setImage(country.getFlag());
					getOriginalDefenderLabel().setText(country.getOfficialName());					
				}
			}
			// Hiding the labels as there is no use for them
			warDefender.setVisible(false);
			warDefenderHelper.setVisible(false);

		}
		/* War is active and there is a defender */
		else {
			for (Country country : Reference.countryList) {
				if (war.getDefender().equals(country.getTag())) {
					defenderFlag.setImage(country.getFlag());
					
				}
			}
			// Showing labels and giving value
			warDefender.setVisible(true);
			warDefenderHelper.setVisible(true);
			warDefender.setText(Country.findOfficalName(war.getDefender()));
			getOriginalDefenderLabel().setText(war.getOriginalDefenderOfficial());
		}
//		originalDefenderLabel.setText(war.getOriginalDefender()); // temporary solution
		
		/* Defender list */
		defenderTableContent.clear(); // Clearing list
		      
		/* Comparing war.countrylist items to reference.countrylist items
		 * Join type true for attackers.
		 * Creating labels with flags to add to the list */
		for (int i = 0; i < war.getCountryList().length; i++) {
			for (Country country : Reference.countryList) {
				if (country.getTag().equals(war.getCountryList()[i].getTag()) && !war.getCountryList()[i].isJoinType()) { 
					ObservableJoinedCountry temp = new ObservableJoinedCountry(country.getOfficialName(), country.getFlag(), war.getCountryList()[i].getStartDate(), 
							war.getCountryList()[i].getEndDate());
					defenderTableContent.add(temp);
					break;

				}				
			}
		}
		/* Adding the countries to table */
		 defenderTable.setItems(defenderTableContent); 		
	}
	/** Sets all labels at the attackers side except losses */
	private static void battleTablePopulate() {
		battleTableContent.clear(); // Clearing before adding new items

        /* Adding battles to list */
		for (Battle item : war.getBattleList()) {
	        battleTableContent.add(item);
		}
		/* Displaying battles in table */
		 battleTable.setItems(battleTableContent); 

	}
	/** Sets labels for losses */
	private static void lossesPopulate() {
		
		int attackerTotalLosses = 0;
		int attackerTotalShipLosses = 0;
		int defenderTotalLosses = 0;
		int defenderTotalShipLosses = 0;
		
		/* The most complicated for loop EVER. 
		 * If you want to change it, good luck.
		 * Basically it looks at the battleList and compares the attacker and defender to the countryList.
		 * It also checks if the battle was land or naval and adds them to the total.*/
		for (Battle battle : war.getBattleList()) {
			for (int i = 0; i < war.getCountryList().length; i++) {
				if (battle.getAttacker().equals(war.getCountryList()[i].getTag()) ) {
					if (war.getCountryList()[i].isJoinType()) {
						if (battle.getBattleType() == Type.LAND) {
							attackerTotalLosses = attackerTotalLosses + battle.getAttackerLosses();
						}
						else {
							attackerTotalShipLosses = attackerTotalShipLosses + battle.getAttackerLosses();
						}						
					}
					else {
						if (battle.getBattleType()== Type.LAND) {
							defenderTotalLosses = defenderTotalLosses + battle.getDefenderLosses();
						}
						else {
							defenderTotalShipLosses = defenderTotalShipLosses + battle.getDefenderLosses();
						}
					}

				}							

				if (battle.getDefender().equals(war.getCountryList()[i].getTag()) ){
					if (war.getCountryList()[i].isJoinType()) {
						if (battle.getBattleType()== Type.LAND) {
							attackerTotalLosses = attackerTotalLosses + battle.getAttackerLosses();
						}
						else {
							attackerTotalShipLosses = attackerTotalShipLosses + battle.getAttackerLosses();
						}						
					}
					else {
						if (battle.getBattleType()== Type.LAND) {
							defenderTotalLosses = defenderTotalLosses + battle.getDefenderLosses();
						}
						else {
							defenderTotalShipLosses = defenderTotalShipLosses + battle.getDefenderLosses();
						}
					}
				}
			}

		}
	
		/* Attacker losses */
		attackerTotalLossesLabel.setText(Integer.toString(attackerTotalLosses));
		attackerTotalShipLossesLabel.setText(Integer.toString(attackerTotalShipLosses));
		/* Defender losses */
		defenderTotalLossesLabel.setText(Integer.toString(defenderTotalLosses));
		defenderTotalShipLossesLabel.setText(Integer.toString(defenderTotalShipLosses));
		/* Total losses */
		warTotalLossesLabel.setText(Integer.toString(attackerTotalLosses+defenderTotalLosses));
		warTotalShipLossesLabel.setText(Integer.toString(attackerTotalShipLosses+defenderTotalShipLosses));
		
		
	}
	
	/** Handels everthing in the war details tab */
	private static void warGoalTabPopulate() {
		/* Show the tab */
		warGoalTab.setDisable(false);
		warGoalTableContent.clear(); // A bit of cleaning
		
		// Adding to list
		for (WarGoal goal: war.getWarGoalList()) {
			warGoalTableContent.add(goal);
		}
		
		warGoalTable.setItems(warGoalTableContent);
	}
	/** This method is run once on startup and it defines the values of columns in 
	 * the war tab and the wargoaltab.
	 */
	public static void setColumnValues() {  
		/* Connecting the War fields with warTable columns */
        getColNameBattle().setCellValueFactory(new PropertyValueFactory<Battle, String>("name"));
        getColDateBattle().setCellValueFactory(new PropertyValueFactory<Battle, String>("date"));
        getColDefenderBattle().setCellValueFactory(new PropertyValueFactory<Battle, String>("defenderOfficial"));
        getColAttackerBattle().setCellValueFactory(new PropertyValueFactory<Battle, String>("attackerOfficial"));
        getColTypeBattle().setCellValueFactory(new PropertyValueFactory<Battle, Type>("battleType"));
        getColBattleResult().setCellValueFactory(new PropertyValueFactory<Battle, Result>("res"));
        getColBattleTotalLosses().setCellValueFactory(new PropertyValueFactory<Battle, Integer>("totalLosses"));
        
		/* Defender table */
        getColDefenderName().setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, String>("officialName"));
        getColDefenderFlag().setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, ImageView>("flag"));
        getColDefenderStartDate().setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, String>("joinDate"));
        getColDefenderEndDate().setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, String>("endDate"));
        
		/* Attacker table */
        getColAttackerName().setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, String>("officialName"));
        getColAttackerFlag().setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, ImageView>("flag"));
        getColAttackerStartDate().setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, String>("joinDate"));
        getColAttackerEndDate().setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, String>("endDate"));
	}
	
	
	/** Gives values to the labels related to wargoals */
	private static void originalWarGoalPopulate() {
		warName2.setText(war.getName());
		
		/* True if the version is HoD. Otherwise some labels will be hidden */
		if (FileLoader.checkVersion()) {
			/* Checking if this particular war has an original wargoal. Otherwise the first wargoal in the 
			 * wargoalList will be used
			 */
			if (!(war.getOriginalWarGoal().getActor().equals(""))) {
				warGoalActorLabel.setText(Country.findOfficalName(war.getOriginalWarGoal().getActor()));		
				warGoalReceiverLabel.setText(findOfficalName(war.getOriginalWarGoal().getReceiver()));		
				warGoalCBLabel.setText(war.getOriginalWarGoal().getCasus_belli());		
				warGoalCountryLabel.setText(findOfficalName(war.getOriginalWarGoal().getCountry()));		
				warGoalStateLabel.setText(Integer.toString(war.getOriginalWarGoal().getState_province_id()));		
				warGoalDateLabel.setText(war.getOriginalWarGoal().getDate());		
				warGoalScoreLabel.setText(Double.toString(war.getOriginalWarGoal().getScore()));		
				warGoalChangeLabel.setText(Double.toString(war.getOriginalWarGoal().getChange()));		
				warGoalFulfilledLabel.setText(war.getOriginalWarGoal().getFulfilled().toString());		
			}
			else {
				warGoalActorLabel.setText(findOfficalName(war.getWarGoalList()[0].getActor()));		
				warGoalReceiverLabel.setText(findOfficalName(war.getWarGoalList()[0].getReceiver()));		
				warGoalCBLabel.setText(war.getWarGoalList()[0].getCasus_belli());		
				warGoalCountryLabel.setText(findOfficalName(war.getWarGoalList()[0].getCountry()));		
				warGoalStateLabel.setText(Integer.toString(war.getWarGoalList()[0].getState_province_id()));		
				warGoalDateLabel.setText(war.getWarGoalList()[0].getDate());		
				warGoalScoreLabel.setText(Double.toString(war.getWarGoalList()[0].getScore()));		
				warGoalChangeLabel.setText(Double.toString(war.getWarGoalList()[0].getChange()));		
				warGoalFulfilledLabel.setText(war.getWarGoalList()[0].getFulfilled().toString());	
			}
			/* Showing the disabled labels */
			warGoalDateLabel.setVisible(true);
			warGoalDateHelper.setVisible(true);
			warGoalScoreLabel.setVisible(true);
			warGoalScoreHelper.setVisible(true);
			warGoalChangeLabel.setVisible(true);
			warGoalChangeHelper.setVisible(true);
			warGoalFulfilledLabel.setVisible(true);
			warGoalFulfilledHelper.setVisible(true);
		}
		else {	
			try {
				warGoalActorLabel.setText(findOfficalName(war.getWarGoalList()[0].getActor()));		
				warGoalReceiverLabel.setText(findOfficalName(war.getWarGoalList()[0].getReceiver()));		
				warGoalCBLabel.setText(war.getWarGoalList()[0].getCasus_belli());		
				warGoalCountryLabel.setText(war.getWarGoalList()[0].getCountry());		
				warGoalStateLabel.setText(Integer.toString(war.getWarGoalList()[0].getState_province_id()));
			} catch (IndexOutOfBoundsException e) {
				/* Setting values to blank as some wars don't have any wargoals */
				warGoalActorLabel.setText("");		
				warGoalReceiverLabel.setText("");		
				warGoalCBLabel.setText("");		
				warGoalCountryLabel.setText("");		
				warGoalStateLabel.setText("");
			}
			/* Disabling unused labels */
			warGoalDateLabel.setVisible(false);
			warGoalDateHelper.setVisible(false);
			warGoalScoreLabel.setVisible(false);
			warGoalScoreHelper.setVisible(false);
			warGoalChangeLabel.setVisible(false);
			warGoalChangeHelper.setVisible(false);
			warGoalFulfilledLabel.setVisible(false);
			warGoalFulfilledHelper.setVisible(false);
		}
	}
	
	/** Here because I didn't want to make a separate class for only this method.
	 * Sets the column values for wargoaltab. If the version is HoD, more column are shown. 
	 * Otherwise they are hidden. 
	 */
	public static void setWarGoalTabColumnValues() {
		/* Wargoal tab columns */
        getColWarGoalActor().setCellValueFactory(new PropertyValueFactory<WarGoal, String>("actor"));
        getColWarGoalReceiver().setCellValueFactory(new PropertyValueFactory<WarGoal, String>("receiver"));
        getColWarGoalCasusBelli().setCellValueFactory(new PropertyValueFactory<WarGoal, String>("casus_belli"));
        getColWarGoalCountry().setCellValueFactory(new PropertyValueFactory<WarGoal, String>("country"));
        getColWarGoalStateID().setCellValueFactory(new PropertyValueFactory<WarGoal, Integer>("state_province_id"));
        /* Giving values to columns only if the game is HoD. 
         * Also hiding some columns if there is nothing to put in them*/
        if (FileLoader.checkVersion()) {
            getColWarGoalDate().setCellValueFactory(new PropertyValueFactory<WarGoal, String>("date"));
            getColWarGoalScore().setCellValueFactory(new PropertyValueFactory<WarGoal, Double>("score"));
            getColWarGoalChange().setCellValueFactory(new PropertyValueFactory<WarGoal, Double>("change"));
            getColWarGoalFulfilled().setCellValueFactory(new PropertyValueFactory<WarGoal, Result>("fulfilled"));
        	getColWarGoalDate().setVisible(true);
        	getColWarGoalScore().setVisible(true);
        	getColWarGoalChange().setVisible(true);
        	getColWarGoalFulfilled().setVisible(true);
        }	
        else {
        	getColWarGoalDate().setVisible(false);
        	getColWarGoalScore().setVisible(false);
        	getColWarGoalChange().setVisible(false);
        	getColWarGoalFulfilled().setVisible(false);
        }
	}

}

