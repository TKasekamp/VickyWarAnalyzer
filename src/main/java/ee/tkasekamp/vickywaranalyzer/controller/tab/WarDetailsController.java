package ee.tkasekamp.vickywaranalyzer.controller.tab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;
import ee.tkasekamp.vickywaranalyzer.core.Battle;
import ee.tkasekamp.vickywaranalyzer.core.Battle.Result;
import ee.tkasekamp.vickywaranalyzer.core.Battle.Type;
import ee.tkasekamp.vickywaranalyzer.core.War;
import ee.tkasekamp.vickywaranalyzer.gui.ObservableJoinedCountry;
import ee.tkasekamp.vickywaranalyzer.service.ModelService;

public class WarDetailsController extends AbstractController {

	@FXML
	private ImageView attackerFlag;

	@FXML
	private Label originalAttackerLabel;

	@FXML
	private Label attackerTotalLossesLabel;

	@FXML
	private Label attackerTotalShipLossesLabel;

	@FXML
	private TableView<ObservableJoinedCountry> attackerTable;

	@FXML
	private TableColumn<ObservableJoinedCountry, ImageView> colAttackerFlag;

	@FXML
	private TableColumn<ObservableJoinedCountry, String> colAttackerName;

	@FXML
	private TableColumn<ObservableJoinedCountry, String> colAttackerStartDate;

	@FXML
	private TableColumn<ObservableJoinedCountry, String> colAttackerEndDate;

	@FXML
	private Label warAttackerHelper;

	@FXML
	private Label warAttacker;

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
	private ImageView defenderFlag;

	@FXML
	private Label originalDefenderLabel;

	@FXML
	private Label defenderTotalLossesLabel;

	@FXML
	private Label defenderTotalShipLossesLabel;

	@FXML
	private TableView<ObservableJoinedCountry> defenderTable;

	@FXML
	private TableColumn<ObservableJoinedCountry, ImageView> colDefenderFlag;

	@FXML
	private TableColumn<ObservableJoinedCountry, String> colDefenderName;

	@FXML
	private TableColumn<ObservableJoinedCountry, String> colDefenderStartDate;

	@FXML
	private TableColumn<ObservableJoinedCountry, String> colDefenderEndDate;

	@FXML
	private Label warDefenderHelper;

	@FXML
	private Label warDefender;

	private MainController main;
	private Tab tab;
	private ModelService modelService;

	public void init(MainController mainController, ModelService modelService,
			Tab tab) {
		main = mainController;
		this.tab = tab;
		this.modelService = modelService;
		setColumnValues();
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
	}
	
	/**
	 * This method is run once on startup and it defines the values of columns
	 * in the war tab and the wargoaltab.
	 */
	private void setColumnValues() {
		/* Connecting the War fields with warTable columns */
		colNameBattle.setCellValueFactory(
				new PropertyValueFactory<Battle, String>("name"));
		colDateBattle.setCellValueFactory(
				new PropertyValueFactory<Battle, String>("date"));
		colDefenderBattle.setCellValueFactory(
				new PropertyValueFactory<Battle, String>("defenderOfficial"));
		colAttackerBattle.setCellValueFactory(
				new PropertyValueFactory<Battle, String>("attackerOfficial"));
		colTypeBattle.setCellValueFactory(
				new PropertyValueFactory<Battle, Type>("battleType"));
		colBattleResult.setCellValueFactory(
				new PropertyValueFactory<Battle, Result>("res"));
		colBattleTotalLosses.setCellValueFactory(
				new PropertyValueFactory<Battle, Integer>("totalLosses"));

		/* Defender table */
		colDefenderName.setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"officialName"));
		colDefenderFlag.setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, ImageView>(
						"flag"));
		colDefenderStartDate.setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"joinDate"));
		colDefenderEndDate.setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"endDate"));

		/* Attacker table */
		colAttackerName.setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"officialName"));
		colAttackerFlag.setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, ImageView>(
						"flag"));
		colAttackerStartDate.setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"joinDate"));
		colAttackerEndDate.setCellValueFactory(
				new PropertyValueFactory<ObservableJoinedCountry, String>(
						"endDate"));
	}

}
