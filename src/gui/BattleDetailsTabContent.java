package gui;

import static gui.GuiController.attackerUnitsTable;
import static gui.GuiController.attackerUnitsTableContent;
import static gui.GuiController.battleAttacker;
import static gui.GuiController.battleAttackerArmySize;
import static gui.GuiController.battleAttackerFlag;
import static gui.GuiController.battleAttackerLeader;
import static gui.GuiController.battleAttackerLosses;
import static gui.GuiController.battleDate;
import static gui.GuiController.battleDefender;
import static gui.GuiController.battleDefenderArmySize;
import static gui.GuiController.battleDefenderFlag;
import static gui.GuiController.battleDefenderLeader;
import static gui.GuiController.battleDefenderLosses;
import static gui.GuiController.battleDetailsTab;
import static gui.GuiController.battleLocation;
import static gui.GuiController.battleName;
import static gui.GuiController.battleResult;
import static gui.GuiController.battleTotalLosses;
import static gui.GuiController.battleType;
import static gui.GuiController.colAttackerUnitNumber;
import static gui.GuiController.colAttackerUnitType;
import static gui.GuiController.colDefenderUnitNumber;
import static gui.GuiController.colDefenderUnitType;
import static gui.GuiController.defenderUnitsTable;
import static gui.GuiController.defenderUnitsTableContent;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Battle;
import main.Country;
import main.Reference;
import main.Unit;

/** Handles stuff in the battle details tab.
 * Works similar to WarDetailsTabContent*/
public class BattleDetailsTabContent {
	private static Battle battle;
	
	
	/** Uses the battle from war details tab to give values to labels and tables
	 * 
	 * @param battle
	 */
	public static void battleDetailsTabPopulate(Battle b) {
		battle = b;
		/* Giving values to tab and center info box */
		battleDetailsTab.setText("Battle of " +battle.getName());
		battleName.setText("Battle of " +battle.getName());
		battleDate.setText(battle.getDate());
		battleType.setText(battle.getBattleType().toString());
		battleLocation.setText(Integer.toString(battle.getLocation()));
		battleResult.setText(battle.getRes().toString());
		battleAttackerLeader.setText(battle.getLeaderAttacker());
		battleDefenderLeader.setText(battle.getLeaderDefender());
		battleTotalLosses.setText(Integer.toString(battle.getTotalLosses()));
		
		attackerPopulate();
		defenderPopulate();
		
	}
	private static void attackerPopulate() {
		/* Finding the main flag */
		for (Country country : Reference.countryList) {
			if (battle.getAttacker().equals(country.getTag())) {
				battleAttackerFlag.setImage(country.getFlag());
			}
		}
		battleAttacker.setText(battle.getAttackerOfficial());
		/* Clearing table contents */
		attackerUnitsTableContent.clear();
		/* Army size and adding to table list*/
		int size = 0;
		for (Unit unit: battle.getAttackerUnits()) {
			size+= unit.getNumber();
			attackerUnitsTableContent.add(unit);
		}
		battleAttackerArmySize.setText(Integer.toString(size));
		battleAttackerLosses.setText(Integer.toString(battle.getAttackerLosses()));
		
		attackerUnitsTable.setItems(attackerUnitsTableContent);
	}
	private static void defenderPopulate() {
		/* Finding the main flag */
		for (Country country : Reference.countryList) {
			if (battle.getDefender().equals(country.getTag())) {
				battleDefenderFlag.setImage(country.getFlag());
			}
		}
		battleDefender.setText(battle.getDefenderOfficial());
		/* Clearing table contents */
		defenderUnitsTableContent.clear();
		/* Army size and adding to table list*/
		int size = 0;
		for (Unit unit: battle.getDefenderUnits()) {
			size+= unit.getNumber();
			defenderUnitsTableContent.add(unit);
		}
		battleDefenderArmySize.setText(Integer.toString(size));
		battleDefenderLosses.setText(Integer.toString(battle.getDefenderLosses()));
		
		defenderUnitsTable.setItems(defenderUnitsTableContent);
	}
	/** Defines columns in attackerUnitTable and defenderUnitTable 
	 */
	public static void setColumnValues() {
		/* Attacker side */
		colAttackerUnitType.setCellValueFactory(new PropertyValueFactory<Unit, String>("type"));
		colAttackerUnitNumber.setCellValueFactory(new PropertyValueFactory<Unit, Integer>("number"));
		/* Defender side */
		colDefenderUnitType.setCellValueFactory(new PropertyValueFactory<Unit, String>("type"));
		colDefenderUnitNumber.setCellValueFactory(new PropertyValueFactory<Unit, Integer>("number"));
	}
}
