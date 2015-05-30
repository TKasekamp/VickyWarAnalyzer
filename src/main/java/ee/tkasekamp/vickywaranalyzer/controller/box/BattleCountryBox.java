package ee.tkasekamp.vickywaranalyzer.controller.box;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import ee.tkasekamp.vickywaranalyzer.core.Battle;
import ee.tkasekamp.vickywaranalyzer.core.Unit;
import ee.tkasekamp.vickywaranalyzer.service.ModelService;

public class BattleCountryBox {
	@FXML
	private TableView<Unit> attackerUnitsTable;
	@FXML
	private TableColumn<Unit, String> colAttackerUnitType;
	@FXML
	private TableColumn<Unit, Integer> colAttackerUnitNumber;

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

	private ObservableList<Unit> attackerUnitsTableContent;

	private String side;
	private ModelService modelService;

	public void init(ModelService modelService, String side) {
		attackerUnitsTableContent = FXCollections.observableArrayList();
		this.side = side;
		this.modelService = modelService;
	}
	
	public void populate(Battle battle) {
		
	}
}
