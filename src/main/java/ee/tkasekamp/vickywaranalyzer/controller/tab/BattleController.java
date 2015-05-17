package ee.tkasekamp.vickywaranalyzer.controller.tab;

import ee.tkasekamp.vickywaranalyzer.controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

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
