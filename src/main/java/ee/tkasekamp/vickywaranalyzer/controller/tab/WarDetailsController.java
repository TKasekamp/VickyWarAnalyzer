package ee.tkasekamp.vickywaranalyzer.controller.tab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;
import ee.tkasekamp.vickywaranalyzer.controller.WarCountryBox;
import ee.tkasekamp.vickywaranalyzer.core.Battle;
import ee.tkasekamp.vickywaranalyzer.core.Country;
import ee.tkasekamp.vickywaranalyzer.core.Battle.Result;
import ee.tkasekamp.vickywaranalyzer.core.Battle.Type;
import ee.tkasekamp.vickywaranalyzer.core.War;
import ee.tkasekamp.vickywaranalyzer.gui.FileLoader;
import ee.tkasekamp.vickywaranalyzer.service.ModelService;

public class WarDetailsController extends AbstractController {

	@FXML
	private TableView<Battle> battleTable;

	@FXML
	private TableColumn<Battle, String> colNameBattle;

	@FXML
	private TableColumn<Battle, String> colDateBattle;

	@FXML
	private TableColumn<Battle, String> colAttackerBattle;

	@FXML
	private TableColumn<Battle, String> colDefenderBattle;

	@FXML
	private TableColumn<Battle, Type> colTypeBattle;

	@FXML
	private TableColumn<Battle, Result> colBattleResult;

	@FXML
	private TableColumn<Battle, Integer> colBattleTotalLosses;

	@FXML
	private Label warNameLabel;

	@FXML
	private Label warStartDateLabel;

	@FXML
	private Label warEndDateLabel;

	@FXML
	private Label warTotalLossesLabel;

	@FXML
	private Label warTotalShipLossesLabel;

	@FXML
	private Label warActionLabel;

	@FXML
	private Label warHasEndedLabel;

	@FXML
	private Label warGoalActorLabel;

	@FXML
	private Label warGoalReceiverLabel;

	@FXML
	private Label warGoalCBLabel;

	@FXML
	private Label warGoalCountryLabel;

	@FXML
	private Label warGoalScoreHelper;

	@FXML
	private Label warGoalScoreLabel;

	@FXML
	private Label warGoalChangeHelper;

	@FXML
	private Label warGoalChangeLabel;

	@FXML
	private Label warGoalDateHelper;

	@FXML
	private Label warGoalDateLabel;

	@FXML
	private Label warGoalFulfilledHelper;

	@FXML
	private Label warGoalFulfilledLabel;

	@FXML
	private Label warGoalStateLabel;

	@FXML
	private WarCountryBox attackerBoxController;
	@FXML
	private WarCountryBox defenderBoxController;

	private ObservableList<Battle> battleTableContent;
	private MainController main;
	private Tab tab;
	private ModelService modelService;

	public void init(MainController mainController, ModelService modelService,
			Tab tab) {
		main = mainController;
		this.tab = tab;
		this.modelService = modelService;
		setColumnValues();
		attackerBoxController.init(modelService, "Attacker");
		defenderBoxController.init(modelService, "Defender");
		battleTableContent = FXCollections.observableArrayList();
	}

	@Override
	public void reset() {
		tab.setDisable(true);
		tab.setText("War");

	}

	public void populate(War war) {
		/* Set the name of the tab */
		tab.setText(war.getName());
		tab.setDisable(false);
		/* Basic information about the war */
		warNameLabel.setText(war.getName());
		warStartDateLabel.setText(war.getStartDate());
		warEndDateLabel.setText(war.getEndDate());
		warActionLabel.setText(war.getAction());
		if (war.isActive()) {
			warHasEndedLabel.setText("No");
		} else {
			warHasEndedLabel.setText("Yes");
		}

		attackerBoxController.populate(war);
		defenderBoxController.populate(war);
		battleTablePopulate(war);
		lossesPopulate(war);
		originalWarGoalPopulate(war);
	}

	private void setColumnValues() {
		colNameBattle
				.setCellValueFactory(new PropertyValueFactory<Battle, String>(
						"name"));
		colDateBattle
				.setCellValueFactory(new PropertyValueFactory<Battle, String>(
						"date"));
		colDefenderBattle
				.setCellValueFactory(new PropertyValueFactory<Battle, String>(
						"defenderOfficial"));
		colAttackerBattle
				.setCellValueFactory(new PropertyValueFactory<Battle, String>(
						"attackerOfficial"));
		colTypeBattle
				.setCellValueFactory(new PropertyValueFactory<Battle, Type>(
						"battleType"));
		colBattleResult
				.setCellValueFactory(new PropertyValueFactory<Battle, Result>(
						"res"));
		colBattleTotalLosses
				.setCellValueFactory(new PropertyValueFactory<Battle, Integer>(
						"totalLosses"));

	}

