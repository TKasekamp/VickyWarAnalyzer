package ee.tkasekamp.vickywaranalyzer.controller.tab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;
import ee.tkasekamp.vickywaranalyzer.controller.WarCountryBox;
import ee.tkasekamp.vickywaranalyzer.core.Battle;
import ee.tkasekamp.vickywaranalyzer.core.Battle.Result;
import ee.tkasekamp.vickywaranalyzer.core.Battle.Type;
import ee.tkasekamp.vickywaranalyzer.core.War;
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
	private WarCountryBox attackerBox;
	@FXML
	private WarCountryBox defenderBox;

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

}
