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
	private GuiController controller;

	public WarDetailsTabContent(GuiController controller) {
		this.controller = controller;
	}

	/**
	 * This is the master method which will fill all the tables and labels in
	 * war details tab
	 */
	public void warDetailsTabPopulate(War a) {
		war = a;
		/* Set the name of the tab */
		controller.getWarDetailsTab().setText(war.getName());
		controller.getWarDetailsTab().setDisable(false);
		/* Basic information about the war */
		controller.getWarNameLabel().setText(war.getName());
		controller.getWarStartDateLabel().setText(war.getStartDate());
		controller.getWarEndDateLabel().setText(war.getEndDate());
		controller.getWarActionLabel().setText(war.getAction());
		if (war.isActive()) {
			controller.getWarHasEndedLabel().setText("No");
		} else {
			controller.getWarHasEndedLabel().setText("Yes");
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
	private void attackerPopulate() {
		/* If there is no attacker, the flag will be the original attacker's */
		if (war.getAttacker().equals("")) {
			for (Country country : Reference.countryList) {
				if (war.getOriginalAttacker().equals(country.getTag())) {
					controller.getAttackerFlag().setImage(country.getFlag());
					controller.getOriginalAttackerLabel().setText(
							country.getOfficialName());
				}
			}
			// Hiding the labels as there is no use for them
			controller.getWarAttacker().setVisible(false);
			controller.getWarAttackerHelper().setVisible(false);
		} else {
			for (Country country : Reference.countryList) {
				if (war.getAttacker().equals(country.getTag())) {
					controller.getAttackerFlag().setImage(country.getFlag());

				}
			}
			// Showing labels and giving value
			controller.getWarAttacker().setVisible(true);
			controller.getWarAttackerHelper().setVisible(true);
			controller.getWarAttacker().setText(
					war.getOriginalAttackerOfficial());
		}

		// originalAttackerLabel.setText(war.getOriginalAttacker()); //
		// temporary solution

		/* Attacker table */
		controller.getAttackerTableContent().clear(); // Clearing list

		/*
		 * Comparing war.countrylist items to reference.countrylist items Join
		 * type true for attackers
		 */
		for (int i = 0; i < war.getCountryList().length; i++) {
			for (Country country : Reference.countryList) {
				if (country.getTag().equals(war.getCountryList()[i].getTag())
						&& war.getCountryList()[i].isJoinType()) {
					/* Creating a row with items from both classes */
					ObservableJoinedCountry temp = new ObservableJoinedCountry(
							country.getOfficialName(), country.getFlag(),
							war.getCountryList()[i].getStartDate(),
							war.getCountryList()[i].getEndDate());
					controller.getAttackerTableContent().add(temp);
					break;

				}
			}
		}

		/* Adding the countries to table */
		controller.getAttackerTable().setItems(
				controller.getAttackerTableContent());

	}

	/** Sets all labels at the defenders side except losses */
	private void defenderPopulate() {
		/* Finding the main flag */
		if (war.getDefender().equals("")) {
			for (Country country : Reference.countryList) {
				if (war.getOriginalDefender().equals(country.getTag())) {
					controller.getDefenderFlag().setImage(country.getFlag());
					controller.getOriginalDefenderLabel().setText(
							country.getOfficialName());
				}
			}
			// Hiding the labels as there is no use for them
			controller.getWarDefender().setVisible(false);
			controller.getWarDefenderHelper().setVisible(false);

		}
		/* War is active and there is a defender */
		else {
			for (Country country : Reference.countryList) {
				if (war.getDefender().equals(country.getTag())) {
					controller.getDefenderFlag().setImage(country.getFlag());

				}
			}
			// Showing labels and giving value
			controller.getWarDefender().setVisible(true);
			controller.getWarDefenderHelper().setVisible(true);
			controller.getWarDefender().setText(
					Country.findOfficalName(war.getDefender()));
			controller.getOriginalDefenderLabel().setText(
					war.getOriginalDefenderOfficial());
		}
		// originalDefenderLabel.setText(war.getOriginalDefender()); //
		// temporary solution

		/* Defender list */
		controller.getDefenderTableContent().clear(); // Clearing list

		/*
		 * Comparing war.countrylist items to reference.countrylist items Join
		 * type true for attackers. Creating labels with flags to add to the
		 * list
		 */
		for (int i = 0; i < war.getCountryList().length; i++) {
			for (Country country : Reference.countryList) {
				if (country.getTag().equals(war.getCountryList()[i].getTag())
						&& !war.getCountryList()[i].isJoinType()) {
					ObservableJoinedCountry temp = new ObservableJoinedCountry(
							country.getOfficialName(), country.getFlag(),
							war.getCountryList()[i].getStartDate(),
							war.getCountryList()[i].getEndDate());
					controller.getDefenderTableContent().add(temp);
					break;

				}
			}
		}
		/* Adding the countries to table */
		controller.getDefenderTable().setItems(
				controller.getDefenderTableContent());
	}

	/** Sets all labels at the attackers side except losses */
	private void battleTablePopulate() {
		controller.getBattleTableContent().clear(); // Clearing before adding
													// new items

		/* Adding battles to list */
		for (Battle item : war.getBattleList()) {
			controller.getBattleTableContent().add(item);
		}
		/* Displaying battles in table */
		controller.getBattleTable()
				.setItems(controller.getBattleTableContent());

	}

	/** Sets labels for losses */
	private void lossesPopulate() {

		int attackerTotalLosses = 0;
		int attackerTotalShipLosses = 0;
		int defenderTotalLosses = 0;
		int defenderTotalShipLosses = 0;

		/*
		 * The most complicated for loop EVER. If you want to change it, good
		 * luck. Basically it looks at the battleList and compares the attacker
		 * and defender to the countryList. It also checks if the battle was
		 * land or naval and adds them to the total.
		 */
		for (Battle battle : war.getBattleList()) {
			for (int i = 0; i < war.getCountryList().length; i++) {
				if (battle.getAttacker().equals(
						war.getCountryList()[i].getTag())) {
					if (war.getCountryList()[i].isJoinType()) {
						if (battle.getBattleType() == Type.LAND) {
							attackerTotalLosses = attackerTotalLosses
									+ battle.getAttackerLosses();
						} else {
							attackerTotalShipLosses = attackerTotalShipLosses
									+ battle.getAttackerLosses();
						}
					} else {
						if (battle.getBattleType() == Type.LAND) {
							defenderTotalLosses = defenderTotalLosses
									+ battle.getDefenderLosses();
						} else {
							defenderTotalShipLosses = defenderTotalShipLosses
									+ battle.getDefenderLosses();
						}
					}

				}

				if (battle.getDefender().equals(
						war.getCountryList()[i].getTag())) {
					if (war.getCountryList()[i].isJoinType()) {
						if (battle.getBattleType() == Type.LAND) {
							attackerTotalLosses = attackerTotalLosses
									+ battle.getAttackerLosses();
						} else {
							attackerTotalShipLosses = attackerTotalShipLosses
									+ battle.getAttackerLosses();
						}
					} else {
						if (battle.getBattleType() == Type.LAND) {
							defenderTotalLosses = defenderTotalLosses
									+ battle.getDefenderLosses();
						} else {
							defenderTotalShipLosses = defenderTotalShipLosses
									+ battle.getDefenderLosses();
						}
					}
				}
			}

		}

		/* Attacker losses */
		controller.getAttackerTotalLossesLabel().setText(
				Integer.toString(attackerTotalLosses));
		controller.getAttackerTotalShipLossesLabel().setText(
				Integer.toString(attackerTotalShipLosses));
		/* Defender losses */
		controller.getDefenderTotalLossesLabel().setText(
				Integer.toString(defenderTotalLosses));
		controller.getDefenderTotalShipLossesLabel().setText(
				Integer.toString(defenderTotalShipLosses));
		/* Total losses */
		controller.getWarTotalLossesLabel().setText(
				Integer.toString(attackerTotalLosses + defenderTotalLosses));
		controller.getWarTotalShipLossesLabel().setText(
				Integer.toString(attackerTotalShipLosses
						+ defenderTotalShipLosses));

	}

	/** Handels everthing in the war details tab */
	private void warGoalTabPopulate() {
		/* Show the tab */
		controller.getWarGoalTab().setDisable(false);
		controller.getWarGoalTableContent().clear(); // A bit of cleaning

		// Adding to list
		for (WarGoal goal : war.getWarGoalList()) {
			controller.getWarGoalTableContent().add(goal);
		}

		controller.getWarGoalTable().setItems(
				controller.getWarGoalTableContent());
	}

	/**
	 * This method is run once on startup and it defines the values of columns
	 * in the war tab and the wargoaltab.
	 */
	public void setColumnValues() {
		/* Connecting the War fields with warTable columns */
		controller.getColNameBattle().setCellValueFactory(
				new PropertyValueFactory<Battle, String>("name"));
		controller.getColDateBattle().setCellValueFactory(
				new PropertyValueFactory<Battle, String>("date"));
		controller.getColDefenderBattle().setCellValueFactory(
				new PropertyValueFactory<Battle, String>("defenderOfficial"));
		controller.getColAttackerBattle().setCellValueFactory(
				new PropertyValueFactory<Battle, String>("attackerOfficial"));
		controller.getColTypeBattle().setCellValueFactory(
				new PropertyValueFactory<Battle, Type>("battleType"));
		controller.getColBattleResult().setCellValueFactory(
				new PropertyValueFactory<Battle, Result>("res"));
		controller.getColBattleTotalLosses().setCellValueFactory(
				new PropertyValueFactory<Battle, Integer>("totalLosses"));

		/* Defender table */
		controller.getColDefenderName().setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"officialName"));
		controller.getColDefenderFlag().setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, ImageView>(
						"flag"));
		controller.getColDefenderStartDate().setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"joinDate"));
		controller.getColDefenderEndDate().setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"endDate"));

		/* Attacker table */
		controller.getColAttackerName().setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"officialName"));
		controller.getColAttackerFlag().setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, ImageView>(
						"flag"));
		controller.getColAttackerStartDate().setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"joinDate"));
		controller.getColAttackerEndDate().setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"endDate"));
	}

	/** Gives values to the labels related to wargoals */
	private void originalWarGoalPopulate() {
		controller.getWarName2().setText(war.getName());

		/* True if the version is HoD. Otherwise some labels will be hidden */
		if (FileLoader.checkVersion()) {
			/*
			 * Checking if this particular war has an original wargoal.
			 * Otherwise the first wargoal in the wargoalList will be used
			 */
			if (!(war.getOriginalWarGoal().getActor().equals(""))) {
				controller.getWarGoalActorLabel().setText(
						Country.findOfficalName(war.getOriginalWarGoal()
								.getActor()));
				controller.getWarGoalReceiverLabel()
						.setText(
								findOfficalName(war.getOriginalWarGoal()
										.getReceiver()));
				controller.getWarGoalCBLabel().setText(
						war.getOriginalWarGoal().getCasus_belli());
				controller.getWarGoalCountryLabel().setText(
						findOfficalName(war.getOriginalWarGoal().getCountry()));
				controller.getWarGoalStateLabel().setText(
						Integer.toString(war.getOriginalWarGoal()
								.getState_province_id()));
				controller.getWarGoalDateLabel().setText(
						war.getOriginalWarGoal().getDate());
				controller.getWarGoalScoreLabel().setText(
						Double.toString(war.getOriginalWarGoal().getScore()));
				controller.getWarGoalChangeLabel().setText(
						Double.toString(war.getOriginalWarGoal().getChange()));
				controller.getWarGoalFulfilledLabel().setText(
						war.getOriginalWarGoal().getFulfilled().toString());
			} else {
				controller.getWarGoalActorLabel().setText(
						findOfficalName(war.getWarGoalList()[0].getActor()));
				controller.getWarGoalReceiverLabel().setText(
						findOfficalName(war.getWarGoalList()[0].getReceiver()));
				controller.getWarGoalCBLabel().setText(
						war.getWarGoalList()[0].getCasus_belli());
				controller.getWarGoalCountryLabel().setText(
						findOfficalName(war.getWarGoalList()[0].getCountry()));
				controller.getWarGoalStateLabel().setText(
						Integer.toString(war.getWarGoalList()[0]
								.getState_province_id()));
				controller.getWarGoalDateLabel().setText(
						war.getWarGoalList()[0].getDate());
				controller.getWarGoalScoreLabel().setText(
						Double.toString(war.getWarGoalList()[0].getScore()));
				controller.getWarGoalChangeLabel().setText(
						Double.toString(war.getWarGoalList()[0].getChange()));
				controller.getWarGoalFulfilledLabel().setText(
						war.getWarGoalList()[0].getFulfilled().toString());
			}
			/* Showing the disabled labels */
			controller.getWarGoalDateLabel().setVisible(true);
			controller.getWarGoalDateHelper().setVisible(true);
			controller.getWarGoalScoreLabel().setVisible(true);
			controller.getWarGoalScoreHelper().setVisible(true);
			controller.getWarGoalChangeLabel().setVisible(true);
			controller.getWarGoalChangeHelper().setVisible(true);
			controller.getWarGoalFulfilledLabel().setVisible(true);
			controller.getWarGoalFulfilledHelper().setVisible(true);
		} else {
			try {
				controller.getWarGoalActorLabel().setText(
						findOfficalName(war.getWarGoalList()[0].getActor()));
				controller.getWarGoalReceiverLabel().setText(
						findOfficalName(war.getWarGoalList()[0].getReceiver()));
				controller.getWarGoalCBLabel().setText(
						war.getWarGoalList()[0].getCasus_belli());
				controller.getWarGoalCountryLabel().setText(
						war.getWarGoalList()[0].getCountry());
				controller.getWarGoalStateLabel().setText(
						Integer.toString(war.getWarGoalList()[0]
								.getState_province_id()));
			} catch (IndexOutOfBoundsException e) {
				/* Setting values to blank as some wars don't have any wargoals */
				controller.getWarGoalActorLabel().setText("");
				controller.getWarGoalReceiverLabel().setText("");
				controller.getWarGoalCBLabel().setText("");
				controller.getWarGoalCountryLabel().setText("");
				controller.getWarGoalStateLabel().setText("");
			}
			/* Disabling unused labels */
			controller.getWarGoalDateLabel().setVisible(false);
			controller.getWarGoalDateHelper().setVisible(false);
			controller.getWarGoalScoreLabel().setVisible(false);
			controller.getWarGoalScoreHelper().setVisible(false);
			controller.getWarGoalChangeLabel().setVisible(false);
			controller.getWarGoalChangeHelper().setVisible(false);
			controller.getWarGoalFulfilledLabel().setVisible(false);
			controller.getWarGoalFulfilledHelper().setVisible(false);
		}
	}

	/**
	 * Here because I didn't want to make a separate class for only this method.
	 * Sets the column values for wargoaltab. If the version is HoD, more column
	 * are shown. Otherwise they are hidden.
	 */
	public void setWarGoalTabColumnValues() {
		/* Wargoal tab columns */
		controller.getColWarGoalActor().setCellValueFactory(
				new PropertyValueFactory<WarGoal, String>("actor"));
		controller.getColWarGoalReceiver().setCellValueFactory(
				new PropertyValueFactory<WarGoal, String>("receiver"));
		controller.getColWarGoalCasusBelli().setCellValueFactory(
				new PropertyValueFactory<WarGoal, String>("casus_belli"));
		controller.getColWarGoalCountry().setCellValueFactory(
				new PropertyValueFactory<WarGoal, String>("country"));
		controller.getColWarGoalStateID()
				.setCellValueFactory(
						new PropertyValueFactory<WarGoal, Integer>(
								"state_province_id"));
		/*
		 * Giving values to columns only if the game is HoD. Also hiding some
		 * columns if there is nothing to put in them
		 */
		if (FileLoader.checkVersion()) {
			controller.getColWarGoalDate().setCellValueFactory(
					new PropertyValueFactory<WarGoal, String>("date"));
			controller.getColWarGoalScore().setCellValueFactory(
					new PropertyValueFactory<WarGoal, Double>("score"));
			controller.getColWarGoalChange().setCellValueFactory(
					new PropertyValueFactory<WarGoal, Double>("change"));
			controller.getColWarGoalFulfilled().setCellValueFactory(
					new PropertyValueFactory<WarGoal, Result>("fulfilled"));
			controller.getColWarGoalDate().setVisible(true);
			controller.getColWarGoalScore().setVisible(true);
			controller.getColWarGoalChange().setVisible(true);
			controller.getColWarGoalFulfilled().setVisible(true);
		} else {
			controller.getColWarGoalDate().setVisible(false);
			controller.getColWarGoalScore().setVisible(false);
			controller.getColWarGoalChange().setVisible(false);
			controller.getColWarGoalFulfilled().setVisible(false);
		}
	}

}