	/** Sets all labels at the attackers side except losses */
	private void battleTablePopulate(War war) {
		battleTableContent.clear();

		/* Adding battles to list */
		for (Battle item : war.getBattleList()) {
			battleTableContent.add(item);
		}
		/* Displaying battles in table */
		battleTable.setItems(battleTableContent);

	}

	/** Sets labels for losses */
	private void lossesPopulate(War war) {

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
		attackerBoxController.setTotalLosses(attackerTotalLosses,
				attackerTotalShipLosses);
		/* Defender losses */
		defenderBoxController.setTotalLosses(defenderTotalLosses,
				defenderTotalShipLosses);
		/* Total losses */
		warTotalLossesLabel.setText(Integer.toString(attackerTotalLosses
				+ defenderTotalLosses));
		warTotalShipLossesLabel.setText(Integer
				.toString(attackerTotalShipLosses + defenderTotalShipLosses));

	}

	/** Gives values to the labels related to wargoals */
	private void originalWarGoalPopulate(War war) {

		/* True if the version is HoD. Otherwise some labels will be hidden */
		if (modelService.isHOD()) {
			/*
			 * Checking if this particular war has an original wargoal.
			 * Otherwise the first wargoal in the wargoalList will be used
			 */
			if (!(war.getOriginalWarGoal().getActor().equals(""))) {
				warGoalActorLabel.setText(modelService.findOfficialName(war
						.getOriginalWarGoal().getActor()));
				warGoalReceiverLabel.setText(modelService.findOfficialName(war
						.getOriginalWarGoal().getReceiver()));
				warGoalCBLabel.setText(war.getOriginalWarGoal()
						.getCasus_belli());
				warGoalCountryLabel.setText(modelService.findOfficialName(war
						.getOriginalWarGoal().getCountry()));
				warGoalStateLabel.setText(Integer.toString(war
						.getOriginalWarGoal().getState_province_id()));
				warGoalDateLabel.setText(war.getOriginalWarGoal().getDate());
				warGoalScoreLabel.setText(Double.toString(war
						.getOriginalWarGoal().getScore()));
				warGoalChangeLabel.setText(Double.toString(war
						.getOriginalWarGoal().getChange()));
				warGoalFulfilledLabel.setText(war.getOriginalWarGoal()
						.getFulfilled().toString());
			} else {
				warGoalActorLabel.setText(modelService.findOfficialName(war
						.getWarGoalList()[0].getActor()));
				warGoalReceiverLabel.setText(modelService.findOfficialName(war
						.getWarGoalList()[0].getReceiver()));
				warGoalCBLabel
						.setText(war.getWarGoalList()[0].getCasus_belli());
				warGoalCountryLabel.setText(modelService.findOfficialName(war
						.getWarGoalList()[0].getCountry()));
				warGoalStateLabel
						.setText(Integer.toString(war.getWarGoalList()[0]
								.getState_province_id()));
				warGoalDateLabel.setText(war.getWarGoalList()[0].getDate());
				warGoalScoreLabel
						.setText(Double.toString(war.getWarGoalList()[0]
								.getScore()));
				warGoalChangeLabel
						.setText(Double.toString(war.getWarGoalList()[0]
								.getChange()));
				warGoalFulfilledLabel.setText(war.getWarGoalList()[0]
						.getFulfilled().toString());
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
		} else {
			try {
				warGoalActorLabel.setText(modelService.findOfficialName(war
						.getWarGoalList()[0].getActor()));
				warGoalReceiverLabel.setText(modelService.findOfficialName(war
						.getWarGoalList()[0].getReceiver()));
				warGoalCBLabel
						.setText(war.getWarGoalList()[0].getCasus_belli());
				warGoalCountryLabel.setText(war.getWarGoalList()[0]
						.getCountry());
				warGoalStateLabel
						.setText(Integer.toString(war.getWarGoalList()[0]
								.getState_province_id()));
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

}
