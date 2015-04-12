package ee.tkasekamp.vickywaranalyzer.gui;

import ee.tkasekamp.vickywaranalyzer.core.Battle;
import ee.tkasekamp.vickywaranalyzer.core.Country;
import ee.tkasekamp.vickywaranalyzer.core.Unit;
import ee.tkasekamp.vickywaranalyzer.util.Reference;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Handles stuff in the battle details tab. Works similar to
 * WarDetailsTabContent
 */
public class BattleDetailsTabContent {
	private static Battle battle;
	private GuiController controller;

	public BattleDetailsTabContent(GuiController controller) {
		this.controller = controller;
	}

	/**
	 * Uses the battle from war details tab to give values to labels and tables
	 * 
	 * @param battle
	 */
	public void battleDetailsTabPopulate(Battle b) {
		battle = b;
		/* Giving values to tab and center info box */
		controller.getBattleDetailsTab().setText(
				"Battle of " + battle.getName());
		controller.getBattleName().setText("Battle of " + battle.getName());
		controller.getBattleDate().setText(battle.getDate());
		controller.getBattleType().setText(battle.getBattleType().toString());
		controller.getBattleLocation().setText(
				Integer.toString(battle.getLocation()));
		controller.getBattleResult().setText(battle.getRes().toString());
		controller.getBattleAttackerLeader()
				.setText(battle.getLeaderAttacker());
		controller.getBattleDefenderLeader()
				.setText(battle.getLeaderDefender());
		controller.getBattleTotalLosses().setText(
				Integer.toString(battle.getTotalLosses()));

		attackerPopulate();
		defenderPopulate();

	}

	private void attackerPopulate() {
		/* Finding the main flag */
		for (Country country : Reference.countryList) {
			if (battle.getAttacker().equals(country.getTag())) {
				controller.getBattleAttackerFlag().setImage(country.getFlag());
			}
		}
		controller.getBattleAttacker().setText(battle.getAttackerOfficial());
		/* Clearing table contents */
		controller.getAttackerUnitsTableContent().clear();
		/* Army size and adding to table list */
		int size = 0;
		for (Unit unit : battle.getAttackerUnits()) {
			size += unit.getNumber();
			controller.getAttackerUnitsTableContent().add(unit);
		}
		controller.getBattleAttackerArmySize().setText(Integer.toString(size));
		controller.getBattleAttackerLosses().setText(
				Integer.toString(battle.getAttackerLosses()));

		controller.getAttackerUnitsTable().setItems(
				controller.getAttackerUnitsTableContent());
	}

	private void defenderPopulate() {
		/* Finding the main flag */
		for (Country country : Reference.countryList) {
			if (battle.getDefender().equals(country.getTag())) {
				controller.getBattleDefenderFlag().setImage(country.getFlag());
			}
		}
		controller.getBattleDefender().setText(battle.getDefenderOfficial());
		/* Clearing table contents */
		controller.getDefenderUnitsTableContent().clear();
		/* Army size and adding to table list */
		int size = 0;
		for (Unit unit : battle.getDefenderUnits()) {
			size += unit.getNumber();
			controller.getDefenderUnitsTableContent().add(unit);
		}
		controller.getBattleDefenderArmySize().setText(Integer.toString(size));
		controller.getBattleDefenderLosses().setText(
				Integer.toString(battle.getDefenderLosses()));

		controller.getDefenderUnitsTable().setItems(
				controller.getDefenderUnitsTableContent());
	}

	/**
	 * Defines columns in attackerUnitTable and defenderUnitTable
	 */
	public void setColumnValues() {
		/* Attacker side */
		controller.getColAttackerUnitType().setCellValueFactory(
				new PropertyValueFactory<Unit, String>("type"));
		controller.getColAttackerUnitNumber().setCellValueFactory(
				new PropertyValueFactory<Unit, Integer>("number"));
		/* Defender side */
		controller.getColDefenderUnitType().setCellValueFactory(
				new PropertyValueFactory<Unit, String>("type"));
		controller.getColDefenderUnitNumber().setCellValueFactory(
				new PropertyValueFactory<Unit, Integer>("number"));
	}
}
