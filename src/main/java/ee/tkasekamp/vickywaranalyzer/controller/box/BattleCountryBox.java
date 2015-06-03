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
	private TableView<Unit> unitsTable;
	@FXML
	private TableColumn<Unit, String> colUnitType;
	@FXML
	private TableColumn<Unit, Integer> colUnitNumber;

	@FXML
	private Label losses;

	@FXML
	private Label armySize;

	@FXML
	private Label country;

	@FXML
	private ImageView flag;

	@FXML
	private Label leader;

	// Helper labels that describe side of the country

	@FXML
	private Label sideHelper;
	@FXML
	private Label leaderHelper;

	@FXML
	private Label sideUnitsHelper;

	private ObservableList<Unit> unitsTableContent;

	private String side;
	private ModelService modelService;

	public void init(ModelService modelService, String side) {
		unitsTableContent = FXCollections.observableArrayList();
		this.side = side;
		this.modelService = modelService;
		setHelperLabels();
	}

	public void populate(Battle battle) {

	}

	private void setHelperLabels() {
		sideHelper.setText(side + ":");
		leaderHelper.setText(side + " leader:");
		sideUnitsHelper.setText(side + " units");
	}
}
