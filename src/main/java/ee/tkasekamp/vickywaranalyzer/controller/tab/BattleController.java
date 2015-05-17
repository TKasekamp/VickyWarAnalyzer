package ee.tkasekamp.vickywaranalyzer.controller.tab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;
import ee.tkasekamp.vickywaranalyzer.core.Battle;

public class BattleController extends AbstractController{
	@FXML
	private TableView<?> attackerUnitsTable;

	@FXML
	private TableColumn<?, ?> colAttackerUnitType;

	@FXML
	private TableColumn<?, ?> colAttackerUnitNumber;

	@FXML
	private Label battleAttackerLosses;

	@FXML
	private Label battleAttackerArmySize;

	@FXML
	private Label battleAttacker;

	@FXML
	private ImageView battleAttackerFlag;

	@FXML
	private Label battleAttackerLeader;

	@FXML
	private TableView<?> defenderUnitsTable;

	@FXML
	private TableColumn<?, ?> colDefenderUnitType;

	@FXML
	private TableColumn<?, ?> colDefenderUnitNumber;

	@FXML
	private Label battleDefenderLosses;

	@FXML
	private Label battleDefenderArmySize;

	@FXML
	private Label battleDefender;

	@FXML
	private ImageView battleDefenderFlag;

	@FXML
	private Label battleDefenderLeader;

	@FXML
	private Label battleName;

	@FXML
	private Label battleType;

	@FXML
	private Label battleResult;

	@FXML
	private Label battleLocation;

	@FXML
	private Label battleDate;

	@FXML
	private Label battleTotalLosses;
	
	private MainController main;
	private Tab tab;
	
	public void init(MainController mainController, Tab tab) {
		main = mainController;
		this.tab = tab;
	}

	@Override
	public void reset() {
		tab.setDisable(true);
		tab.setText("Battle");
		
	}

	public void populate(Battle battle) {
		
	}
}
