package ee.tkasekamp.vickywaranalyzer.controller.tab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;

public class WarDetailsController extends AbstractController{

	@FXML
	private ImageView attackerFlag;

	@FXML
	private Label originalAttackerLabel;

	@FXML
	private Label attackerTotalLossesLabel;

	@FXML
	private Label attackerTotalShipLossesLabel;

	@FXML
	private TableView<?> attackerTable;

	@FXML
	private TableColumn<?, ?> colAttackerFlag;

	@FXML
	private TableColumn<?, ?> colAttackerName;

	@FXML
	private TableColumn<?, ?> colAttackerStartDate;

	@FXML
	private TableColumn<?, ?> colAttackerEndDate;

	@FXML
	private Label warAttackerHelper;

	@FXML
	private Label warAttacker;

	@FXML
	private TableView<?> battleTable;

	@FXML
	private TableColumn<?, ?> colNameBattle;

	@FXML
	private TableColumn<?, ?> colDateBattle;

	@FXML
	private TableColumn<?, ?> colAttackerBattle;

	@FXML
	private TableColumn<?, ?> colDefenderBattle;

	@FXML
	private TableColumn<?, ?> colTypeBattle;

	@FXML
	private TableColumn<?, ?> colBattleResult;

	@FXML
	private TableColumn<?, ?> colBattleTotalLosses;

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
	private TableView<?> defenderTable;

	@FXML
	private TableColumn<?, ?> colDefenderFlag;

	@FXML
	private TableColumn<?, ?> colDefenderName;

	@FXML
	private TableColumn<?, ?> colDefenderStartDate;

	@FXML
	private TableColumn<?, ?> colDefenderEndDate;

	@FXML
	private Label warDefenderHelper;

	@FXML
	private Label warDefender;

	private MainController main;

	public void init(MainController mainController) {
		main = mainController;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void populate() {
		// TODO Auto-generated method stub
		
	}
}
